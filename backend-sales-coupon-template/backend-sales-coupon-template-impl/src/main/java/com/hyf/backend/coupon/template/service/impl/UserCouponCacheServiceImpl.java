package com.hyf.backend.coupon.template.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.constant.UserCouponCacheKey;
import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
import com.hyf.backend.coupon.template.service.UserCouponCacheService;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/4/1
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
@Slf4j
public class UserCouponCacheServiceImpl implements UserCouponCacheService, InitializingBean {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<UserCouponBO> getUserCoupon(Long uid, Integer status) {
        String redisKey = getRedisKey(uid, status);
        List<String> userCouponJsonStrList = stringRedisTemplate.opsForHash().values(redisKey).stream().map(Objects::toString).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userCouponJsonStrList)) {
            //避免缓存穿透，设置一个默认值到redis中
//            log.info("user coupon cache is empty uid: {}, status: {}", uid, status);
//            Map<String, String> defaultCacheMap = new HashMap<>();
//            defaultCacheMap.put("-1", JSON.toJSONString(new UserCouponBO().setId(-1L)));
//            stringRedisTemplate.opsForHash().putAll(redisKey, defaultCacheMap);
            return Collections.emptyList();
        }
        return userCouponJsonStrList.stream().map(s -> JSON.parseObject(s, UserCouponBO.class)).collect(Collectors.toList());
    }

    @Override
    public PageListBO<UserCouponBO> getUserCouponPage(Long uid, Integer status, Integer pageNo, Integer pageSize) {
        String redisKeyZset = getRedisKeyZset(uid, status);
        String redisKey = getRedisKey(uid, status);
        long start = (pageNo - 1) * pageSize;
        long end = (pageNo - 1) * pageSize + pageSize;
        Set<String> idStrSet = stringRedisTemplate.opsForZSet().reverseRangeByScore(redisKeyZset, 0, new DateTime().plusDays(1).toDate().getTime(), start, end);
//        List<Long> couponIdList = idStrList.stream().map(Long::valueOf).collect(Collectors.toList());
//        couponIdList
        if (CollectionUtils.isEmpty(idStrSet)) {
            log.info("user coupon cache is empty uid: {}, status: {}", uid, status);
        }
        List<Object> idStrList = new ArrayList<>(idStrSet);
//        String collect = idStrList.stream().collect(Collectors.joining(","));
//        stringRedisTemplate.opsForHash().get(redisKey, )

        List<UserCouponBO> collect = stringRedisTemplate.opsForHash().
                multiGet(redisKey, idStrList).stream().
                map(json -> {
                    String s = Objects.toString(json);
                    return JSON.parseObject(s, UserCouponBO.class);
                }).collect(Collectors.toList());
        return new PageListBO<>(collect, pageNo, pageSize, stringRedisTemplate.opsForZSet().zCard(redisKeyZset));

    }

    /**
     * 注意添加到USED和EXPIRED的同时也要删除USABLE的缓存
     *
     * @param uid
     * @param status
     * @return
     */
    @Override
    public boolean addCoupon(Long uid, Integer status, List<UserCouponBO> userCouponBO) {
        UserCouponStatusEnum userCouponStatusEnum = UserCouponStatusEnum.of(status);
        switch (userCouponStatusEnum) {
            case USED:
                return addUsedCouponToCache(uid, userCouponBO);
            case USABLE:
                return addUsableCouponToCache(uid, status, userCouponBO);
            case EXPIRED:
                return addExpiredCouponToCache(uid, userCouponBO);
            default:
                return false;
        }
    }

    private boolean addExpiredCouponToCache(Long uid, List<UserCouponBO> userCouponBO) {
        String redisUsableKey = getRedisKey(uid, UserCouponStatusEnum.USABLE.getCode());
        String redisExpiredKey = getRedisKey(uid, UserCouponStatusEnum.EXPIRED.getCode());
        String redisUsableKeyZset = getRedisKeyZset(uid, UserCouponStatusEnum.USABLE.getCode());
        String redisExpiredKeyZset = getRedisKeyZset(uid, UserCouponStatusEnum.EXPIRED.getCode());
        //TODO不应该在内存中判断是否有重复，应该在redis中判断是否有重复
        List<UserCouponBO> userUsableCouponList = getUserCoupon(uid, UserCouponStatusEnum.USABLE.getCode());
        List<Long> userUsableCouponIdList = userUsableCouponList.stream().map(UserCouponBO::getId).collect(Collectors.toList());
        //过期的优惠券必须在可使用的缓存中
        List<Long> paramCouponIdList = userCouponBO.stream().map(UserCouponBO::getId).collect(Collectors.toList());
        if (!CollectionUtils.isSubCollection(paramCouponIdList, userUsableCouponIdList)) {
            log.error("CurCoupons Is Not Equal ToCache: {}, {}, {}",
                    uid, JSON.toJSONString(userUsableCouponIdList),
                    JSON.toJSONString(paramCouponIdList));
            throw new BizException("CurCoupons Is Not Equal To Cache!");
        }

        Map<String, String> needCacheMap = userCouponBO.stream().collect(Collectors.toMap(key -> String.valueOf(key.getId()), JSON::toJSONString));
        Set<ZSetOperations.TypedTuple<String>> defaultTypedTupleSet = userCouponBO.stream().map(bo -> new DefaultTypedTuple<>(String.valueOf(bo.getId()), (double) bo.getGetTime().getTime())).collect(Collectors.toSet());
        List<String> paramStrIdList = paramCouponIdList.stream().map(Objects::toString).collect(Collectors.toList());
        SessionCallback<Objects> sessionCallback = new SessionCallback<Objects>() {
            @Override
            public Objects execute(RedisOperations redisOperations) throws DataAccessException {
                //先加入用户优惠券过期缓存中，不会覆盖额，直接追加在后面
                redisOperations.opsForHash().putAll(redisExpiredKey, needCacheMap);
                redisOperations.opsForZSet().add(redisExpiredKeyZset, defaultTypedTupleSet);
                //从用户可使用优惠券中剔除
                redisOperations.opsForHash().delete(redisUsableKey, paramStrIdList);
                redisOperations.opsForZSet().remove(redisUsableKeyZset, paramStrIdList);


                redisOperations.expire(redisExpiredKey, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                redisOperations.expire(redisUsableKey, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);

                redisOperations.expire(redisExpiredKeyZset, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                redisOperations.expire(redisUsableKeyZset, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                return null;
            }
        };

        log.info("Pipeline Exe Result: {}",
                JSON.toJSONString(
                        stringRedisTemplate.executePipelined(sessionCallback)));
        return true;
    }

    private boolean addUsedCouponToCache(Long uid, List<UserCouponBO> userCouponBO) {
        String redisUsedKey = getRedisKey(uid, UserCouponStatusEnum.USED.getCode());
        String redisUsableKey = getRedisKey(uid, UserCouponStatusEnum.USABLE.getCode());
        String redisUsedKeyZset = getRedisKeyZset(uid, UserCouponStatusEnum.USED.getCode());
        String redisUsableKeyZset = getRedisKeyZset(uid, UserCouponStatusEnum.USABLE.getCode());
        List<UserCouponBO> userUsableCouponList = getUserCoupon(uid, UserCouponStatusEnum.USABLE.getCode());
        List<Long> userUsableCouponIdList = userUsableCouponList.stream().map(UserCouponBO::getId).collect(Collectors.toList());

        List<Long> paramCouponIdList = userCouponBO.stream().map(UserCouponBO::getId).collect(Collectors.toList());
        if (!CollectionUtils.isSubCollection(paramCouponIdList, userUsableCouponIdList)) {
            log.error("CurCoupons Is Not Equal ToCache: {}, {}, {}",
                    uid, JSON.toJSONString(userUsableCouponIdList),
                    JSON.toJSONString(paramCouponIdList));
            throw new BizException("CurCoupons Is Not Equal To Cache!");
        }
//        List<UserCouponBO> userUsedCouponList = getUserCoupon(uid, UserCouponStatusEnum.USED.getCode());
//        userUsedCouponList.add(userCouponBO);
//        Map<Long, UserCouponBO> userCouponIdToMap = userUsedCouponList.stream().collect(Collectors.toMap(UserCouponBO::getId, id -> id));
//        Map<String, String> needCacheMap = new HashMap<>();
//        needCacheMap.put(String.valueOf(userCouponBO.getId()), JSON.toJSONString(userCouponBO));
        Map<String, String> needCacheMap = userCouponBO.stream().collect(Collectors.toMap(key -> String.valueOf(key.getId()), JSON::toJSONString));
        Set<ZSetOperations.TypedTuple<String>> defaultTypedTupleSet = userCouponBO.stream().map(coupon -> new DefaultTypedTuple<>(String.valueOf(coupon.getId()), (double) coupon.getGetTime().getTime())).collect(Collectors.toSet());
        List<String> paramIdStrList = paramCouponIdList.stream().map(Objects::toString).collect(Collectors.toList());
        SessionCallback<Objects> sessionCallback = new SessionCallback<Objects>() {

            @Override
            public Objects execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForHash().putAll(redisUsedKey, needCacheMap);
                redisOperations.opsForZSet().add(redisUsedKeyZset, defaultTypedTupleSet);

                redisOperations.opsForHash().delete(redisUsableKey, paramIdStrList);
                //删掉usable zset的数据 也要删掉map中的数据
                redisOperations.opsForZSet().remove(redisUsableKeyZset, paramIdStrList);


                redisOperations.expire(redisUsedKey, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                redisOperations.expire(redisUsableKey, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                redisOperations.expire(redisUsableKeyZset, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
                redisOperations.expire(redisUsedKeyZset, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);


//                redisOperations.opsForZSet().add(redisKey, String.valueOf(userCouponBO.getId()), userCouponBO.getGetTime().getTime());
                return null;
            }
        };

        log.info("Pipeline Exe Result: {}",
                JSON.toJSONString(
                        stringRedisTemplate.executePipelined(sessionCallback)));
        return true;

    }

    private boolean addUsableCouponToCache(Long uid, Integer status, List<UserCouponBO> userCouponBO) {
        String redisKey = getRedisKey(uid, status);
        String redisKeyZset = getRedisKeyZset(uid, status);
//        List<UserCouponBO> userCouponList = getUserCoupon(uid, status);
//        userCouponList.add(userCouponBO);
//        Map<Long, UserCouponBO> idToMap = userCouponList.stream().collect(Collectors.toMap(UserCouponBO::getId, bo -> bo));
//        Map<String, String> needCacheMap = new HashMap<>();
//        needCacheMap.put(String.valueOf(userCouponBO.getId()), JSON.toJSONString(userCouponBO));
        Map<String, String> needCacheMap = userCouponBO.stream().collect(Collectors.toMap(key -> String.valueOf(key.getId()), JSON::toJSONString));

        stringRedisTemplate.opsForHash().putAll(redisKey, needCacheMap);
        log.info("save to cache... coupon: {}", JSON.toJSONString(userCouponBO));
        stringRedisTemplate.expire(redisKey, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);

        //往zset中添加一个id
        Set<ZSetOperations.TypedTuple<String>> defaultTypedTupleSet = userCouponBO.stream().map(bo -> new DefaultTypedTuple<>(String.valueOf(bo.getId()), (double) bo.getGetTime().getTime())).collect(Collectors.toSet());
        stringRedisTemplate.opsForZSet().add(redisKeyZset, defaultTypedTupleSet);
        stringRedisTemplate.expire(redisKeyZset, getRandomExpirationTime(1, 2), TimeUnit.SECONDS);
        return true;
    }

    private Long getRandomExpirationTime(Integer min, Integer max) {

        return RandomUtils.nextLong(
                min * 60 * 60,
                max * 60 * 60
        );
    }

    private String getRedisKey(Long uid, Integer status) {
        UserCouponStatusEnum userCouponStatusEnum = UserCouponStatusEnum.of(status);
        switch (userCouponStatusEnum) {
            case EXPIRED:
                return String.format(UserCouponCacheKey.USER_COUPON_EXPIRED_CACHE, uid);
            case USABLE:
                return String.format(UserCouponCacheKey.USER_COUPON_USABLE_CACHE, uid);
            case USED:
                return String.format(UserCouponCacheKey.USER_COUPON_USED_CACHE, uid);
            default:
                return null;

        }
    }

    private String getRedisKeyZset(Long uid, Integer status) {
        UserCouponStatusEnum userCouponStatusEnum = UserCouponStatusEnum.of(status);
        switch (userCouponStatusEnum) {
            case EXPIRED:
                return String.format(UserCouponCacheKey.USER_COUPON_EXPIRED_CACHE_ZSET, uid);
            case USABLE:
                return String.format(UserCouponCacheKey.USER_COUPON_USABLE_CACHE_ZSET, uid);
            case USED:
                return String.format(UserCouponCacheKey.USER_COUPON_USED_CACHE_ZSET, uid);
            default:
                return null;

        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        stringRedisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer());
    }
}


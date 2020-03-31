package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.coupon.template.constant.CouponTemplateCacheKey;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDO;
import com.hyf.backend.coupon.template.mapper.CouponTemplateDOMapper;
import com.hyf.backend.coupon.template.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/2/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {
    private static final char[] cs = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'};

    @Autowired
    private CouponTemplateDOMapper couponTemplateDOMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据模板异步创建优惠券码
     *
     * @param salesCouponTemplate
     */
    @Async("getAsyncExecutor")
    @Override
    public void asyncGenerateKeyByCouponTemplate(CouponTemplateDO salesCouponTemplate) {
        Set<String> couponCodes = new HashSet<>(salesCouponTemplate.getCouponCount());

        String datePrefix6 = new DateTime().toString("yyMMdd");
        for (int i = 0; i < salesCouponTemplate.getCouponCount(); ++i) {
            couponCodes.add(buildSuffix8(datePrefix6));
        }
        while (couponCodes.size() < salesCouponTemplate.getCouponCount()) {
            couponCodes.add(buildSuffix8(datePrefix6));
        }
        assert couponCodes.size() == salesCouponTemplate.getCouponCount();

        String redisKey = String.format("%s%s", CouponTemplateCacheKey.COUPON_TEMPLATE_CODE_KEY, salesCouponTemplate.getId());

        Long count = stringRedisTemplate.opsForList().rightPushAll(redisKey, couponCodes);
        log.info("保存到redis中了, count: {}", count);

        CouponTemplateDO toUpdate = new CouponTemplateDO();
        toUpdate.setId(salesCouponTemplate.getId());
        toUpdate.setIsAvailable(true);
        couponTemplateDOMapper.updateByPrimaryKeySelective(toUpdate);

    }

    private String buildSuffix8(String date) {

        List<Character> characterList = date.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(characterList);
        String prefix6 = characterList.stream().map(Object::toString).collect(Collectors.joining());
        String mid7 = RandomStringUtils.random(7, cs);
        String s = RandomStringUtils.randomNumeric(1);
        return prefix6 + mid7 + s;
    }
}

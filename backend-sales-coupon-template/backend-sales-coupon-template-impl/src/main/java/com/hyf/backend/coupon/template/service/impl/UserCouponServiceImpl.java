package com.hyf.backend.coupon.template.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyf.backend.common.constant.RocketMQTopic;
import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.domain.QueryPageDTO;
import com.hyf.backend.common.message.UserCouponExpiredMessage;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.coupon.template.api.dto.ApiQueryIdsDTO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
import com.hyf.backend.coupon.template.dataobject.UserCouponDO;
import com.hyf.backend.coupon.template.dataobject.UserCouponDOExample;
import com.hyf.backend.coupon.template.mapper.UserCouponDOMapper;
import com.hyf.backend.coupon.template.service.CouponTemplateService;
import com.hyf.backend.coupon.template.service.UserCouponCacheService;
import com.hyf.backend.coupon.template.service.UserCouponService;
import com.hyf.backend.coupon.template.service.helper.CouponClassify;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
@Slf4j
public class UserCouponServiceImpl implements UserCouponService {
    @Autowired
    private UserCouponDOMapper userCouponDOMapper;
    @Autowired
    private UserCouponCacheService userCouponCacheService;
    @Autowired
    private CouponTemplateService couponTemplateService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public PageListBO<UserCouponBO> listByUidAndType(Long uid, Integer status, QueryPageDTO queryPageDTO) {
        PageListBO<UserCouponBO> cachedUserCouponList =
                userCouponCacheService.getUserCouponPage(uid, status, queryPageDTO.getPageNo(), queryPageDTO.getPageSize());
        PageListBO<UserCouponBO> preTarget;
        if (CollectionUtils.isNotEmpty(cachedUserCouponList.getList())) {
            log.debug("coupon cache is not empty: {}, {}", uid, status);
            preTarget = cachedUserCouponList;
        } else {
            log.debug("coupon cache is empty, get coupon from db: {}, {}",
                    uid, status);
            //从数据库中查询
            UserCouponDOExample example = new UserCouponDOExample();
            example.createCriteria().andStatusEqualTo(UserCouponStatusEnum.of(status))
                    .andUserIdEqualTo(uid);
            PageListBO<UserCouponDO> userCouponDOPageListBO = MapperHelper.selectPageByExample(userCouponDOMapper, example, queryPageDTO.toPage());
            if (CollectionUtils.isEmpty(userCouponDOPageListBO.getList())) {
                log.debug("current user do not have coupon: {}, {}", uid, status);
                return new PageListBO<>(userCouponDOPageListBO, UserCouponBO::new);
            }
            PageListBO<UserCouponBO> userCouponBOPageListBO = new PageListBO<>(userCouponDOPageListBO, UserCouponBO::new);
            List<UserCouponBO> userCouponList = userCouponBOPageListBO.getList();
            List<Long> templateIdList = userCouponList.stream().map(UserCouponBO::getTemplateId).collect(Collectors.toList());
            List<CouponTemplateBO> couponTemplateBOList = couponTemplateService.findByIds(new ApiQueryIdsDTO().setIds(templateIdList));
            Map<Long, CouponTemplateBO> templateIdToMap = couponTemplateBOList.stream().collect(Collectors.toMap(CouponTemplateBO::getId, template -> template));
//            preTarget = userCouponDOPageListBO.getList().stream().map(UserCouponBO::new).collect(Collectors.toList());
            for (UserCouponBO userCouponBO : userCouponList) {
                if (templateIdToMap.get(userCouponBO.getTemplateId()) != null) {
                    userCouponBO.setCouponTemplateBO(templateIdToMap.get(userCouponBO.getTemplateId()));
                }
                if (status.equals(UserCouponStatusEnum.USABLE.getCode()) && userCouponBO.getCouponTemplateBO().getExpirationCode().equals(CouponTemplateExpirationEnum.GUDING.getCode())) {
                    //固定有效期，有效时间就是deadline-now
                    int days = Days.daysBetween(DateTime.now(), new DateTime(userCouponBO.getCouponTemplateBO().getExpirationDeadline())).getDays();
//                    int days = Days.daysBetween(, DateTime.now()).getDays();
                    userCouponBO.setEffectDays(days);
                }
            }
            preTarget = userCouponBOPageListBO;
            //写入到缓存中
            boolean b = userCouponCacheService.addCoupon(uid, status, userCouponBOPageListBO.getList());
        }
        //如果取到的是可用的优惠券，还要对优惠券判断是否过期了
        if (status.equals(UserCouponStatusEnum.USABLE.getCode())) {
            CouponClassify classify = CouponClassify.classify(preTarget.getList());
            if (CollectionUtils.isNotEmpty(classify.getExpiredList())) {
                log.info("Add Expired Coupons To Cache From FindCouponsByStatus: " +
                        "{}, {}", uid, status);
                //将已经过期的优惠券添加到缓存中
                userCouponCacheService.addCoupon(uid, UserCouponStatusEnum.EXPIRED.getCode(), classify.getExpiredList());
                //异步发送消息到mq，然后消费方更新数据库
                rocketMQTemplate.convertAndSend(RocketMQTopic.USER_COUPON_TOPIC + RocketMQTopic.USER_ROUPON_TOPIC_EXPIRED_TAG, JSON.toJSONString(
                        new UserCouponExpiredMessage(classify.getExpiredList().stream().map(UserCouponBO::getId).collect(Collectors.toList())
                                , UserCouponStatusEnum.EXPIRED.getCode())
                ));
            }
        }
        return preTarget;
    }

    @Override
    public List<CouponTemplateBO> findAvailableTemplate(Long uid, QueryPageDTO queryPageDTO) {
        List<CouponTemplateBO> availableCouponTemplatePageList = couponTemplateService.findAvailableCouponTemplate(queryPageDTO);
        //过滤过期的优惠券模板
        List<CouponTemplateBO> availableCouponTemplateList = availableCouponTemplatePageList.stream().
                filter(template -> template.getExpirationDeadline().getTime() > new Date().getTime()).collect(Collectors.toList());
        log.info("Find Usable Template Count: {}", availableCouponTemplateList.size());

        List<UserCouponBO> userUsableCouponList = userCouponCacheService.getUserCoupon(uid, UserCouponStatusEnum.USABLE.getCode());
        List<UserCouponBO> userUsedCouponList = userCouponCacheService.getUserCoupon(uid, UserCouponStatusEnum.USED.getCode());
        List<UserCouponBO> userExpiredCouponList = userCouponCacheService.getUserCoupon(uid, UserCouponStatusEnum.EXPIRED.getCode());

        Map<Long, List<UserCouponBO>> couponTemplateIdToUserUsableCouponListMap = userUsableCouponList.stream().collect(Collectors.groupingBy(UserCouponBO::getTemplateId));
        Map<Long, List<UserCouponBO>> couponTemplateIdToUserUsedCouponListMap = userUsedCouponList.stream().collect(Collectors.groupingBy(UserCouponBO::getTemplateId));
        Map<Long, List<UserCouponBO>> couponTemplateIdToUserExpiredListMap = userExpiredCouponList.stream().collect(Collectors.groupingBy(UserCouponBO::getTemplateId));

        List<CouponTemplateBO> result = new ArrayList<>();

        //分8种情况，规定已使用的和过期的都不能在领取了
        //1. 有      没有              没有
        //2. 有，    没有，            有
        //3. 有      有                没有
        //4  有      有               有
        //5. 没有    没有            没有
        //6. 没有    没有              有
        //7. 没有    有               没有
        //8. 没有     有               有

        for (CouponTemplateBO couponTemplateBO : availableCouponTemplateList) {

            //第一种情况
            if (couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {
                //用户有领取当前优惠券模板的，判断没人领取限制
                Integer eachGetLimitation = couponTemplateBO.getEachGetLimitation();
                int getCount = couponTemplateIdToUserUsableCouponListMap.get(couponTemplateBO.getId()).size();
                if (getCount >= eachGetLimitation) {
                    continue;
                }
                result.add(couponTemplateBO);
            }
            //第二种情况
            else if (couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {

                Integer eachGetLimitation = couponTemplateBO.getEachGetLimitation();
                //已经领取的和已经过期的总数
                int getCount = couponTemplateIdToUserUsableCouponListMap.get(couponTemplateBO.getId()).size() +
                        couponTemplateIdToUserExpiredListMap.get(couponTemplateBO.getId()).size();

                if (getCount >= eachGetLimitation) {
                    continue;
                }
                result.add(couponTemplateBO);
            }
            //第三种情况
            else if (couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {
                Integer eachGetLimitation = couponTemplateBO.getEachGetLimitation();
                //已经领取的和已经使用的
                int getCount = couponTemplateIdToUserUsableCouponListMap.get(couponTemplateBO.getId()).size() +
                        couponTemplateIdToUserUsedCouponListMap.get(couponTemplateBO.getId()).size();

                if (getCount >= eachGetLimitation) {
                    continue;
                }
                result.add(couponTemplateBO);
            }
            //第四种情况
            else if (couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {
                //已经领取的和已经使用的和已经过期的都有
                Integer eachGetLimitation = couponTemplateBO.getEachGetLimitation();
                int getCount = couponTemplateIdToUserUsableCouponListMap.get(couponTemplateBO.getId()).size() +
                        couponTemplateIdToUserUsedCouponListMap.get(couponTemplateBO.getId()).size() + couponTemplateIdToUserExpiredListMap.get(couponTemplateBO.getId()).size();

                if (getCount >= eachGetLimitation) {
                    continue;
                }
                result.add(couponTemplateBO);
            }
            //第五种情况
            else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {
                //不用判断，直接添加就好了
                result.add(couponTemplateBO);
            }
            //第六种情况
            else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {
                //用户有领取当前优惠券模板的，判断没人领取限制
                Integer eachGetLimitation = couponTemplateBO.getEachGetLimitation();
                int getCount = couponTemplateIdToUserExpiredListMap.get(couponTemplateBO.getId()).size();
                if (getCount >= eachGetLimitation) {
                    continue;
                }
                result.add(couponTemplateBO);
            }
            //第七种情况
            else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && !couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {
                //用户有领取当前优惠券模板的，判断没人领取限制
                Integer eachGetLimitation = couponTemplateBO.getEachGetLimitation();
                int getCount = couponTemplateIdToUserUsedCouponListMap.get(couponTemplateBO.getId()).size();
                if (getCount >= eachGetLimitation) {
                    continue;
                }
                result.add(couponTemplateBO);
            }
            //第八种情况
            else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserUsedCouponListMap.containsKey(couponTemplateBO.getId())
                    && couponTemplateIdToUserExpiredListMap.containsKey(couponTemplateBO.getId())) {
                //用户有领取当前优惠券模板的，判断没人领取限制
                Integer eachGetLimitation = couponTemplateBO.getEachGetLimitation();
                int getCount = couponTemplateIdToUserUsedCouponListMap.get(couponTemplateBO.getId()).size() + couponTemplateIdToUserExpiredListMap.get(couponTemplateBO.getId()).size();
                if (getCount >= eachGetLimitation) {
                    continue;
                }
                result.add(couponTemplateBO);
            } else {
                throw new BizException("未知情况发生.....");
            }
        }

        return result;
    }


    @Override
    public UserCouponBO acquireCoupon(Long uid, Long templateId) {
        List<CouponTemplateBO> couponTemplateBOList = couponTemplateService.findByIds(new ApiQueryIdsDTO().setIds(Collections.singletonList(templateId)));
        if (couponTemplateBOList.size() <= 0) {
            log.error("Can Not Acquire Template From TemplateClient: {}",
                    templateId);
            throw new BizException("Can Not Acquire Template From TemplateClient");
        }

        CouponTemplateBO couponTemplateBO = couponTemplateBOList.get(0);

        if (couponTemplateBO.getExpirationDeadline().getTime() <= new Date().getTime()) {
            throw new BizException("优惠券模板已经过期了");
        }
        //判断领取限制
        List<UserCouponBO> userUsableCouponList = userCouponCacheService.getUserCoupon(uid, UserCouponStatusEnum.USABLE.getCode());
        List<UserCouponBO> userUsedCouponList = userCouponCacheService.getUserCoupon(uid, UserCouponStatusEnum.USED.getCode());
        List<UserCouponBO> userExpiredCouponList = userCouponCacheService.getUserCoupon(uid, UserCouponStatusEnum.EXPIRED.getCode());

        Map<Long, List<UserCouponBO>> couponTemplateIdToUserUsableCouponListMap = userUsableCouponList.stream().collect(Collectors.groupingBy(UserCouponBO::getTemplateId));
        Map<Long, List<UserCouponBO>> couponTemplateIdToUserUsedCouponListMap = userUsedCouponList.stream().collect(Collectors.groupingBy(UserCouponBO::getTemplateId));
        Map<Long, List<UserCouponBO>> couponTemplateIdToUserExpiredListMap = userExpiredCouponList.stream().collect(Collectors.groupingBy(UserCouponBO::getTemplateId));

        //又分为八种情况
        //1. 有      没有              没有
        //2. 有，    没有，            有
        //3. 有      有                没有
        //4  有      有               有
        //5. 没有    没有            没有
        //6. 没有    没有              有
        //7. 没有    有               没有
        //8. 没有     有               有
        if (couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserExpiredListMap.containsKey(templateId)
                && couponTemplateIdToUserUsableCouponListMap.get(templateId).size() >= couponTemplateBO.getEachGetLimitation()) {
            log.error("Exceed Template Assign Limitation: {}",
                    templateId);
            throw new BizException("Exceed Template Assign Limitation");
        }
        //第二种情况
        else if (couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserExpiredListMap.containsKey(templateId)) {

            int getCount = couponTemplateIdToUserUsableCouponListMap.get(templateId).size() + couponTemplateIdToUserExpiredListMap.get(templateId).size();
            if (getCount >= couponTemplateBO.getEachGetLimitation()) {
                log.error("Exceed Template Assign Limitation: {}",
                        templateId);
                throw new BizException("Exceed Template Assign Limitation");
            }
        }
        //第三种情况
        else if (couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserExpiredListMap.containsKey(templateId)) {
            int getCount = couponTemplateIdToUserUsableCouponListMap.get(templateId).size() + couponTemplateIdToUserUsedCouponListMap.get(templateId).size();
            if (getCount >= couponTemplateBO.getEachGetLimitation()) {
                log.error("Exceed Template Assign Limitation: {}",
                        templateId);
                throw new BizException("Exceed Template Assign Limitation");
            }
        }
        //第四种情况
        else if (couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserExpiredListMap.containsKey(templateId)) {
            int getCount = couponTemplateIdToUserUsableCouponListMap.get(templateId).size() + couponTemplateIdToUserUsedCouponListMap.get(templateId).size() + couponTemplateIdToUserExpiredListMap.get(templateId).size();
            if (getCount >= couponTemplateBO.getEachGetLimitation()) {
                log.error("Exceed Template Assign Limitation: {}",
                        templateId);
                throw new BizException("Exceed Template Assign Limitation");
            }
        }
        //第五种情况
        else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserExpiredListMap.containsKey(templateId)) {
            //可以领取
        }
        //第六种情况
        else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserExpiredListMap.containsKey(templateId)) {
            int getCount = couponTemplateIdToUserExpiredListMap.get(templateId).size();
            if (getCount >= couponTemplateBO.getEachGetLimitation()) {
                log.error("Exceed Template Assign Limitation: {}",
                        templateId);
                throw new BizException("Exceed Template Assign Limitation");
            }
        }
        //第七种情况
        else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && !couponTemplateIdToUserExpiredListMap.containsKey(templateId)) {
            int getCount = couponTemplateIdToUserUsedCouponListMap.get(templateId).size();
            if (getCount >= couponTemplateBO.getEachGetLimitation()) {
                log.error("Exceed Template Assign Limitation: {}",
                        templateId);
                throw new BizException("Exceed Template Assign Limitation");
            }
        }
        //第八种情况
        else if (!couponTemplateIdToUserUsableCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserUsedCouponListMap.containsKey(templateId)
                && couponTemplateIdToUserExpiredListMap.containsKey(templateId)) {
            int getCount = couponTemplateIdToUserUsedCouponListMap.get(templateId).size() + couponTemplateIdToUserExpiredListMap.get(templateId).size();
            if (getCount >= couponTemplateBO.getEachGetLimitation()) {
                log.error("Exceed Template Assign Limitation: {}",
                        templateId);
                throw new BizException("Exceed Template Assign Limitation");
            }
        } else {
            throw new BizException("情况不存在");
        }

        //判断优惠券总数是否被领取完了
        UserCouponDOExample userCouponDOExample = new UserCouponDOExample();
        userCouponDOExample.createCriteria().andTemplateIdEqualTo(templateId);
        Long aLong = userCouponDOMapper.countByExample(userCouponDOExample);
        if (aLong >= couponTemplateBO.getCouponCount()) {
            throw new BizException("优惠券被领完了");
        }

        UserCouponDO userCouponDO = new UserCouponDO();
        userCouponDO.setTemplateId(templateId);
        userCouponDO.setUserId(uid);
        userCouponDO.setGetTime(new Date());
        userCouponDO.setStatus(UserCouponStatusEnum.USABLE);
        userCouponDO.setCreateTime(new Date());
        userCouponDO.setUpdateTime(new Date());
        userCouponDOMapper.insertSelective(userCouponDO);

        UserCouponBO userCouponBO = new UserCouponBO(userCouponDO);
        userCouponBO.setCouponTemplateBO(couponTemplateBO);
        //有效时间
        if (couponTemplateBO.getExpirationCode().equals(CouponTemplateExpirationEnum.GUDING.getCode())) {
            int days = Days.daysBetween(DateTime.now(), new DateTime(userCouponBO.getCouponTemplateBO().getExpirationDeadline())).getDays();
            userCouponBO.setEffectDays(days);
        } else {
            userCouponBO.setEffectDays(couponTemplateBO.getExpirationGap());
        }
        //放入缓存中
        userCouponCacheService.addCoupon(uid, UserCouponStatusEnum.USABLE.getCode(), Collections.singletonList(userCouponBO));

        return userCouponBO;

    }

    @Override
    public List<UserCouponBO> getUserCouponByIdList(List<Long> userCouponIdList) {
        if (CollectionUtils.isEmpty(userCouponIdList)) {
            return Collections.EMPTY_LIST;
        }
        UserCouponDOExample example = new UserCouponDOExample();
        example.createCriteria().andIdIn(userCouponIdList);
        List<UserCouponBO> userCouponBOList = userCouponDOMapper.selectByExample(example).stream().map(UserCouponBO::new).collect(Collectors.toList());
        List<Long> templateIdList = userCouponBOList.stream().map(UserCouponBO::getTemplateId).collect(Collectors.toList());
        List<CouponTemplateBO> couponTemplateBOList = couponTemplateService.findByIds(new ApiQueryIdsDTO().setIds(templateIdList));
        Map<Long, CouponTemplateBO> templateIdToMap = couponTemplateBOList.stream().collect(Collectors.toMap(CouponTemplateBO::getId, template -> template));
        for (UserCouponBO userCouponBO : userCouponBOList) {
            if (templateIdToMap.containsKey(userCouponBO.getTemplateId())) {
                userCouponBO.setCouponTemplateBO(templateIdToMap.get(userCouponBO.getTemplateId()));
            }
            if (userCouponBO.getCouponTemplateBO().getExpirationCode().equals(CouponTemplateExpirationEnum.GUDING.getCode())) {
                int days = Days.daysBetween(DateTime.now(), new DateTime(userCouponBO.getCouponTemplateBO().getExpirationDeadline().getTime())).getDays();
                userCouponBO.setEffectDays(days);
            }
        }

        return userCouponBOList;
    }
}

package com.hyf.backend.coupon.template.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyf.backend.common.constant.RocketMQTopic;
import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.domain.QueryPageDTO;
import com.hyf.backend.common.message.UserCouponExpiredMessage;
import com.hyf.backend.common.mybatis.mapper.MapperHelper;
import com.hyf.backend.common.vo.ListVO;
import com.hyf.backend.coupon.template.api.dto.ApiCouponSettlementDTO;
import com.hyf.backend.coupon.template.api.dto.ApiQueryIdsDTO;
import com.hyf.backend.coupon.template.api.dto.ProductDTO;
import com.hyf.backend.coupon.template.api.dto.UserSettlementDTO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponSettlementVO;
import com.hyf.backend.coupon.template.api.vo.ApiCouponTemplateVO;
import com.hyf.backend.coupon.template.api.vo.ApiUserCouponVO;
import com.hyf.backend.coupon.template.bo.CouponTemplateBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.bo.UserSettlementBO;
import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
import com.hyf.backend.coupon.template.dataobject.UserCouponDO;
import com.hyf.backend.coupon.template.dataobject.UserCouponDOExample;
import com.hyf.backend.coupon.template.feign.ApiCartClient;
import com.hyf.backend.coupon.template.feign.ApiGoodsClient;
import com.hyf.backend.coupon.template.mapper.UserCouponDOMapper;
import com.hyf.backend.coupon.template.service.CouponTemplateService;
import com.hyf.backend.coupon.template.service.UserCouponCacheService;
import com.hyf.backend.coupon.template.service.UserCouponService;
import com.hyf.backend.coupon.template.service.helper.CouponDiscountTypeClassify;
import com.hyf.backend.coupon.template.service.helper.CouponExpiredClassify;
import com.hyf.backend.goods.api.vo.ApiGoodsSkuVO;
import com.hyf.backend.goods.api.vo.ApiUserCartVO;
import com.hyf.backend.goods.dto.ApiSkuIdListQueryDTO;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.hyf.backend.utils.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    @Autowired
    private ApiGoodsClient goodsClient;
    @Autowired
    private ApiCartClient cartClient;
    @Autowired
    private ManjianSettlementStrategy manjianSettlementStrategy;
    @Autowired
    private LijianSettlementStrategy lijianSettlementStrategy;
    @Autowired
    private ZhekouSettlementStrategy zhekouSettlementStrategy;

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
            CouponExpiredClassify classify = CouponExpiredClassify.classify(preTarget.getList());
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

        if (uid == null) {
            return availableCouponTemplateList;
        }
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

        //s分为八种情况
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

    public List<UserCouponBO> getUserAvailableMatchGoodsCouponList(long uid, List<ApiGoodsSkuVO> skuVOList) {
        //找到用户所有可用的优惠券
        PageListBO<UserCouponBO> userCouponBOPageListBO = listByUidAndType(uid, UserCouponStatusEnum.USABLE.getCode(), new QueryPageDTO().setPageNo(1).setPageSize(Integer.MAX_VALUE));
        List<UserCouponBO> userCouponBOList = userCouponBOPageListBO.getList();
        Map<Long, UserCouponBO> couponIdToCouponMap = userCouponBOList.stream().collect(Collectors.toMap(UserCouponBO::getId, coupon -> coupon));
        //筛选出和当前商品匹配的优惠券，每一个优惠券支持的商品类型和
        List<UserCouponBO> matchCouponList = new ArrayList<>();
//            [1, 3, 2]        [[1,2,3,4],[3,4]]
        Set<Long> matchCouponIdSet = new HashSet<>();
        for (ApiGoodsSkuVO skuVO : skuVOList) {
            for (UserCouponBO userCouponBO : userCouponBOList) {
                List<Integer> goodsTypeLimitation = userCouponBO.getCouponTemplateBO().getGoodsTypeLimitation();
                //判断交集
                if (goodsTypeLimitation.contains(skuVO.getGoods().getCategoryId())) {
                    matchCouponIdSet.add(userCouponBO.getId());
                }
            }
        }

        for (Long matchCouponId : matchCouponIdSet) {
            if (couponIdToCouponMap.containsKey(matchCouponId)) {
                matchCouponList.add(couponIdToCouponMap.get(matchCouponId));
            }
        }
        return matchCouponList;
    }

    @Override
    public List<ApiUserCouponVO> listMatchCartGoodsCouponList(Integer uid, Integer cartId) {
        if (cartId == null) {
            //查询用户所有已勾选的商品
            //从已经勾选的购物车中找到商品，然后和可用的优惠券做规则筛选
            ResponseVO<ListVO<ApiUserCartVO>> checkedGoods = cartClient.findCheckedGoods(uid);
            if (!checkedGoods.isOk()) {
                throw new BizException(checkedGoods.getMsg());
            }
            List<ApiUserCartVO> cartList = checkedGoods.getData().getList();
//            Map<Integer, ApiUserCartVO> cartIdToCartMap = cartList.stream().collect(Collectors.toMap(ApiUserCartVO::getId, cart -> cart));
            List<Integer> skuIdList = cartList.stream().map(ApiUserCartVO::getSkuId).collect(Collectors.toList());
            ResponseVO<ListVO<ApiGoodsSkuVO>> skuListVORes = goodsClient.findBySkuList(new ApiSkuIdListQueryDTO(skuIdList));

            if (!skuListVORes.isOk()) {
                throw new BizException(skuListVORes.getMsg());
            }
            List<ApiGoodsSkuVO> skuVOList = skuListVORes.getData().getList();
//            Map<Integer, ApiGoodsSkuVO> skuIdToSkuMap = skuVOList.stream().collect(Collectors.toMap(ApiGoodsSkuVO::getId, sku -> sku));
            List<UserCouponBO> matchCouponList = getUserAvailableMatchGoodsCouponList(uid, skuVOList);

            List<ApiUserCouponVO> voList = new ArrayList<>();
            for (UserCouponBO userCouponBO : matchCouponList) {
                ApiUserCouponVO userCouponVO = new ApiUserCouponVO();
                BeanUtils.copyProperties(userCouponBO, userCouponVO);
                ApiCouponTemplateVO templateVO = new ApiCouponTemplateVO();
                BeanUtils.copyProperties(userCouponBO.getCouponTemplateBO(), templateVO);
                userCouponVO.setCouponTemplate(templateVO);


                if (userCouponVO.getCouponTemplate().getExpirationCode().equals(CouponTemplateExpirationEnum.GUDING.getCode())) {
                    long startTime = System.currentTimeMillis() / 1000;
                    long endTime = userCouponVO.getCouponTemplate().getExpirationDeadline().getTime() / 1000;
                    userCouponVO.setStartTime(startTime);
                    userCouponVO.setEndTime(endTime);
                } else if (userCouponVO.getCouponTemplate().getExpirationCode().equals(CouponTemplateExpirationEnum.SHIFT.getCode())) {
                    long startTime = userCouponVO.getGetTime().getTime() / 1000;
                    long endTime = userCouponVO.getGetTime().getTime() + new DateTime(userCouponVO.getGetTime().getTime()).plusDays(userCouponVO.getCouponTemplate().getExpirationGap()).toDate().getTime();
                    userCouponVO.setStartTime(startTime);
                    userCouponVO.setEndTime(endTime);
                }
                voList.add(userCouponVO);
            }
            return voList;
        } else {

        }
        return null;
    }

    @Override
    public ApiCouponSettlementVO settlementCheckedGoods(ApiCouponSettlementDTO settlementDTO) {
        Integer cartId = settlementDTO.getCartId();
        ApiCouponSettlementVO settlementVO = new ApiCouponSettlementVO();
        //结算勾选的商品
        if (null == cartId) {

            //从已经勾选的购物车中找到商品，然后和可用的优惠券做规则筛选
            ResponseVO<ListVO<ApiUserCartVO>> checkedGoods = cartClient.findCheckedGoods(settlementDTO.getUid());
            if (!checkedGoods.isOk()) {
                throw new BizException(checkedGoods.getMsg());
            }
            List<ApiUserCartVO> cartList = checkedGoods.getData().getList();
            Map<Integer, ApiUserCartVO> cartIdToCartMap = cartList.stream().collect(Collectors.toMap(ApiUserCartVO::getId, cart -> cart));
            List<Integer> skuIdList = cartList.stream().map(ApiUserCartVO::getSkuId).collect(Collectors.toList());
            ResponseVO<ListVO<ApiGoodsSkuVO>> skuListVORes = goodsClient.findBySkuList(new ApiSkuIdListQueryDTO(skuIdList));

            if (!skuListVORes.isOk()) {
                throw new BizException(skuListVORes.getMsg());
            }
            List<ApiGoodsSkuVO> skuVOList = skuListVORes.getData().getList();
            Map<Integer, ApiGoodsSkuVO> skuIdToSkuMap = skuVOList.stream().collect(Collectors.toMap(ApiGoodsSkuVO::getId, sku -> sku));
            //没有勾选优惠券，找到优惠力度最大的
            Integer couponId = settlementDTO.getCouponId();
            if (null == couponId) {
                List<UserCouponBO> matchCouponList = getUserAvailableMatchGoodsCouponList(settlementDTO.getUid(), skuVOList);
                //matchCouponList是当前用户购物车中商品的匹配的优惠券,从中计算出优惠力度最大的


                BigDecimal totalGoodsPrice = BigDecimal.ZERO;
                //计算购物车中商品的总价
                for (ApiUserCartVO cartVO : cartList) {
                    totalGoodsPrice = totalGoodsPrice.add(cartVO.getPrice().multiply(BigDecimal.valueOf(cartVO.getNumber())));
                }
                settlementVO.setTotalGoodsPrice(totalGoodsPrice);
                if (CollectionUtils.isEmpty(matchCouponList)) {
                    settlementVO.setCost(totalGoodsPrice);
                    settlementVO.setUserAvailableCouponList(new ArrayList<>());
                    settlementVO.setDiscountPrice(BigDecimal.ZERO);
                    settlementVO.setEmploy(false);
//                    settlementVO.setTotalGoodsPrice(totalGoodsPrice);
                    return settlementVO;
                }


                //这里有多个优惠券分类，将立减、折扣、满减三类优惠券分类搞个集合装起来，然后分别从这三类集合中找到各自类别中优惠力度最大的，然后三个出来的在进行和商品总价格计算出优惠力度最大的
                CouponDiscountTypeClassify discountTypeClassify = CouponDiscountTypeClassify.classify(matchCouponList);

                UserCouponBO maxDiscountFromLijian = discountTypeClassify.getMaxDiscountFromLijian();
                UserCouponBO maxDiscountFromManjian = discountTypeClassify.getMaxDiscountFromManjian();
                UserCouponBO maxDiscountFromzhekou = discountTypeClassify.getMaxDiscountFromzhekou();

                //从这三类优惠券中和商品总价进行计算
                BigDecimal afterLijianPrice = null;
                BigDecimal afterManjianPrice = null;
                BigDecimal afterzhekouPrice = null;

                Map<BigDecimal, BigDecimal> afterDiscountMap = new HashMap<>(3);
                Map<BigDecimal, UserCouponBO> afterDiscountCouponMap = new HashMap<>(3);
                if (null != maxDiscountFromLijian) {
                    Integer lijianQuota = maxDiscountFromLijian.getCouponTemplateBO().getLijianQuota();
                    afterLijianPrice = totalGoodsPrice.subtract(BigDecimal.valueOf(lijianQuota));
                    afterDiscountMap.put(afterLijianPrice, BigDecimal.valueOf(lijianQuota));
                    afterDiscountCouponMap.put(afterLijianPrice, maxDiscountFromLijian);
                }

                if (null != maxDiscountFromManjian) {
                    if (totalGoodsPrice.compareTo(BigDecimal.valueOf(maxDiscountFromManjian.getCouponTemplateBO().getDiscountBase())) > 0) {
                        //达到满减基准
                        afterManjianPrice = totalGoodsPrice.subtract(BigDecimal.valueOf(maxDiscountFromManjian.getCouponTemplateBO().getManjianQuota()));
                        afterDiscountMap.put(afterManjianPrice, BigDecimal.valueOf(maxDiscountFromManjian.getCouponTemplateBO().getManjianQuota()));
                        afterDiscountCouponMap.put(afterManjianPrice, maxDiscountFromManjian);
                    }
                }

                if (null != maxDiscountFromzhekou) {
                    Integer zhekouQuota = maxDiscountFromzhekou.getCouponTemplateBO().getZhekouQuota();
                    afterzhekouPrice = totalGoodsPrice.multiply(BigDecimal.valueOf(zhekouQuota).multiply(BigDecimal.valueOf(1.0)).divide(BigDecimal.valueOf(100)));
                    afterzhekouPrice = totalGoodsPrice.subtract(afterzhekouPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                    BigDecimal subtract = totalGoodsPrice.subtract(afterzhekouPrice);
                    afterDiscountMap.put(afterzhekouPrice, subtract);
                    afterDiscountCouponMap.put(afterzhekouPrice, maxDiscountFromzhekou);
                }


                BigDecimal afterMaxDiscountPrice = BigDecimal.valueOf(Integer.MAX_VALUE);

                List<BigDecimal> bigDecimals = Arrays.asList(afterLijianPrice, afterManjianPrice, afterzhekouPrice);
                for (BigDecimal price : bigDecimals) {
                    if (price != null && price.compareTo(afterMaxDiscountPrice) < 0) {
                        afterMaxDiscountPrice = price;
                    }

                }

                settlementVO.setCost(afterMaxDiscountPrice);
                if (afterDiscountMap.containsKey(afterMaxDiscountPrice)) {
                    settlementVO.setDiscountPrice(afterDiscountMap.get(afterMaxDiscountPrice));
                }
                if (afterDiscountCouponMap.containsKey(afterMaxDiscountPrice)) {
                    Long id = afterDiscountCouponMap.get(afterMaxDiscountPrice).getId();
                    settlementVO.setMaxDiscountCouponId(id.intValue());
                }
                List<ApiUserCouponVO> voList = new ArrayList<>();
                for (UserCouponBO userCouponBO : matchCouponList) {
                    ApiUserCouponVO userCouponVO = new ApiUserCouponVO();
                    BeanUtils.copyProperties(userCouponBO, userCouponVO);
                    ApiCouponTemplateVO templateVO = new ApiCouponTemplateVO();
                    BeanUtils.copyProperties(userCouponBO.getCouponTemplateBO(), templateVO);
                    userCouponVO.setCouponTemplate(templateVO);
                    voList.add(userCouponVO);
                }
                settlementVO.setUserAvailableCouponList(voList);
                return settlementVO;
            } else {
                //从已经勾选的购物车中找到商品，然后和给定的优惠券做规则筛选

                UserCouponBO userCouponBO = getUserCouponById(settlementDTO.getUid(), couponId, UserCouponStatusEnum.USABLE.getCode());
                UserSettlementDTO settlementDTO1 = new UserSettlementDTO();
                settlementDTO1.setEmploy(false);
                List<ProductDTO> productDTOList = new ArrayList<>();
                for (ApiUserCartVO cartVO : cartList) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setPrice(cartVO.getPrice());
                    productDTO.setCount(cartVO.getNumber().intValue());
                    Integer skuId = cartVO.getSkuId();
                    if (skuIdToSkuMap.containsKey(skuId)) {
                        productDTO.setType(skuIdToSkuMap.get(skuId).getGoods().getCategoryId());
                    }
                    productDTOList.add(productDTO);
                }
                settlementDTO1.setProductDTOList(productDTOList);
                settlementDTO1.setUserCouponIdList(Collections.singletonList(userCouponBO.getId()));
                UserSettlementBO settlement;
                if (userCouponBO.getCouponTemplateBO().getDiscountCategory().equals(CouponDiscountCategoryEnum.ZHEKOU.getCode())) {
                    settlement = zhekouSettlementStrategy.settlement(settlementDTO1);
                } else if (userCouponBO.getCouponTemplateBO().getDiscountCategory().equals(CouponDiscountCategoryEnum.MANJIAN.getCode())) {
                    settlement = manjianSettlementStrategy.settlement(settlementDTO1);
                } else {
                    settlement = lijianSettlementStrategy.settlement(settlementDTO1);
                }
                settlementVO.setDiscountPrice(settlement.getTotalGoodsPrice().subtract(settlement.getCost()));
                settlementVO.setCost(settlement.getCost());
                List<UserCouponBO> userAvailableMatchGoodsCouponList = getUserAvailableMatchGoodsCouponList(settlementDTO.getUid(), skuVOList);
                List<ApiUserCouponVO> voList = new ArrayList<>();
                for (UserCouponBO bo : userAvailableMatchGoodsCouponList) {
                    ApiUserCouponVO userCouponVO = new ApiUserCouponVO();
                    BeanUtils.copyProperties(bo, userCouponVO);
                    ApiCouponTemplateVO templateVO = new ApiCouponTemplateVO();
                    BeanUtils.copyProperties(bo.getCouponTemplateBO(), templateVO);
                    userCouponVO.setCouponTemplate(templateVO);
                    voList.add(userCouponVO);
                }
                settlementVO.setUserAvailableCouponList(voList);
                settlementVO.setTotalGoodsPrice(settlement.getTotalGoodsPrice());
                return settlementVO;
            }
        } else {
            //TODO
            Integer couponId = settlementDTO.getCouponId();
            //从已经勾选的购物车中找到商品，然后和可用的优惠券做规则筛选
            if (null == couponId) {

            } else {
                //从已经勾选的购物车中找到商品，然后和给定的优惠券结算优惠
            }
        }
        return null;
    }

    public UserCouponBO getUserCouponById(Integer uid, Integer couponId, Integer status) {
        PageListBO<UserCouponBO> userCouponBOPageListBO = listByUidAndType((long) uid, status, new QueryPageDTO().setPageNo(1).setPageSize(Integer.MAX_VALUE));
        List<UserCouponBO> list = userCouponBOPageListBO.getList();
        return list.stream().filter(coupon -> coupon.getId().equals((long) couponId)).findFirst().orElseThrow(() -> new BizException("优惠券不存在"));
    }
}

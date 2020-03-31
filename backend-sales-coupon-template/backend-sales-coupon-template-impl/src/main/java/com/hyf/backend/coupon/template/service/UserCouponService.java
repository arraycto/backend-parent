package com.hyf.backend.coupon.template.service;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.domain.QueryPageDTO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface UserCouponService {

    /**
     * 根据用户id和优惠券状态获得用户优惠券列表
     *
     * @param uid
     */
    PageListBO<UserCouponBO> listByUidAndType(Long uid, Integer status, QueryPageDTO queryPageDTO);

    /**
     * 根据用户ID查找可以领取的优惠券模板
     * @param uid
     * @return
     */
    PageListBO<UserCouponBO> findAvailableTemplate(Long uid);

    /**
     * 用户领取优惠券
     * @param userId
     * @param templateId
     * @return
     */
    UserCouponBO acquireCoupon(Long userId, Long templateId);


}

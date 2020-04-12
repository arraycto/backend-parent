package com.hyf.backend.coupon.template.service;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/1
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface UserCouponCacheService {
    /**
     * 从缓存总获取用户的优惠券
     *
     * @param uid
     * @param status
     * @return
     */
    List<UserCouponBO> getUserCoupon(Long uid, Integer status);

    PageListBO<UserCouponBO> getUserCouponPage(Long uid, Integer status, Integer pageNo, Integer pageSize);

    /**
     * 添加优惠券到缓存中
     *
     * @param uid
     * @param status
     * @return
     */
    boolean addCoupon(Long uid, Integer status, List<UserCouponBO> userCouponBO);

    UserCouponBO getUserCouponSingle(Integer uid, Integer couponId, Integer status);
}

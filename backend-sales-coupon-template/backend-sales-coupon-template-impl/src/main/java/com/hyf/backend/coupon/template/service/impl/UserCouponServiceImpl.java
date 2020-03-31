package com.hyf.backend.coupon.template.service.impl;

import com.hyf.backend.common.domain.PageListBO;
import com.hyf.backend.common.domain.QueryPageDTO;
import com.hyf.backend.coupon.template.bo.UserCouponBO;
import com.hyf.backend.coupon.template.mapper.UserCouponDOMapper;
import com.hyf.backend.coupon.template.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Elvis on 2020/3/31
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {
    @Autowired
    private UserCouponDOMapper userCouponDOMapper;

    @Override
    public PageListBO<UserCouponBO> listByUidAndType(Long uid, Integer status, QueryPageDTO queryPageDTO) {
        return null;
    }

    @Override
    public PageListBO<UserCouponBO> findAvailableTemplate(Long uid) {
        return null;
    }

    @Override
    public UserCouponBO acquireCoupon(Long userId, Long templateId) {
        return null;
    }
}

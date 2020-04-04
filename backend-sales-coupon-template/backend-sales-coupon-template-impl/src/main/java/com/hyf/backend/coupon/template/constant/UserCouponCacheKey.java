package com.hyf.backend.coupon.template.constant;

/**
 * @Author: Elvis on 2020/4/1
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class UserCouponCacheKey {

    public static final String USER_COUPON_USABLE_CACHE = "user_coupon_%s_usable";

    public static final String USER_COUPON_USED_CACHE = "user_coupon_%s_used";

    public static final String USER_COUPON_EXPIRED_CACHE = "user_coupon_%s_expired";

    public static final String USER_COUPON_USABLE_CACHE_ZSET = "user_coupon_%s_usable_zset";

    public static final String USER_COUPON_USED_CACHE_ZSET = "user_coupon_%s_used_zset";

    public static final String USER_COUPON_EXPIRED_CACHE_ZSET = "user_coupon_%s_expired_zset";
}

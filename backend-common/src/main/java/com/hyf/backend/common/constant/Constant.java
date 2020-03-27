package com.hyf.backend.common.constant;

/**
 * @Author: Elvis on 2020/2/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Constant {

    //kafka消息的topic
    public static final String TOPIC = "user_coupon_op";

    public static final String X_UID = "x-uid";
    public static final String X_ADMIN_UID = "x-admin-uid";

    public static class RedisPrefix {

        //优惠券码的前缀
        public static final String COUPON_TEMPLATE = "coupon:template:code:";

        //用户当期那可用的优惠券key前缀
        public static final String USER_COUPON_USABLE = "user:coupon:usable:";

        //用户当前所有已使用的优惠券前缀
        public static  final String USER_COUPON_USED = "user:coupon:used:";

        public static final String USER_EXPIRED = "user:coupon:expired:";
    }

}

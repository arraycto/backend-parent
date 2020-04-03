package com.hyf.backend.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Elvis on 2020/4/3
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponExpiredMessage {
    List<Long> ids;
    private Integer status;
}

package com.hyf.backend.coupon.template.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Elvis on 2020/4/7
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiBannerVO {
    private Integer id;

    private String name;

    private String link;

    private String url;

    private Byte position;

    private String content;
}

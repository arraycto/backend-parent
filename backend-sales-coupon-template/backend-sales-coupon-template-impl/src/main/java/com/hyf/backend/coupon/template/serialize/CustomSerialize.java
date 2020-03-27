package com.hyf.backend.coupon.template.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hyf.backend.coupon.template.dataobject.CouponTemplateDO;

import java.io.IOException;

/**
 * @Author: Elvis on 2020/2/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class CustomSerialize extends JsonSerializer<CouponTemplateDO> {
    @Override
    public void serialize(CouponTemplateDO salesCouponTemplate,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

    }
}

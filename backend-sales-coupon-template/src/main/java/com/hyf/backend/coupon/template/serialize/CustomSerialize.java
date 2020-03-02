package com.hyf.backend.coupon.template.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hyf.backend.coupon.template.entity.SalesCouponTemplate;

import java.io.IOException;

/**
 * @Author: Elvis on 2020/2/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class CustomSerialize extends JsonSerializer<SalesCouponTemplate> {
    @Override
    public void serialize(SalesCouponTemplate salesCouponTemplate,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

    }
}

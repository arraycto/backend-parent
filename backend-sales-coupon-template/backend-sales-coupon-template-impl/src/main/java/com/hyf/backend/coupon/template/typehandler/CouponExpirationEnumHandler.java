package com.hyf.backend.coupon.template.typehandler;

import com.hyf.backend.coupon.template.constant.CouponTemplateExpirationEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Elvis on 2020/3/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class CouponExpirationEnumHandler extends BaseTypeHandler<CouponTemplateExpirationEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CouponTemplateExpirationEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public CouponTemplateExpirationEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return CouponTemplateExpirationEnum.of(rs.getInt(columnName));
    }

    @Override
    public CouponTemplateExpirationEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return CouponTemplateExpirationEnum.of(rs.getInt(columnIndex));
    }

    @Override
    public CouponTemplateExpirationEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CouponTemplateExpirationEnum.of(cs.getInt(columnIndex));
    }
}

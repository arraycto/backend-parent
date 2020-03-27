package com.hyf.backend.coupon.template.typehandler;

import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
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
public class CouponDiscountCategoryEnumHandler extends BaseTypeHandler<CouponDiscountCategoryEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CouponDiscountCategoryEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public CouponDiscountCategoryEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return CouponDiscountCategoryEnum.of(rs.getInt(columnName));
    }

    @Override
    public CouponDiscountCategoryEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return CouponDiscountCategoryEnum.of(rs.getInt(columnIndex));
    }

    @Override
    public CouponDiscountCategoryEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CouponDiscountCategoryEnum.of(cs.getInt(columnIndex));
    }
}

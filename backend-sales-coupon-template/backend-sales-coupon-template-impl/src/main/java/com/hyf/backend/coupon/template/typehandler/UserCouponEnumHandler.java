package com.hyf.backend.coupon.template.typehandler;

import com.hyf.backend.coupon.template.constant.CouponDiscountCategoryEnum;
import com.hyf.backend.coupon.template.constant.UserCouponStatusEnum;
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
public class UserCouponEnumHandler extends BaseTypeHandler<UserCouponStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserCouponStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public UserCouponStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return UserCouponStatusEnum.of(rs.getInt(columnName));
    }

    @Override
    public UserCouponStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return UserCouponStatusEnum.of(rs.getInt(columnIndex));
    }

    @Override
    public UserCouponStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return UserCouponStatusEnum.of(cs.getInt(columnIndex));
    }
}

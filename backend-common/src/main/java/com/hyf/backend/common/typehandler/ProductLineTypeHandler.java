package com.hyf.backend.common.typehandler;

import com.hyf.backend.common.constant.ProductLineEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Elvis on 2020/2/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ProductLineTypeHandler extends BaseTypeHandler<ProductLineEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ProductLineEnum productLineEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, productLineEnum.getCode());
    }

    @Override
    public ProductLineEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        return ProductLineEnum.of(code);
    }

    @Override
    public ProductLineEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        return ProductLineEnum.of(code);
    }

    @Override
    public ProductLineEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return ProductLineEnum.of(code);
    }
}

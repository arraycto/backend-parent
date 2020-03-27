package com.hyf.backend.common.mybatis.typehandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/26
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class JsonListIntegerTypeHandler extends BaseTypeHandler<List<Integer>> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public List<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }

    @Override
    public List<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }

    @Override
    public List<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }

    private String toJson(List<Integer> params) {
        try {
            return mapper.writeValueAsString(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "[]";
    }

    private List<Integer> toObject(String content) {
        if (content != null && !content.isEmpty()) {
            try {
                return (List<Integer>) mapper.readValue(content, List.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}

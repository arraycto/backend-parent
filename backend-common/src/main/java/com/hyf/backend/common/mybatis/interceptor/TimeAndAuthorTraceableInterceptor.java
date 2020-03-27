package com.hyf.backend.common.mybatis.interceptor;

import com.hyf.backend.common.constant.Constant;
import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.common.domain.DateAndAuthorTraceable;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: Elvis on 2020/3/27
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Order(1)
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class TimeAndAuthorTraceableInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object arg = invocation.getArgs()[1];

        if (arg instanceof DateAndAuthorTraceable) {
            DateAndAuthorTraceable traceable = (DateAndAuthorTraceable) arg;
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            Date time = new Date();
            Integer adminUserId = Integer.valueOf(ContextHolder.getCurrentContext().get(Constant.X_ADMIN_UID));
            if (sqlCommandType.equals(SqlCommandType.INSERT)) {
                traceable.setCreateUser(adminUserId);
                traceable.setUpdateUser(adminUserId);
                traceable.setCreateTime(time);
                traceable.setUpdateTime(time);
            } else if (sqlCommandType.equals(SqlCommandType.UPDATE)) {
                traceable.setUpdateUser(adminUserId);
                traceable.setUpdateTime(time);
            }
        }

        return invocation.proceed();
    }
}

package com.hyf.backend.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.hyf.backend.utils.common.vo.ResponseVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class AbstractZuulFilter extends ZuulFilter {

    //从请求到相应的变量都绑定在里面
    protected RequestContext requestContext;

    //决定是否继续执行下一个过滤器
    private final static String NEXT = "next";

    @Override
    public boolean shouldFilter() {
        //NEXT变量的值是由上一个过滤器设置进去的，在本过滤器总取出来决定是否要继续执行
        RequestContext ctx = RequestContext.getCurrentContext();
        //如果不包含就继续向下执行
        return (boolean) ctx.getOrDefault(NEXT, true);
    }

    @Override
    public Object run() throws ZuulException {
        requestContext = RequestContext.getCurrentContext();
        return doRun();
    }

    protected abstract Object doRun();

    /**
     * 交由子过滤器调用
     *
     * @return
     */
    Object fail(int httpStatusCode, int bizCode, String errorMsg) {
        //NEXT变量相当于会不会向下执行
        requestContext.set(NEXT, false);
        //不再执行其他的过滤器和逻辑了
        requestContext.setSendZuulResponse(false);
        requestContext.getResponse().setContentType("application/json;charset=utf-8");
        requestContext.setResponseStatusCode(httpStatusCode);
        ResponseVO<String> responseVO = new ResponseVO<>();
        responseVO.setCode(bizCode);
        responseVO.setData(null);
        responseVO.setMsg(errorMsg);
        requestContext.setResponseBody(JSON.toJSONString(responseVO));

        return null;
    }

    /**
     * 交由子过滤器调用
     *
     * @return
     */
    Object success() {
        requestContext.set(NEXT, true);
        return null;
    }
}

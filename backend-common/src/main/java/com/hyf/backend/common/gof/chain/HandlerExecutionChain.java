package com.hyf.backend.common.gof.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */

public class HandlerExecutionChain {

    public static Logger logger = LoggerFactory.getLogger(HandlerExecutionChain.class);
    private List<HandlerInterceptor> handlerInterceptorList = new ArrayList<>();
    private Method method;
    private int interceptorIndex = -1;

    public void addInterceptor(HandlerInterceptor interceptor) {
        handlerInterceptorList.add(interceptor);
    }

    public boolean applyPreHandler(String msg) {
        for (int i = 0; i < handlerInterceptorList.size(); ++i) {
            HandlerInterceptor handlerInterceptor = handlerInterceptorList.get(i);
            if (!handlerInterceptor.applyPreHandler(msg, method)) {
                triggerAfterCompletion(msg, null);
                return false;
            }
            interceptorIndex = i;
        }

        return true;
    }

    public void applyPostHandler(String msg) {
        for (int i = handlerInterceptorList.size() - 1; i >= 0; i--) {
            HandlerInterceptor handlerInterceptor = handlerInterceptorList.get(i);
            handlerInterceptor.applyPostHandler(msg, method);
        }
    }

    private void triggerAfterCompletion(String msg, Throwable o) {
        for (int i = interceptorIndex; i >= 0; i--) {
            HandlerInterceptor handlerInterceptor = handlerInterceptorList.get(i);
            try {
                handlerInterceptor.afterCompletion(msg, method, o);
            } catch (Throwable e1) {
                logger.error("HandlerInterceptor.afterCompletion threw exception", e1);
            }
        }
    }


    public List<HandlerInterceptor> getHandlerInterceptorList() {
        return handlerInterceptorList;
    }

    public void setHandlerInterceptorList(List<HandlerInterceptor> handlerInterceptorList) {
        this.handlerInterceptorList = handlerInterceptorList;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public static void testMethod() {
        System.out.println("目标方法执行了");
    }

    public static void main(String[] args) throws NoSuchMethodException {
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain();
        handlerExecutionChain.addInterceptor(new HandlerInterceptorA());
        handlerExecutionChain.addInterceptor(new HandlerInterceptorB());

        Method testMethod = HandlerExecutionChain.class.getMethod("testMethod", null);
        handlerExecutionChain.setMethod(testMethod);
        try {
            handlerExecutionChain.applyPreHandler("前置处理器");
            testMethod();
            handlerExecutionChain.applyPostHandler("后置处理器");
            handlerExecutionChain.triggerAfterCompletion("最终处理器", null);
        } catch (Throwable e) {
            handlerExecutionChain.triggerAfterCompletion("出错了", e);
            logger.error("出错了...", e);
        }
    }
}

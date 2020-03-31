package com.hyf.backend.common.strategy;

import com.hyf.backend.common.context.ContextHolder;
import com.hyf.backend.common.context.RequestContext;

import java.util.concurrent.Callable;


public class AuthCallable<V> implements Callable<V> {

    private final Callable<V> delegate;

    private final RequestContext requestContext;

    public AuthCallable(Callable<V> delegate, RequestContext requestContext) {
        this.delegate = delegate;
        this.requestContext = requestContext;
    }

    @Override
    public V call() throws Exception {
        try {
            //这里是HystrixCommand中的线程了
            ContextHolder.setCurrentContext(requestContext);
            return this.delegate.call();
        } catch (Exception | Error ex) {
            throw ex;
        }
    }

}
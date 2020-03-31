package com.hyf.backend.springsource.service;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Elvis on 2020/3/29
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Component
@Aspect
public class TryAgainAspect {

    private static final int DEFAULT_MAX_RETRIES = 100;

    private int maxRetries = DEFAULT_MAX_RETRIES;

    @Pointcut("@annotation(IsTryAgain)")
    public void retryOnOptFailure() {

    }

    @Around("retryOnOptFailure()")
    @Transactional(rollbackFor = Exception.class)
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        do {
            numAttempts++;
            try {
                //再次执行业务代码
                return pjp.proceed();
            } catch (TryAgainException ex) {
                if (numAttempts > maxRetries) {
                    //log failure information, and throw exception
//					如果大于 默认的重试机制 次数，我们这回就真正的抛出去了
                    throw new ApiException("超过默认重试次数");
                } else {
                    //如果 没达到最大的重试次数，将再次执行
                    System.out.println("=====正在重试=====" + numAttempts + "次");
                }
            }
        } while (numAttempts <= this.maxRetries);

        return null;
    }
}

package com.hyf.backend.zuul.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class AbstractPostFilter extends AbstractZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }
}

package com.hyf.backend.common.config;

import com.hyf.backend.common.filter.HttpHeaderAdminCreateIdFilter;
import com.hyf.backend.common.filter.HttpHeaderUidFilter;
import com.hyf.backend.common.web.LoginRequiredInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/2/25
 * @Email: yfelvis@gmail.com
 * @Desc: web通用配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginRequiredInterceptor()).addPathPatterns("/api/**");
    }

    @Bean
    public FilterRegistrationBean<HttpHeaderUidFilter> httpHeadUidFilter() {
        FilterRegistrationBean<HttpHeaderUidFilter> registrationBean = new FilterRegistrationBean<>();
        HttpHeaderUidFilter httpHeaderParamFilter = new HttpHeaderUidFilter();
        registrationBean.setFilter(httpHeaderParamFilter);
        List<String> urlPatterns = new ArrayList<>(1);
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<HttpHeaderAdminCreateIdFilter> httpAdminIdFilter() {
        FilterRegistrationBean<HttpHeaderAdminCreateIdFilter> registrationBean = new FilterRegistrationBean<>();
        HttpHeaderAdminCreateIdFilter httpHeaderParamFilter = new HttpHeaderAdminCreateIdFilter();
        registrationBean.setFilter(httpHeaderParamFilter);
        List<String> urlPatterns = new ArrayList<>(1);
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}

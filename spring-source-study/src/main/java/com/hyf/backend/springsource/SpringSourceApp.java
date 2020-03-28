package com.hyf.backend.springsource;

import com.hyf.backend.springsource.test1.ColorConfiguration;
import com.hyf.backend.springsource.test1.EnableColor;
import com.hyf.backend.springsource.test1.Red;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: Elvis on 2020/3/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
public class SpringSourceApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ColorConfiguration.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String beanDefinitionName:beanDefinitionNames) {
            log.info("beanDefinition: {}", beanDefinitionName);
        }
    }
}

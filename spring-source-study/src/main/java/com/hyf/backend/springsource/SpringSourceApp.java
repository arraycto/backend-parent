package com.hyf.backend.springsource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Elvis on 2020/3/28
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
@Slf4j
@MapperScan(basePackages = {"com.hyf.backend.springsource.mapper"})
@EnableTransactionManagement
@SpringBootApplication
@RestController
public class SpringSourceApp {

    @GetMapping("/test")
    public String test(@Param("p") String p) {
        return p;
    }
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ColorConfiguration.class);
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        for(String beanDefinitionName:beanDefinitionNames) {
//            log.info("beanDefinition: {}", beanDefinitionName);
//        }
        List<SmartApplicationListener> listeners = new ArrayList<>();
        listeners.add(new SmartApplicationListener() {
            @Override
            public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
                return eventType.equals(ContextRefreshedEvent.class);
            }

            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                log.info("Spring容器刷完了....");
            }
        });
        SmartApplicationListener[] springApplications = listeners.toArray(new SmartApplicationListener[0]);
        new SpringApplicationBuilder(SpringSourceApp.class).web(WebApplicationType.SERVLET).listeners(springApplications).banner(new Banner() {
            @Override
            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
                log.info("hyfhyf打印Banner");
                out.println("hyfhyf打印Banner");
            }
        }).run(args);
    }
}

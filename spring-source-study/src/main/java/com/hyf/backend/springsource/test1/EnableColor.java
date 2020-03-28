package com.hyf.backend.springsource.test1;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Import(value = {Red.class, ColorRegistarConfiguration.class, ColorImportSelector.class, ColorImportBeanDefinitionRegistar.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableColor {
}

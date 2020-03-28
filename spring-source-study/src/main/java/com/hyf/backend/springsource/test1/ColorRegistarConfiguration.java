package com.hyf.backend.springsource.test1;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ColorRegistarConfiguration {
    @Bean
    public Yellow yellow(){
        return new Yellow();
    }
}

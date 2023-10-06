package com.edu.infrastructure.config;

import com.edu.infrastructure.config.filter.BeforeLoginLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Deprecated
    @Bean
    public FilterRegistrationBean<BeforeLoginLogFilter> beforeLoginLogFilter(){
        FilterRegistrationBean<BeforeLoginLogFilter> bean = new FilterRegistrationBean<BeforeLoginLogFilter>(new BeforeLoginLogFilter());
        bean.addUrlPatterns("/*"); // 모든 url 접근 시
        bean.setOrder(0); // order 순서가 낮을 수록 filter중 가장 먼저 실행된다.
        return bean;
    }
}

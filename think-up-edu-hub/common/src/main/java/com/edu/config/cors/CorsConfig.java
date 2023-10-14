package com.edu.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    /**
     * cors 조건을 security filter에 넣기 위해 생성
     */
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 응답 시 json을 return 시 자바스크립트로 처리 가능하게
        config.addAllowedOrigin("*"); // 모든 ip에서 가능하게
        config.addAllowedHeader("*"); // 모든 header에서 가능하게
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 가능하게
        source.registerCorsConfiguration("/",config);
        return new CorsFilter(source);
    }

}

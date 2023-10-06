package com.edu.infrastructure.config;

import com.edu.infrastructure.config.filter.BeforeLoginLogFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity // security 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter CorsFilter; // corsConfig에서 빈으로 설정한 것

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // state less 방식, session 사용 x
                .and()
                .addFilter(CorsFilter)
                .formLogin().disable() // 스프링 시큐리티에서 제공하는 로그인 폼을 사용하지 않을 것이므로 disable
                // http 로그인 방식 사용x, headers에서 Authorization ID: ~~ / pwd: ~~ 이런식으로 암호화 안된 상태로 날라감. 보안 취약
                // 그래서 jwt로 Bearer 방식 사용할 것, Bearer은 토큰 형식으로 날아가게 하는 것
                .httpBasic().disable()
                .authorizeRequests() // Http 요청에 대한 접근권한 설정 메서드 특정 url 설정할 수 있으나 나는 추가 설정 x
                .anyRequest().authenticated() // 나머지 모든 요청은 인증이 필요하다.
                .and()
                .addFilterBefore(new BeforeLoginLogFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

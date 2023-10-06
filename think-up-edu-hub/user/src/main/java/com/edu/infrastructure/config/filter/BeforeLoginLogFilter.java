package com.edu.infrastructure.config.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class BeforeLoginLogFilter implements Filter {

    @Override
    @Deprecated
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("{} 호출한 IP",request.getRemoteAddr());
        chain.doFilter(request,response); // 커스텀 필터가 끝나고 다음 필터가 진행되게 하려면 꼭 해주어야한다.
    }
}

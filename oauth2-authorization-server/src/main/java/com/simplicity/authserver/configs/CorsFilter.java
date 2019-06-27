package com.simplicity.authserver.configs;

import lombok.SneakyThrows;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {
        @Override
        @SneakyThrows
        protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
            if (req.getServletPath().equals("/oauth/token")) {
                res.addHeader("Access-Control-Allow-Origin", "*");
                res.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
                res.addHeader("Access-Control-Max-Age", "3600");
                res.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
                if (req.getMethod().equals(HttpMethod.OPTIONS.name())) {
                    res.setStatus(HttpServletResponse.SC_OK);
                } else {
                    chain.doFilter(req, res);
                }
            } else {
                chain.doFilter(req, res);
            }
        }
}

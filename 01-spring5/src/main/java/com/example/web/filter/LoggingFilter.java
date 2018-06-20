package com.example.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("======================================");
        System.out.println("## REQUEST");
        String requestMethodAndUri = httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI();
        System.out.println(requestMethodAndUri);
        for (Enumeration<String> headerNames = httpServletRequest.getHeaderNames(); headerNames.hasMoreElements();) {
            String headerName = headerNames.nextElement();
            String headerValue = httpServletRequest.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }
        System.out.println("======================================");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        System.out.println("======================================");
        System.out.println("## RESPONSE (for " + requestMethodAndUri + ")");
        System.out.println(httpServletResponse.getStatus());
        for (String headerName : httpServletResponse.getHeaderNames()) {
            String headerValue = httpServletResponse.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }
        System.out.println("======================================");
    }}

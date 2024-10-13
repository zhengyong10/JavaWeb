package com.example.filter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    // 存储不需要登录就能访问的路径的列表
    private static final List<String> excludedPaths = Arrays.asList(
            "/hello-servlet", "/Login.html", "/LoginServlet", "/Welcome", ".gif", ".ico", ".html", ".jsp"
    );
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 检查当前请求路径是否在排除列表中
        if (isExcludedPath(request.getRequestURI())) {
            // 如果在排除列表中，允许请求通过
            System.out.println("通行");
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session!= null && session.getAttribute("user")!= null) {
            // 如果用户已登录，允许请求继续
            filterChain.doFilter(request, response);
        } else {
            // 如果用户未登录，将请求重定向到登录页面
            response.sendRedirect(request.getContextPath()+"/Login.html");
        }
    }

    private boolean isExcludedPath(String requestURI) {
        // 判断当前请求路径是否在排除列表中
        for (String path : excludedPaths) {
            if (requestURI.equals( "/Filter" + path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
        // 销毁方法，可以在这里进行一些清理操作
    }
}
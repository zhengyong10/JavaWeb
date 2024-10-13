package com.example.demo;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@WebListener
public class RequestLogger implements ServletRequestListener {

    // 创建日志记录器
    private static final Logger logger = Logger.getLogger(RequestLogger.class.getName());
    private static FileHandler fileHandler;

    static {
        try {
            // 配置日志文件处理器，指定日志文件名称并设置为追加模式
            fileHandler = new FileHandler("request_log.txt", true);
            // 设置日志格式为简单格式，便于阅读
            fileHandler.setFormatter(new SimpleFormatter());
            // 将文件处理器添加到日志记录器中
            logger.addHandler(fileHandler);
            // 设置日志级别为 INFO
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        // 获取客户端 IP 地址
        String clientIp = request.getRemoteAddr();
        // 获取请求方法（GET、POST 等）
        String requestMethod = request.getMethod();
        // 获取请求 URI
        String requestUri = request.getRequestURI();
        // 获取查询字符串，如果有
        String queryString = request.getQueryString();
        // 获取 User-Agent
        String userAgent = request.getHeader("User-Agent");

        // 记录请求开始时的信息到日志
        logger.info(String.format("请求时间：%s，客户端 IP 地址：%s，请求方法：%s，请求 URI：%s，查询字符串：%s，User-Agent：%s",
                new Date(), clientIp, requestMethod, requestUri, queryString, userAgent));
        // 将开始时间存储在请求属性中，以便在请求结束时获取
        request.setAttribute("startTime", startTime);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        // 从请求属性中获取请求开始时间
        long startTime = (long) request.getAttribute("startTime");
        // 获取当前时间作为请求结束时间
        long endTime = System.currentTimeMillis();
        // 计算请求处理时间
        long processingTime = endTime - startTime;
        // 记录请求处理时间到日志
        logger.info(String.format("请求处理时间：%d 毫秒", processingTime));
    }
}

package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/test")

public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 获取请求开始时间并格式化为字符串（假设在 ServletRequestListener 中设置了 startTime 属性）
        long startTime = (long) request.getAttribute("startTime");
        Date startDate = new Date(startTime);
        String startTimeStr = startDate.toString();

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

        // 获取请求结束时间并计算处理时间
        long endTime = System.currentTimeMillis();
        long processingTime = endTime - startTime;

        out.println("<html><body>");
        out.println("请求时间：" + startTimeStr + "<br>");
        out.println("客户端 IP 地址：" + clientIp + "<br>");
        out.println("请求方法：" + requestMethod + "<br>");
        out.println("请求 URI：" + requestUri + "<br>");
        out.println("查询字符串：" + queryString + "<br>");
        out.println("User-Agent：" + userAgent + "<br>");
        out.println("请求处理时间：" + processingTime + " 毫秒<br>");
        out.println("</body></html>");
    }
}

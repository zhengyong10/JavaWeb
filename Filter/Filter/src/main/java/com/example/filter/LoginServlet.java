package com.example.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 假设正确的用户名是 "user"，密码是 "123456"
        boolean isUsernameCorrect = "user".equals(username);
        boolean isPasswordCorrect = "123456".equals(password);

        if (isUsernameCorrect && isPasswordCorrect) {
            System.out.println("登录成功");
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            response.sendRedirect(request.getContextPath()+"/Welcome.html");
        } else {
            System.out.println("登录失败");
            response.sendRedirect(request.getContextPath()+"/Login.html");
        }
    }
}

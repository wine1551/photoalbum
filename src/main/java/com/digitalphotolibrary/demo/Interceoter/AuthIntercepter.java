package com.digitalphotolibrary.demo.Interceoter;

import com.digitalphotolibrary.demo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("preHandle请求URL：" + requestURL.toString());
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getIs_auth() == false) {
            //System.out.println(user.getIs_auth());
            response.sendRedirect("/error");
            return false;
        }
//            response.url
        return true;

    }
}

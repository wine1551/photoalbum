package com.digitalphotolibrary.demo.Interceoter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginIntercepter implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            StringBuffer requestURL = request.getRequestURL();
            System.out.println("preHandle请求URL：" + requestURL.toString());
            HttpSession session = request.getSession();
            if(session.getAttribute("user")==null ){
                response.sendRedirect("/login");
                //response.getWriter().write("请登录");
                return false;
            }
//            response.url
            return true;
        }
}

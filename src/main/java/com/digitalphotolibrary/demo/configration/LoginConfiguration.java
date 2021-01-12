package com.digitalphotolibrary.demo.configration;

import com.digitalphotolibrary.demo.Interceoter.AuthIntercepter;
import com.digitalphotolibrary.demo.Interceoter.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/pics/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("*.css")
                .excludePathPatterns("*.js")
                .excludePathPatterns("/auth")
                .excludePathPatterns("/auth/**");

        registry.addInterceptor((new AuthIntercepter()))
                .addPathPatterns("/auth")
                .addPathPatterns("/auth/**");
    }
}


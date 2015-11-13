
package com.br.squemasports.config;

import com.br.squemasports.config.thymeleaf.ThymeleafLayoutInterceptor;
import com.br.squemasports.viewmodel.SessionLoginViewModel;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
@ComponentScan(basePackages = { "com.br.squemasports.controller" })
@EnableWebMvc
@Import({ThymeleafConfig.class, DataConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
//        ClassLoader cl = ClassLoader.getSystemClassLoader();
//        
//        URL[] urls = ((URLClassLoader) cl).getURLs();
//        
//        System.out.println("CLASSPATHES");
//        for (URL url : urls) {
//            System.out.println(url.getFile());
//        }
        
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/**");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThymeleafLayoutInterceptor());
        registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                System.out.println("PRE HANDLE INTERCEPT: " + request.getRequestURI() + " - " + request.getSession().getAttribute(SessionLoginViewModel.SESSION));
                if (!"/login".equals(request.getRequestURI())) {
                    if (request.getSession().getAttribute(SessionLoginViewModel.SESSION) != null) {
                        System.out.println("REDIRECT TO LOGIN " + handler);
//                        response.sendRedirect("/login");
//                        return false;
                    }
                }
                return true;
            }
            
        });
        
//        registry.addWebRequestInterceptor(new WebRequestInterceptor() {
//            
//            @Override
//            public void preHandle(WebRequest wr) throws Exception {
//                System.out.println("PRE HANDLE Request: " + wr.getClass() + " :: " + wr.getDescription(true));
//                Object sessionLogin = wr.getAttribute(SessionLoginViewModel.SESSION, RequestAttributes.SCOPE_SESSION);
//                System.out.println("PRE HANDLE - " + sessionLogin);
//            }
//            
//            @Override
//            public void postHandle(WebRequest wr, ModelMap mm) throws Exception {
//                System.out.println("POST HANDLE Request: " + wr + " - ModelMap: " + mm);
//                Object sessionLogin = wr.getAttribute(SessionLoginViewModel.SESSION, RequestAttributes.SCOPE_SESSION);
//                System.out.println("POST HANDLE SessionLogin - " + sessionLogin);
//                if (sessionLogin != null && mm != null) {
//                    mm.addAttribute(SessionLoginViewModel.SESSION, sessionLogin);
//                }
//            }
//            
//            @Override
//            public void afterCompletion(WebRequest wr, Exception excptn) throws Exception {}
//        });
    }
    
}

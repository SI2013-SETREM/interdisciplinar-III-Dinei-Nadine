
package com.br.squemasports.config;

import com.br.squemasports.config.thymeleaf.ThymeleafLayoutInterceptor;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
    }
    
}

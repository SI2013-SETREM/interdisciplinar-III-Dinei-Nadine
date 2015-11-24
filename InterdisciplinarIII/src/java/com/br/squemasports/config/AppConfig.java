
package com.br.squemasports.config;

import com.br.squemasports.config.thymeleaf.ThymeleafLayoutInterceptor;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.SessionLoginViewModel;
import java.math.RoundingMode;
import java.util.Currency;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.PercentFormatter;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

@Configuration
@ComponentScan(basePackages = { "com.br.squemasports.controller" })
@EnableWebMvc
@Import({ThymeleafConfig.class, DataConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/**");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        PercentFormatter percent = new PercentFormatter();
        registry.addFormatter(percent);
        
        CurrencyFormatter currency = new MoneyFormatter();
        registry.addFormatter(currency);
        
        DateFormatter date = new DateFormatter("dd/mm/yyyy");
        registry.addFormatter(date);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThymeleafLayoutInterceptor());
        registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if (!"/login".equals(request.getRequestURI()) && !"/login/".equals(request.getRequestURI()) && !request.getRequestURI().contains("/ws/")) {
                    if (request.getSession().getAttribute(SessionLoginViewModel.SESSION) == null) {
                        
                        if (!"/".equals(request.getRequestURI())) {
                            FlashMap flashMap = new FlashMap();
                            flashMap.put(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ALERTA, "Por favor, faça login para ver a página"));
                            FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
                            flashMapManager.saveOutputFlashMap(flashMap, request, response);
                        }
                        
                        response.sendRedirect("/login");
                        return false;
                    }
                }
                return true;
            }
            
        });
        
    }
    
}

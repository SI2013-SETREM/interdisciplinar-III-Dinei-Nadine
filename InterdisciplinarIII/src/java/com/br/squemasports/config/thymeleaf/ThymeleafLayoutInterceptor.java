
package com.br.squemasports.config.thymeleaf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * From http://blog.codeleak.pl/2013/11/thymeleaf-template-layouts-in-spring.html
 */
public class ThymeleafLayoutInterceptor extends HandlerInterceptorAdapter {
 
    private static final String DEFAULT_LAYOUT = "layout/default";
    private static final String DEFAULT_VIEW_ATTRIBUTE_NAME = "view";
 
    private String defaultLayout = DEFAULT_LAYOUT;
    private String viewAttributeName = DEFAULT_VIEW_ATTRIBUTE_NAME;
 
    public void setDefaultLayout(String defaultLayout) {
        Assert.hasLength(defaultLayout);
        this.defaultLayout = defaultLayout;
    } 
    
    public void setViewAttributeName(String viewAttributeName) {
        Assert.hasLength(defaultLayout);
        this.viewAttributeName = viewAttributeName;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
        String originalViewName = modelAndView.getViewName();
        if (isRedirectOrForward(originalViewName)) {
            return;
        }
        String layoutName = getLayoutName(handler);
        modelAndView.setViewName(layoutName);
        modelAndView.addObject(this.viewAttributeName, originalViewName);
    }
 
    private boolean isRedirectOrForward(String viewName) {
        return viewName.startsWith("redirect:") || viewName.startsWith("forward:");
    }
 
    private String getLayoutName(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Layout layout = handlerMethod.getMethodAnnotation(Layout.class);
        if (layout == null) {
            layout = handlerMethod.getBeanType().getAnnotation(Layout.class);
        }
        if (layout == null) {
            return this.defaultLayout;
        } else {
            return layout.value();
        }
    }
    
}
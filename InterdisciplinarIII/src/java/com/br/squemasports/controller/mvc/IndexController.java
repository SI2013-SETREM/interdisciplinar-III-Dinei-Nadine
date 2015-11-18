
package com.br.squemasports.controller.mvc;

import com.br.squemasports.config.thymeleaf.Layout;
import com.br.squemasports.model.Produto;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String index() {
//        return "index";
        return "redirect:" + Produto.URL_MVC;
    }
    
    @Layout("layout/blank")
    @RequestMapping("/erro")
    public String erro(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        model.addAttribute("errorMessage", (throwable != null) ? throwable.getMessage() : null);
        return "erro";
    }
}


package com.br.squemasports.controller.mvc;

import com.br.squemasports.config.thymeleaf.Layout;
import com.br.squemasports.viewmodel.LoginViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    
    @Layout("layout/blank")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("titulo", "Login");
        return mv;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String doLogin(@RequestBody LoginViewModel lvm) {
    public String doLogin() {
        return "redirect:/";
    }
}

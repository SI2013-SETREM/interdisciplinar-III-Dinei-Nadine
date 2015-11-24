
package com.br.squemasports.controller.mvc;

import com.br.squemasports.config.thymeleaf.Layout;
import com.br.squemasports.dao.UsuarioRepository;
import com.br.squemasports.general.Util;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.Produto;
import com.br.squemasports.model.Usuario;
import com.br.squemasports.viewmodel.LoginViewModel;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.SessionLoginViewModel;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    
    @Autowired
    private UsuarioRepository repo;
    
    @Layout("layout/blank")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpSession session) {
        session.removeAttribute(SessionLoginViewModel.SESSION);
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("titulo", "Login");
        mv.addObject("loginVM", new LoginViewModel());
        return mv;
    }
    
    @RequestMapping(value = "/logoff", method = RequestMethod.GET)
    public ModelAndView logoff(HttpSession session, final RedirectAttributes redirectAttributes) {
        session.removeAttribute(SessionLoginViewModel.SESSION);
        ModelAndView mv = new ModelAndView();
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Saiu com sucesso"));
        mv.setViewName("redirect:/login");
        return mv;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public MV doLogin(@ModelAttribute LoginViewModel lvm, HttpSession session, final RedirectAttributes redirectAttributes) throws IOException {
        MV mv = new MV();
        
        Usuario usuario = null;
        if (lvm != null) {
            lvm.setLogin(Util.getString(lvm.getLogin()));
            lvm.setSenha(Util.getString(lvm.getSenha()));
            
            List<Usuario> lstUsuarios = repo.findByLogin(lvm.getLogin());
            Optional<Usuario> optUsuario = lstUsuarios.stream()
//                    .filter(u -> Util.md5(u.getSal() + lvm.getSenha()).equals(u.getSenha()))
                    .filter(u -> u.checkSenha(lvm.getSenha()))
                    .findFirst();
            usuario = optUsuario.orElse(null);
        }
        if (usuario != null) {
            SessionLoginViewModel slvm = new SessionLoginViewModel();
            slvm.setUsuarioId(usuario.getId());
            slvm.setUsuarioNome(usuario.getNome());
            slvm.setUsuarioLogin(usuario.getLogin());
            session.setAttribute(SessionLoginViewModel.SESSION, slvm);
            
            mv.setViewName("redirect:" + Produto.URL_MVC);
        } else {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha no login"));
            mv.setViewName("redirect:/login");
        }
        return mv;
    }
}

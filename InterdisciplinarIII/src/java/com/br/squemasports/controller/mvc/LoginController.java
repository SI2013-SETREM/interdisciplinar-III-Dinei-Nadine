
package com.br.squemasports.controller.mvc;

import com.br.squemasports.config.thymeleaf.Layout;
import com.br.squemasports.dao.UsuarioRepository;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.Usuario;
import com.br.squemasports.viewmodel.LoginViewModel;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.SessionLoginViewModel;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    
    @Autowired
    private UsuarioRepository repo;
    
    @Layout("layout/blank")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("titulo", "Login");
        mv.addObject("loginVM", new LoginViewModel());
        return mv;
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String doLogin(@RequestBody LoginViewModel lvm) {
    public void doLogin(@ModelAttribute LoginViewModel lvm, HttpServletRequest req, HttpServletResponse resp, final RedirectAttributes redirectAttributes) throws IOException {
//        ModelAndView mv = new ModelAndView("redirect:/");
//        mv.addObject("user", "USuário");
//        return mv;
        
        Usuario usuario = null;
        if (lvm != null) {
            lvm.setLogin(Util.getString(lvm.getLogin()));
            lvm.setSenha(Util.getString(lvm.getSenha()));
            
            List<Usuario> lstUsuarios = repo.findByLogin(lvm.getLogin());
            Optional<Usuario> optUsuario = lstUsuarios.stream()
                    .filter(u -> Util.md5(u.getSal() + lvm.getSenha()).equals(u.getSenha()))
                    .findFirst();
            usuario = optUsuario.orElse(null);
        }
        
        if (usuario != null) {
            SessionLoginViewModel slvm = new SessionLoginViewModel();
            slvm.setUsuarioId(usuario.getId());
            slvm.setUsuarioNome(usuario.getNome());
            slvm.setUsuarioLogin(usuario.getLogin());
            req.getSession().setAttribute(SessionLoginViewModel.SESSION, slvm);
            
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha no login"));
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}

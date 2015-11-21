package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.UsuarioRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.WsRequest;
import com.br.squemasports.model.Usuario;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.SessionLoginViewModel;
import com.br.squemasports.viewmodel.UsuarioViewModel;
import javax.servlet.http.HttpSession;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(Usuario.URL_MVC)
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository repo;
    
    @RequestMapping
    public MV list() {
        MV mv = new MV(Usuario.class, "listUsuario");
        mv.addObject("titulo", "Usuários");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(Usuario.class, "formUsuario");
        UsuarioViewModel documento = new UsuarioViewModel();
        if (id != null && ObjectId.isValid(id)) {
            Usuario usuario = repo.findOne(id);
            if (usuario != null) {
                usuario.setSenha(null);
                usuario.fill(documento);
                mv.addObject("titulo", "Usuário " + documento);
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        } else {
            mv.addObject("titulo", "Novo usuário");
        }
        mv.addObject("documento", documento);
        return mv;
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public MV password(HttpSession session) {
        MV mv = new MV(Usuario.class, "formTrocaSenha");
        mv.addObject("titulo", "Trocar senha");
        UsuarioViewModel documento = new UsuarioViewModel();
        SessionLoginViewModel sessionLogin = (SessionLoginViewModel) session.getAttribute(SessionLoginViewModel.SESSION);
        Usuario usuario = repo.findOne(sessionLogin.getUsuarioId());
        if (usuario != null) {
            usuario.setSenha(null);
            usuario.fill(documento);
        } else {
            mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Usuário de id '" + sessionLogin.getUsuarioId() + "' não encontrado"));
        }
        mv.addObject("documento", documento);
        return mv;
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute UsuarioViewModel uvm, HttpSession session, final RedirectAttributes redirectAttributes) {
        MensagemMVC msgMvc = new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Senha alterada com sucesso");
        
        SessionLoginViewModel sessionLogin = (SessionLoginViewModel) session.getAttribute(SessionLoginViewModel.SESSION);
        Usuario usuario = repo.findOne(sessionLogin.getUsuarioId());
        if (usuario != null) {
            if (uvm.checkSenha()) {
                if (usuario.checkSenha(uvm.getSenhaAntiga())) {
                    usuario.setSenha(uvm.getSenha());
                    repo.save(usuario);
                } else {
                    msgMvc = new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha na autenticação");
                }
            } else {
                msgMvc = new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "As senhas não conferem");
            }
        } else {
            msgMvc = new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Usuário de id '" + sessionLogin.getUsuarioId() + "' não encontrado");
        }
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, msgMvc);
        return "redirect:" + Usuario.URL_MVC + "/password";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute UsuarioViewModel uvm, final RedirectAttributes redirectAttributes) {
        String redirect = Usuario.URL_MVC;
        MensagemMVC msgMvc;
        try {
            Usuario usuario = new Usuario();
            if (id != null && ObjectId.isValid(id)) {
                usuario = repo.findOne(id);
            }
            msgMvc = uvm.fill(usuario);
            if (msgMvc.getGravidade() == MensagemMVC.GRAVIDADE.SUCESSO) {
                if (id != null && ObjectId.isValid(id)) {
                    repo.save(usuario);
                } else {
                    repo.insert(usuario);
                }
                msgMvc = new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo");
            } else {
                redirectAttributes.addFlashAttribute("repopulate", uvm);
                redirect += "/" + id;
            }
        } catch (Exception ex) {
            msgMvc = new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage());
        }
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, msgMvc);
        return "redirect:" + redirect;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + Usuario.URL_MVC;
    }
}

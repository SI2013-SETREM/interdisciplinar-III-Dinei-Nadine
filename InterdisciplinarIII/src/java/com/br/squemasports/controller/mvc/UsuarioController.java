package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.UsuarioRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.WsRequest;
import com.br.squemasports.model.Usuario;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.UsuarioViewModel;
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
        if ("novo".equals(id)) {
            Usuario u = new Usuario();
            u.setStatus(true);
            mv.addObject("documento", u);
            mv.addObject("titulo", "Novo usuário");
        } else {
            Usuario u = repo.findOne(id);
            if (u != null) {
                u.setSenha(null);
                mv.addObject("documento", u);
                mv.addObject("titulo", "Usuário " + u.getNome());
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute UsuarioViewModel uvm, final RedirectAttributes redirectAttributes) {
        String redirect = Usuario.URL_MVC;
        MensagemMVC msg;
        try {
            Usuario usuario = new Usuario();
            if (id != null && ObjectId.isValid(id)) {
                usuario = repo.findOne(id);
            }
            msg = uvm.fill(usuario);
            if (msg.getGravidade() == MensagemMVC.GRAVIDADE.SUCESSO) {
                if (id == null || "novo".equals(id)) {
                    repo.insert(usuario);
                } else {
                    repo.save(usuario);
                }
                msg = new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo");
            } else {
                redirect += "/" + id;
            }
        } catch (Exception ex) {
            msg = new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage());
        }
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, msg);
        return "redirect:" + redirect;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + Usuario.URL_MVC;
    }
}

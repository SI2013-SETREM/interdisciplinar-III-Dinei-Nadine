package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.UnidadeMedidaRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.UnidadeMedida;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.UnidadeMedidaViewModel;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(UnidadeMedida.URL_MVC)
public class UnidadeMedidaController {
    
    @Autowired
    private UnidadeMedidaRepository repo;
    
    @RequestMapping
    public MV list() {
        MV mv = new MV(UnidadeMedida.class, "listUnidadeMedida");
        mv.addObject("titulo", "Unidades de Medida");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(UnidadeMedida.class, "formUnidadeMedida");
        if ("novo".equals(id)) {
            UnidadeMedida u = new UnidadeMedida();
            mv.addObject("documento", u);
            mv.addObject("titulo", "Nova unidade de medida");
        } else {
            UnidadeMedida u = repo.findOne(id);
            if (u != null) {
                mv.addObject("documento", u);
                mv.addObject("titulo", "Unidade de Medida " + u.getNome());
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute UnidadeMedidaViewModel umvm, final RedirectAttributes redirectAttributes) {
        try {
            UnidadeMedida unidadeMedida = new UnidadeMedida();
            if (id != null && ObjectId.isValid(id)) {
                unidadeMedida = repo.findOne(id);
            }
            umvm.fill(unidadeMedida);
            if (id == null || "novo".equals(id)) {
                repo.insert(unidadeMedida);
            } else {
                repo.save(unidadeMedida);
            }
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage()));
        }
        return "redirect:" + UnidadeMedida.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + UnidadeMedida.URL_MVC;
    }
}

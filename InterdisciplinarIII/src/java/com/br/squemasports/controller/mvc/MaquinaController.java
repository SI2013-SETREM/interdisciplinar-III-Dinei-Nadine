package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.MaquinaRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.Maquina;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.MaquinaViewModel;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(Maquina.URL_MVC)
public class MaquinaController {
    
    @Autowired
    private MaquinaRepository repo;
    
    @RequestMapping
    public MV list() {
        MV mv = new MV(Maquina.class, "listMaquina");
        mv.addObject("titulo", "Maquinário");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(Maquina.class, "formMaquina");
        if ("novo".equals(id)) {
            Maquina u = new Maquina();
            mv.addObject("documento", u);
            mv.addObject("titulo", "Nova máquina");
        } else {
            Maquina u = repo.findOne(id);
            if (u != null) {
                mv.addObject("documento", u);
                mv.addObject("titulo", "Máquina " + u.getFabricanteModelo());
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute MaquinaViewModel mvm, final RedirectAttributes redirectAttributes) {
        try {
            Maquina maquina = new Maquina();
            if (id != null && ObjectId.isValid(id)) {
                maquina = repo.findOne(id);
            }
            mvm.fill(maquina);
            if (id == null || "novo".equals(id)) {
                repo.insert(maquina);
            } else {
                repo.save(maquina);
            }
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage()));
        }
        return "redirect:" + Maquina.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + Maquina.URL_MVC;
    }
}

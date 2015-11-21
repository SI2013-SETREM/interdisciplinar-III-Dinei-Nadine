package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.MaquinaRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.Maquina;
import com.br.squemasports.model.Produto;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.MaquinaViewModel;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;
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
        Maquina documento;
        if (id != null && ObjectId.isValid(id)) {
            documento = repo.findOne(id);
            if (documento != null) {
                mv.addObject("documento", documento);
                mv.addObject("titulo", "Máquina " + documento.getFabricanteModelo());
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        } else {
            documento = new Maquina();
            mv.addObject("titulo", "Nova máquina");
        }
        mv.addObject("documento", documento);
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute MaquinaViewModel mvm, final RedirectAttributes redirectAttributes) {
        try {
            Maquina documento = new Maquina();
            if (id != null && ObjectId.isValid(id)) {
                documento = repo.findOne(id);
            }
            mvm.fill(documento);
            if (id != null && ObjectId.isValid(id)) {
                repo.save(documento);
                updateRelated(id, documento);
            } else {
                repo.insert(documento);
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
        updateRelated(id, null);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + Maquina.URL_MVC;
    }
    
    public void updateRelated(String id, Maquina documento) {
        MongoOperations mongoOperations = Util.getMongoOperations();
        mongoOperations.updateMulti(
                query(where("produtoMaquinas.maquina.id").is(new ObjectId(id))), 
                update("produtoMaquinas.$.maquina", documento), 
                Produto.class);
    }
}

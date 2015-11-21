package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.UnidadeMedidaRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.Componente;
import com.br.squemasports.model.Produto;
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

import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;

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
        UnidadeMedida documento;
        if (id != null && ObjectId.isValid(id)) {
            documento = repo.findOne(id);
            if (documento != null) {
                mv.addObject("titulo", "Unidade de Medida " + documento);
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        } else {
            documento = new UnidadeMedida();
            mv.addObject("titulo", "Nova unidade de medida");
        }
        mv.addObject("documento", documento);
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute UnidadeMedidaViewModel umvm, final RedirectAttributes redirectAttributes) {
        try {
            UnidadeMedida documento = new UnidadeMedida();
            if (id != null && ObjectId.isValid(id)) {
                documento = repo.findOne(id);
            }
            umvm.fill(documento);
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
        return "redirect:" + UnidadeMedida.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        updateRelated(id, null);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + UnidadeMedida.URL_MVC;
    }
    
    public void updateRelated(String id, UnidadeMedida documento) {
        MongoOperations mongoOperations = Util.getMongoOperations();
        mongoOperations.updateMulti(
                query(where("unidadeMedida.id").is(new ObjectId(id))), 
                update("unidadeMedida", documento), 
                Componente.class);
        mongoOperations.updateMulti(
                query(where("produtoComponentes.componente.unidadeMedida.id").is(new ObjectId(id))), 
                update("produtoComponentes.$.componente.unidadeMedida", documento), 
                Produto.class);
    }
}

package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.CategoriaComponenteRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.CategoriaComponente;
import com.br.squemasports.model.Componente;
import com.br.squemasports.model.Produto;
import com.br.squemasports.viewmodel.MensagemMVC;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Controller
@RequestMapping(CategoriaComponente.URL_MVC)
public class CategoriaComponenteController {
    
    @Autowired
    private CategoriaComponenteRepository repo;
    
    @RequestMapping
    public MV list() {
        MV mv = new MV(CategoriaComponente.class, "listCategoriaComponente");
        mv.addObject("titulo", "Categorias de Componentes");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(CategoriaComponente.class, "formCategoriaComponente");
        if (id != null && ObjectId.isValid(id)) {
            CategoriaComponente u = repo.findOne(id);
            if (u != null) {
                mv.addObject("documento", u);
                mv.addObject("titulo", "Categoria de componentes " + u.getNome());
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        } else {
            CategoriaComponente u = new CategoriaComponente();
            mv.addObject("documento", u);
            mv.addObject("titulo", "Nova categoria de componentes");
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute CategoriaComponente categoria, final RedirectAttributes redirectAttributes) {
        try {
            CategoriaComponente documento = new CategoriaComponente();
            if (id != null && ObjectId.isValid(id)) {
                documento = repo.findOne(id);
            }
            documento.setNome(Util.getString(categoria.getNome()));
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
        return "redirect:" + CategoriaComponente.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        updateRelated(id, null);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + CategoriaComponente.URL_MVC;
    }
    
    public void updateRelated(String id, CategoriaComponente documento) {
        MongoOperations mongoOperations = Util.getMongoOperations();
        mongoOperations.updateMulti(
                query(where("categoria.id").is(new ObjectId(id))), 
                update("categoria", documento), 
                Componente.class);
        mongoOperations.updateMulti(
                query(where("produtoComponentes.componente.categoria.id").is(new ObjectId(id))), 
                update("produtoComponentes.$.componente.categoria", documento), 
                Produto.class);
    }
}

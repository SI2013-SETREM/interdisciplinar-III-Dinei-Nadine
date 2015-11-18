package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.FornecedorRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.Componente;
import com.br.squemasports.model.Fornecedor;
import com.br.squemasports.model.Produto;
import com.br.squemasports.viewmodel.FornecedorViewModel;
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
@RequestMapping(Fornecedor.URL_MVC)
public class FornecedorController {

    @Autowired
    private FornecedorRepository repo;

    @RequestMapping
    public MV list() {
        MV mv = new MV(Fornecedor.class, "listFornecedor");
        mv.addObject("titulo", "Fornecedores");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(Fornecedor.class, "formFornecedor");
        if (ObjectId.isValid(id)) {
            Fornecedor documento = repo.findOne(id);
            if (documento != null) {
                mv.addObject("documento", documento);
                mv.addObject("titulo", "Fornecedor " + documento.getNome());
            } else {
                mv.addObject("documento", new Fornecedor());
                mv.addObject("titulo", "Erro no fornecedor");
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        } else {
            mv.addObject("documento", new Fornecedor());
            mv.addObject("titulo", "Novo fornecedor");
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute FornecedorViewModel fvm, final RedirectAttributes redirectAttributes) {
        try {
            Fornecedor documento = new Fornecedor();
            if (id != null && ObjectId.isValid(id)) {
                documento = repo.findOne(id);
            }
            fvm.fill(documento);
            if (id != null && ObjectId.isValid(id)) {
                repo.save(documento);
                
                // Atualiza os documentos relacionados
                MongoOperations mongoOperations = Util.getMongoOperations();
                mongoOperations.updateMulti(
                        query(where("fornecedor.id").is(new ObjectId(id))), 
                        update("fornecedor", documento), 
                        Componente.class);
                mongoOperations.updateMulti(
                        query(where("produtoComponentes.componente.fornecedor.id").is(new ObjectId(id))), 
                        update("produtoComponentes.$.componente.fornecedor", documento), 
                        Produto.class);
            } else {
                repo.insert(documento);
            }
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage()));
        }
        return "redirect:" + Fornecedor.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + Fornecedor.URL_MVC;
    }
}

package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.CategoriaProdutoRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.CategoriaProduto;
import com.br.squemasports.viewmodel.MensagemMVC;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(CategoriaProduto.URL_MVC)
public class CategoriaProdutoController {
    
    @Autowired
    private CategoriaProdutoRepository repo;
    
    @RequestMapping
    public MV list() {
        MV mv = new MV(CategoriaProduto.class, "listCategoriaProduto");
        mv.addObject("titulo", "Categorias de Produtos");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(CategoriaProduto.class, "formCategoriaProduto");
        if ("novo".equals(id)) {
            CategoriaProduto u = new CategoriaProduto();
            mv.addObject("documento", u);
            mv.addObject("titulo", "Nova categoria de produtos");
        } else {
            CategoriaProduto u = repo.findOne(id);
            if (u != null) {
                mv.addObject("documento", u);
                mv.addObject("titulo", "Categoria de produtos " + u.getNome());
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute CategoriaProduto mvm, final RedirectAttributes redirectAttributes) {
        try {
            CategoriaProduto documento = new CategoriaProduto();
            if (id != null && ObjectId.isValid(id)) {
                documento = repo.findOne(id);
            }
            documento.setNome(Util.getString(mvm.getNome()));
            if (id == null || "novo".equals(id)) {
                repo.insert(documento);
            } else {
                repo.save(documento);
            }
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage()));
        }
        return "redirect:" + CategoriaProduto.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + CategoriaProduto.URL_MVC;
    }
}

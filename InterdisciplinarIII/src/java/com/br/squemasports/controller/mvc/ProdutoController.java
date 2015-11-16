
package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.CategoriaProdutoRepository;
import com.br.squemasports.dao.ComponenteRepository;
import com.br.squemasports.dao.ProdutoRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.Produto;
import com.br.squemasports.viewmodel.ProdutoViewModel;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.ProdutoComponenteViewModel;
import java.util.ArrayList;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(Produto.URL_MVC)
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository repo;
    @Autowired
    private CategoriaProdutoRepository repoCategoria;
    @Autowired
    private ComponenteRepository repoComponentes;
    
    @RequestMapping
    public MV list() {
        MV mv = new MV(Produto.class, "listProduto");
        mv.addObject("titulo", "Produtos");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(Produto.class, "formProduto");
        mv.addObject("categorias", repoCategoria.findAll());
        mv.addObject("componentes", repoComponentes.findAll());
        ProdutoViewModel pvm = new ProdutoViewModel();
        if (id != null && ObjectId.isValid(id)) {
            Produto doc = repo.findOne(id);
            if (doc != null) {
                doc.fill(pvm);
                mv.addObject("titulo", "Produto " + doc);
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        } else {
            mv.addObject("titulo", "Novo produto");
        }
        mv.addObject("documento", pvm);
        return mv;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, params = {"addComponente"})
    public MV addComponente(@PathVariable("id") String id, final ProdutoViewModel documento, final BindingResult bindingResult) {
        MV mv = new MV(Produto.class, "formProduto");
        mv.addObject("categorias", repoCategoria.findAll());
        mv.addObject("componentes", repoComponentes.findAll());
        ProdutoViewModel pvm = documento;
        if (id != null && ObjectId.isValid(id)) {
            mv.addObject("titulo", "Produto " + pvm);
        } else {
            mv.addObject("titulo", "Novo produto");
        }
        if (pvm.getProdutoComponentes() == null) 
            pvm.setProdutoComponentes(new ArrayList<>());
        pvm.getProdutoComponentes().add(new ProdutoComponenteViewModel());
        mv.addObject("documento", pvm);
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute ProdutoViewModel pvm, final RedirectAttributes redirectAttributes) {
        try {
            Produto produto = new Produto();
            if (id != null && ObjectId.isValid(id)) {
                produto = repo.findOne(id);
            }
            if (pvm.getCategoriaId() != null && ObjectId.isValid(pvm.getCategoriaId())) {
                pvm.setCategoria(repoCategoria.findOne(pvm.getCategoriaId()));
            } else {
                pvm.setCategoria(null);
            }
            if (pvm.getProdutoComponentes() != null) {
                pvm.getProdutoComponentes().stream()
                        .filter((pcvm) -> pcvm.getComponenteId() != null && ObjectId.isValid(pcvm.getComponenteId()))
                        .forEach((pcvm) -> {
                            pcvm.setComponente(repoComponentes.findOne(pcvm.getComponenteId()));
                        });
            }
            pvm.fill(produto);
            if (id != null && ObjectId.isValid(id)) {
                repo.save(produto);
            } else {
                repo.insert(produto);
            }
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage()));
        }
        return "redirect:" + Produto.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + Produto.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/detalhes", method = RequestMethod.GET)
    public MV detalhes(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        MV mv = new MV(Produto.class, "viewProduto");
        ProdutoViewModel pvm = new ProdutoViewModel();
        if (ObjectId.isValid(id)) {
            Produto doc = repo.findOne(id);
            if (doc != null) {
                doc.fill(pvm);
                mv.addObject("titulo", "Produto " + doc);
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
            mv.addObject("documento", pvm);
        } else {
            mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Produto não encontrado, sem identificador"));
        }
        return mv;
    }
    
}

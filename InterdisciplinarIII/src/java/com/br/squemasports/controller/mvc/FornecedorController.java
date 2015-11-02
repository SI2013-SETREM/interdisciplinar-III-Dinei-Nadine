package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.FornecedorRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.Fornecedor;
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
        if ("novo".equals(id)) {
            mv.addObject("documento", new Fornecedor());
            mv.addObject("titulo", "Novo fornecedor");
        } else {
            Fornecedor f = repo.findOne(id);
            if (f != null) {
                mv.addObject("documento", f);
                mv.addObject("titulo", "Fornecedor " + f.getNome());
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute FornecedorViewModel fvm, final RedirectAttributes redirectAttributes) {
        try {
            Fornecedor fornecedor = new Fornecedor();
            if (id != null && ObjectId.isValid(id)) {
                fornecedor = repo.findOne(id);
            }
            fvm.fill(fornecedor);
            if (id == null || "novo".equals(id)) {
                repo.insert(fornecedor);
            } else {
                repo.save(fornecedor);
            }
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage()));
        }
        return "redirect:" + Fornecedor.URL_MVC;
    }
}

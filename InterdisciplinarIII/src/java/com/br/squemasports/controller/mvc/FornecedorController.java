
package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.FornecedorRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.Fornecedor;
import com.br.squemasports.viewmodel.MensagemMVC;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView form(@PathVariable("id") String id) {
        MV mv = new MV(Fornecedor.class, "formFornecedor");
        if ("novo".equals(id)) {
            mv.addObject("documento", new Fornecedor());
            mv.addObject("titulo", "Novo fornecedor");
        } else {
            Fornecedor f = repo.findOne(id);
            if (f != null) {
                String emails = f.getEmailsList(true);
                mv.addObject("teste", emails);
                mv.addObject("documento", f);
                mv.addObject("titulo", "Fornecedor " + f.getNome());
            } else {
                // Msg de erro?
            }
        }
        return mv;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id) {
        return "redirect:" + Fornecedor.URL_MVC;
    }
}

package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.EmpresaRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.Empresa;
import com.br.squemasports.model.Produto;
import com.br.squemasports.model.Setor;
import com.br.squemasports.viewmodel.EmpresaViewModel;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.ProdutoComponenteViewModel;
import com.br.squemasports.viewmodel.ProdutoViewModel;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping(Empresa.URL_MVC)
public class EmpresaController {
    
    @Autowired
    private EmpresaRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public MV form() {
        MV mv = new MV(Empresa.class, "formEmpresa");
        EmpresaViewModel documento = new EmpresaViewModel();
        Empresa empresa = getEmpresa();
        if (empresa != null) 
            empresa.fill(documento);
        mv.addObject("titulo", "Empresa");
        mv.addObject("documento", documento);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@ModelAttribute EmpresaViewModel vm, final RedirectAttributes redirectAttributes) {
        try {
            Empresa documento = getEmpresa();
            if (documento == null) 
                documento = new Empresa();
            vm.fill(documento);
            repo.save(documento);
            
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex + " - " + ex.getMessage()));
        }
        return "redirect:" + Empresa.URL_MVC;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = {"addSetor"})
    public MV addSetor(final EmpresaViewModel documento, final BindingResult bindingResult) {
        MV mv = new MV(Empresa.class, "formEmpresa");
        if (documento != null) {
            if (documento.getSetores() == null) 
                documento.setSetores(new ArrayList<>());
            documento.getSetores().add(new Setor());
        }
        mv.addObject("titulo", "Empresa");
        mv.addObject("documento", documento);
        return mv;
    }
    
    private Empresa getEmpresa() {
        List<Empresa> empresas = repo.findAll();
        if (empresas != null && empresas.size() > 0) {
            return empresas.get(0);
        }
        return null;
    }
}

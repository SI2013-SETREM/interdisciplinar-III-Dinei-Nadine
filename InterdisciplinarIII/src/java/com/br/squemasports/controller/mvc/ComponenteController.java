package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.CategoriaComponenteRepository;
import com.br.squemasports.dao.ComponenteRepository;
import com.br.squemasports.dao.FornecedorRepository;
import com.br.squemasports.dao.UnidadeMedidaRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.model.Componente;
import com.br.squemasports.viewmodel.ComponenteViewModel;
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
@RequestMapping(Componente.URL_MVC)
public class ComponenteController {

    @Autowired
    private ComponenteRepository repo;
    @Autowired
    private CategoriaComponenteRepository repoCategoria;
    @Autowired
    private FornecedorRepository repoFornecedor;
    @Autowired
    private UnidadeMedidaRepository repoUnidadeMedida;

    @RequestMapping
    public MV list() {
        MV mv = new MV(Componente.class, "listComponente");
        mv.addObject("titulo", "Componentes");
        mv.addObject("lista", repo.findAll());
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MV form(@PathVariable("id") String id) {
        MV mv = new MV(Componente.class, "formComponente");
        mv.addObject("categorias", repoCategoria.findAll());
        mv.addObject("fornecedores", repoFornecedor.findAll());
        mv.addObject("unidadesMedida", repoUnidadeMedida.findAll());
        ComponenteViewModel vm = new ComponenteViewModel();
        if (ObjectId.isValid(id)) {
            Componente documento = repo.findOne(id);
            if (documento != null) {
                documento.fill(vm);
                mv.addObject("titulo", "Componente " + documento.getNome());
            } else {
                mv.addObject("titulo", "Erro no componente");
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
            mv.addObject("documento", vm);
        } else {
            mv.addObject("documento", vm);
            mv.addObject("titulo", "Novo componente");
        }
        return mv;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String post(@PathVariable("id") String id, @ModelAttribute ComponenteViewModel vm, final RedirectAttributes redirectAttributes) {
        try {
            Componente documento = new Componente();
            if (id != null && ObjectId.isValid(id)) {
                documento = repo.findOne(id);
            }
            if (vm.getUnidadeMedidaId() != null && ObjectId.isValid(vm.getUnidadeMedidaId())) {
                vm.setUnidadeMedida(repoUnidadeMedida.findOne(vm.getUnidadeMedidaId()));
            } else {
                vm.setUnidadeMedida(null);
            }
            if (vm.getCategoriaId() != null && ObjectId.isValid(vm.getCategoriaId())) {
                vm.setCategoria(repoCategoria.findOne(vm.getCategoriaId()));
            } else {
                vm.setCategoria(null);
            }
            if (vm.getFornecedorId() != null && ObjectId.isValid(vm.getFornecedorId())) {
                vm.setFornecedor(repoFornecedor.findOne(vm.getFornecedorId()));
            } else {
                vm.setFornecedor(null);
            }
            vm.fill(documento);
            if (id != null && ObjectId.isValid(id)) {
                repo.save(documento);
            } else {
                repo.insert(documento);
            }
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro salvo"));
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Falha ao salvar o registro: " + ex.getMessage()));
        }
        return "redirect:" + Componente.URL_MVC;
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, final RedirectAttributes redirectAttributes) {
        repo.delete(id);
        redirectAttributes.addFlashAttribute(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "Registro excluído"));
        return "redirect:" + Componente.URL_MVC;
    }
}

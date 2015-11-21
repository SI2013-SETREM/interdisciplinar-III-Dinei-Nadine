package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.EmpresaRepository;
import com.br.squemasports.dao.ProdutoRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.Custo;
import com.br.squemasports.model.Empresa;
import com.br.squemasports.model.Produto;
import com.br.squemasports.model.ProdutoSetor;
import com.br.squemasports.model.Setor;
import com.br.squemasports.viewmodel.EmpresaViewModel;
import com.br.squemasports.viewmodel.MensagemMVC;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.query.Update.update;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(Empresa.URL_MVC)
public class EmpresaController {
    
    @Autowired
    private EmpresaRepository repo;
    @Autowired
    private ProdutoRepository repoProdutos;

    @RequestMapping(method = RequestMethod.GET)
    public MV form() {
        MV mv = new MV(Empresa.class, "formEmpresa");
        EmpresaViewModel documento = new EmpresaViewModel();
        Empresa empresa = Util.getEmpresa();
        if (empresa != null) 
            empresa.fill(documento);
        mv.addObject("titulo", "Empresa");
        mv.addObject("documento", documento);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@ModelAttribute EmpresaViewModel vm, final RedirectAttributes redirectAttributes) {
        try {
            Empresa documento = Util.getEmpresa();
            if (documento == null) 
                documento = new Empresa();
            vm.fill(documento);
            repo.save(documento);
            
            // Atualiza os setores dos produtos
            List<Produto> produtos = repoProdutos.findAll();
            for (Produto produto : produtos) {
                boolean changed = false;
                if (produto.getProdutoSetores() != null && produto.getProdutoSetores().length > 0) {
                    List<ProdutoSetor> newProdutoSetores = new ArrayList<>();
                    for (ProdutoSetor produtoSetor : produto.getProdutoSetores()) {
                        Optional<Setor> setor = Stream.of(documento.getSetores())
                                .filter(x -> x.getId().equals(produtoSetor.getSetor().getId()))
                                .findFirst();
                        if (setor.isPresent()) {
                            ProdutoSetor newProdutoSetor = new ProdutoSetor();
                            newProdutoSetor.setSetor(setor.get());
                            newProdutoSetor.setMinutos(produtoSetor.getMinutos());
                            newProdutoSetor.setCustoMinuto(produtoSetor.getCustoMinuto());
                            newProdutoSetores.add(newProdutoSetor);
                        }
                        changed = true;
                    }
                    if (newProdutoSetores.size() > 0) {
                        produto.setProdutoSetores(newProdutoSetores.toArray(new ProdutoSetor[0]));
                    } else {
                        produto.setProdutoSetores(null);
                    }
                }
                if (changed) {
                    repoProdutos.save(produto);
                }
            }
            
//            List<String> ids = Stream.of(documento.getSetores())
//                    .map(x -> x.getId())
//                    .collect(Collectors.toList());
//            for (Setor setor : documento.getSetores()) {
//                
//            }
            // Atualiza os documentos relacionados
//            MongoOperations mongoOperations = Util.getMongoOperations();
//            mongoOperations.updateMulti(
//                    query(where("produtoSetores.setor.id").not().in(ids)), 
//                    new Update().pull("produtoSetores.setor", vm), 
//                    Produto.class);
            
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
            Setor s = new Setor();
            s.setId(new ObjectId().toHexString());
            documento.getSetores().add(s);
        }
        mv.addObject("titulo", "Empresa");
        mv.addObject("documento", documento);
        return mv;
    }
    
    @RequestMapping(method = RequestMethod.POST, params = {"addCusto"})
    public MV addCusto(final EmpresaViewModel documento, final BindingResult bindingResult) {
        MV mv = new MV(Empresa.class, "formEmpresa");
        if (documento != null) {
            if (documento.getCustos()== null) 
                documento.setCustos(new ArrayList<>());
            documento.getCustos().add(new Custo());
        }
        mv.addObject("titulo", "Empresa");
        mv.addObject("documento", documento);
        return mv;
    }
    
}

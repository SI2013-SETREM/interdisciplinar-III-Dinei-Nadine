
package com.br.squemasports.controller.mvc;

import com.br.squemasports.dao.CategoriaProdutoRepository;
import com.br.squemasports.dao.ComponenteRepository;
import com.br.squemasports.dao.MaquinaRepository;
import com.br.squemasports.dao.ProdutoRepository;
import com.br.squemasports.general.MV;
import com.br.squemasports.general.Util;
import com.br.squemasports.model.Componente;
import com.br.squemasports.model.Empresa;
import com.br.squemasports.model.Maquina;
import com.br.squemasports.model.Produto;
import com.br.squemasports.model.Setor;
import com.br.squemasports.viewmodel.ProdutoViewModel;
import com.br.squemasports.viewmodel.MensagemMVC;
import com.br.squemasports.viewmodel.ProdutoComponenteViewModel;
import com.br.squemasports.viewmodel.ProdutoMaquinaViewModel;
import com.br.squemasports.viewmodel.ProdutoSetorViewModel;
import com.br.squemasports.viewmodel.SessionLoginViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @Autowired
    private MaquinaRepository repoMaquinas;
    
    @RequestMapping
    public MV list() {
        MV mv = new MV(Produto.class, "listProduto");
        mv.addObject("titulo", "Produtos");
        List<ProdutoViewModel> produtos = new ArrayList<>();
        for (Produto p : repo.findAll()) {
            ProdutoViewModel pvm = new ProdutoViewModel();
            p.calculaRateioCustosSetores();
            p.fill(pvm);
            produtos.add(pvm);
        }
        mv.addObject("lista", produtos);
        mv.addObject("allowPrinting", true);
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
        }
        
        mv.addObject("titulo", getTitulo(id, pvm));
        montaMaquinas(pvm);
        montaSetores(pvm);
        mv.addObject("documento", pvm);
        return mv;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, params = {"addComponente"})
    public MV addComponente(@PathVariable("id") String id, final ProdutoViewModel documento, final BindingResult bindingResult) {
        MV mv = new MV(Produto.class, "formProduto");
        mv.addObject("categorias", repoCategoria.findAll());
        mv.addObject("componentes", repoComponentes.findAll());
        ProdutoViewModel pvm = documento;
        if (pvm.getProdutoComponentes() == null) 
            pvm.setProdutoComponentes(new ArrayList<>());
        pvm.getProdutoComponentes().add(new ProdutoComponenteViewModel());
        
        mv.addObject("titulo", getTitulo(id, pvm));
        montaMaquinas(pvm);
        montaSetores(pvm);
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
                            Componente comp = repoComponentes.findOne(pcvm.getComponenteId());
                            if (comp != null) 
                                comp.setHistoricoValores(null);
                            pcvm.setComponente(comp);
                        });
            }
            if (pvm.getProdutoMaquinas()!= null) {
                pvm.getProdutoMaquinas().stream()
                        .filter((pmvm) -> pmvm.getMaquinaId()!= null && ObjectId.isValid(pmvm.getMaquinaId()))
                        .forEach((pcvm) -> {
                            pcvm.setMaquina(repoMaquinas.findOne(pcvm.getMaquinaId()));
                        });
            }
            if (pvm.getProdutoSetores()!= null) {
                Empresa empresa = Util.getEmpresa();
                pvm.getProdutoSetores().stream()
                        .filter((pmvm) -> pmvm.getSetorId() != null && ObjectId.isValid(pmvm.getSetorId()))
                        .forEach((pcvm) -> {
                            Optional<Setor> optSetor = Stream.of(empresa.getSetores()).filter(x -> x.getId().equals(pcvm.getSetorId())).findFirst();
                            if (optSetor.isPresent()) 
                                pcvm.setSetor(optSetor.get());
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
            Produto documento = repo.findOne(id);
            if (documento != null) {
                documento.calculaRateioCustosSetores();
                documento.fill(pvm);
                mv.addObject("titulo", "Produto " + documento);
            } else {
                mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Registro de id '" + id + "' não encontrado"));
            }
            mv.addObject("documento", pvm);
        } else {
            mv.addObject(MensagemMVC.ATTRIBUTE_NAME, new MensagemMVC(MensagemMVC.GRAVIDADE.ERRO, "Produto não encontrado, sem identificador"));
        }
        return mv;
    }
    
    private String getTitulo(String id, ProdutoViewModel pvm) {
        if (id != null && ObjectId.isValid(id)) {
            return "Produto " + pvm;
        } else {
            return "Novo produto";
        }
    }
    
    private void montaMaquinas(ProdutoViewModel pvm) {
        List<Maquina> maquinas = repoMaquinas.findAll();
        // Mostra todas as máquinas no sistema
        // Pega a lista de produtoMaquinas do Produto
        List<ProdutoMaquinaViewModel> produtoMaquinasProduto = pvm.getProdutoMaquinas();
        if (produtoMaquinasProduto == null) {
            produtoMaquinasProduto = new ArrayList<>();
        }
        List<ProdutoMaquinaViewModel> produtoMaquinas = new ArrayList<>(produtoMaquinasProduto);
        for (Maquina m : maquinas) {
            boolean novo = true;
            for (ProdutoMaquinaViewModel pmvm : produtoMaquinasProduto) {
                if (pmvm.getMaquinaId().equals(m.getId())) {
                    pmvm.setMaquina(m);
                    novo = false;
                    break;
                }
            }
            // Se a máquina ainda não está vinculada ao produto
            if (novo) {
                ProdutoMaquinaViewModel pm = new ProdutoMaquinaViewModel();
                pm.setMaquina(m);
                pm.setMaquinaId(m.getId());
                pm.setMinutos(new Float(0));
                produtoMaquinas.add(pm);
            }
        }
        pvm.setProdutoMaquinas(produtoMaquinas);
    }
    
    private void montaSetores(ProdutoViewModel pvm) {
        List<Setor> setores = new ArrayList<>();
        Empresa empresa = Util.getEmpresa();
        if (empresa != null && empresa.getSetores() != null) {
            setores = Arrays.asList(empresa.getSetores());
        }
        // Mostra todos os setores do sistema
        // Pega a lista de produtoSetores do Produto
        List<ProdutoSetorViewModel> produtoSetoresProduto = pvm.getProdutoSetores();
        if (produtoSetoresProduto == null) {
            produtoSetoresProduto = new ArrayList<>();
        }
        List<ProdutoSetorViewModel> produtoSetores = new ArrayList<>(produtoSetoresProduto);
        for (Setor s : setores) {
            boolean novo = true;
            for (ProdutoSetorViewModel psvm : produtoSetoresProduto) {
                if (psvm.getSetorId().equals(s.getId())) {
                    psvm.setSetor(s);
                    novo = false;
                    break;
                }
            }
            // Se o setor ainda não está vinculada ao produto
            if (novo) {
                ProdutoSetorViewModel ps = new ProdutoSetorViewModel();
                ps.setSetor(s);
                ps.setSetorId(s.getId());
                ps.setMinutos(new Float(0));
                produtoSetores.add(ps);
            }
        }
        pvm.setProdutoSetores(produtoSetores);
    }
    
}

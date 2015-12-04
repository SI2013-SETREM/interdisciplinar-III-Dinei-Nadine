
package com.br.squemasports.controller.ws;

import com.br.squemasports.dao.ProdutoRepository;
import com.br.squemasports.model.Produto;
import com.br.squemasports.viewmodel.AndroidProdutosViewModel;
import com.br.squemasports.viewmodel.ProdutoViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Produto.URL_WS)
public class WsProdutoController {
    
    @Autowired
    private ProdutoRepository repo;
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<AndroidProdutosViewModel> findAll(HttpServletResponse response) {
        response.addHeader("Content-type", "application/json;charset=UTF-8");
        List<Produto> produtos = repo.findAll();
        List<AndroidProdutosViewModel> androidProdutos = new ArrayList<>();
        for (Produto p : produtos) {
            AndroidProdutosViewModel android = new AndroidProdutosViewModel();
            android.setId(p.getId());
            android.setNome(p.toString());
            if (p.getCategoria() != null) {
                android.setCategoria(p.getCategoria().getNome());
            }
            p.calculaRateioCustosSetores();
            android.setCusto(p.getCustoTotal());
            androidProdutos.add(android);
        }
        return androidProdutos;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ProdutoViewModel single(@PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Content-type", "application/json;charset=UTF-8");
        ProdutoViewModel pvm = new ProdutoViewModel();
        Produto p = repo.findOne(id);
        p.fill(pvm);
        return pvm;
    }
    
}


package com.br.squemasports.controller.ws;

import com.br.squemasports.dao.ProdutoRepository;
import com.br.squemasports.model.Produto;
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
    public List<Produto> findAll(HttpServletResponse response) {
        response.addHeader("Content-type", "application/json;charset=UTF-8");
        return repo.findAll();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Produto single(@PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Content-type", "application/json;charset=UTF-8");
        return repo.findOne(id);
    }
    
}

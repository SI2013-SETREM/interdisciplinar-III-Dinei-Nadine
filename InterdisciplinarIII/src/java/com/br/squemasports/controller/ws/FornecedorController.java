
package com.br.squemasports.controller.ws;

import com.br.squemasports.dao.FornecedorRepository;
import com.br.squemasports.general.WsResponse;
import com.br.squemasports.model.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/fornecedor")
public class FornecedorController {
    
    @Autowired
    private FornecedorRepository repo;
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Fornecedor> index() {
        return repo.findAll();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Fornecedor single(@PathVariable("id") String id) {
        return repo.findOne(id);
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public WsResponse<Fornecedor> insert(@RequestBody Fornecedor fornecedor) {
        repo.insert(fornecedor);
        return new WsResponse<>(fornecedor);
    }
    
}

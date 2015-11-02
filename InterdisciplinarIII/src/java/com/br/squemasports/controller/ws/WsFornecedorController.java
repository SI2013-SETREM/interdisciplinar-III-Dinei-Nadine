
package com.br.squemasports.controller.ws;

import com.br.squemasports.dao.FornecedorRepository;
import com.br.squemasports.general.WsRequest;
import com.br.squemasports.general.WsResponse;
import com.br.squemasports.model.Fornecedor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Fornecedor.URL_WS)
public class WsFornecedorController {
    
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
    @ResponseBody
    public WsResponse<Fornecedor> insert(@RequestBody Fornecedor fornecedor) {
        repo.insert(fornecedor);
        return new WsResponse<>(fornecedor);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public WsResponse<Fornecedor> update(@PathVariable("id") String id, @RequestBody WsRequest<Fornecedor> request) {
        switch (request.getOperacao()) {
            case UPDATE:
                repo.save(request.getDocumento());
                break;
            case DELETE:
                repo.delete(request.getDocumento());
                break;
        }
        return new WsResponse<>(request.getDocumento());
    }
    
}

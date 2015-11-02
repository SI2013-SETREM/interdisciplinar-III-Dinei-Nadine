
package com.br.squemasports.controller.ws;

import com.br.squemasports.dao.UsuarioRepository;
import com.br.squemasports.general.WsResponse;
import com.br.squemasports.model.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Usuario.URL_WS)
public class WsUsuarioController {
    
    @Autowired
    private UsuarioRepository repo;
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Usuario> index() {
        return repo.findAll();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Usuario single(@PathVariable("id") String id) {
        return repo.findOne(id);
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public WsResponse<Usuario> insert(@RequestBody Usuario usuario) {
        repo.insert(usuario);
        return new WsResponse<>(usuario);
    }
    
}

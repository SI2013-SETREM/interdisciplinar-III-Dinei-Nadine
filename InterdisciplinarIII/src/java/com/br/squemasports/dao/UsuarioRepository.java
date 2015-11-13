
package com.br.squemasports.dao;

import com.br.squemasports.model.Usuario;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
    public List<Usuario> findByLogin(String login);
    
}

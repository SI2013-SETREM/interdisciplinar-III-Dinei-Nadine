
package com.br.squemasports.dao;

import com.br.squemasports.model.Componente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponenteRepository extends MongoRepository<Componente, String>{
    
}

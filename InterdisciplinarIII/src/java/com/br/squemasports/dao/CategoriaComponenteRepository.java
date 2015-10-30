
package com.br.squemasports.dao;

import com.br.squemasports.model.CategoriaComponente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaComponenteRepository extends MongoRepository<CategoriaComponente, String>{
    
}


package com.br.squemasports.dao;

import com.br.squemasports.model.UnidadeMedida;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeMedidaRepository extends MongoRepository<UnidadeMedida, String>{
    
}


package com.br.squemasports.dao;

import com.br.squemasports.model.Maquina;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaquinaRepository extends MongoRepository<Maquina, String>{
    
}

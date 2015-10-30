
package com.br.squemasports.dao;

import com.br.squemasports.model.CategoriaProduto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProdutoRepository extends MongoRepository<CategoriaProduto, String>{
    
}

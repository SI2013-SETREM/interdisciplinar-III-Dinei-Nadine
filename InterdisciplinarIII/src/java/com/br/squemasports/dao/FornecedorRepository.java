
package com.br.squemasports.dao;

import com.br.squemasports.model.Fornecedor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends MongoRepository<Fornecedor, String> {
    
    public Fornecedor findByNome(String nome);
    
}

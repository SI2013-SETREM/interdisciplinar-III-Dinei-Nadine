
package com.br.squemasports.config;

import com.br.squemasports.dao.FornecedorRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = FornecedorRepository.class)
public class DataConfig extends AbstractMongoConfiguration {
    
    @Override
    protected String getDatabaseName() {
        return "interiii";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("localhost", 27017);
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.br.squemasports.model";
    }
    
}

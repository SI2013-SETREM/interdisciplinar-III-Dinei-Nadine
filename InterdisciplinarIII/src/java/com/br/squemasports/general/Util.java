
package com.br.squemasports.general;

import com.br.squemasports.config.DataConfig;
import com.br.squemasports.dao.EmpresaRepository;
import com.br.squemasports.model.Empresa;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public abstract class Util {
    
    @Autowired
    public static EmpresaRepository repoEmpresa;
    
    public static String reduce(String[] collection) {
        return reduce(collection, ", ");
    }
    public static String reduce(String[] collection, String separator) {
        return reduce(collection, null, separator);
    }
    public static String reduce(String[] collection, Function<? super String, ? extends String> map, String separator) {
        if (collection != null && collection.length > 0) {
            Stream<String> s = Stream.of(collection);
            if (map != null) {
                s = s.map(map);
            }
            return s.reduce("", (t, u) -> t + (!"".equals(t) ? separator : "") + u);
        }
        return "";
    }
    
    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            String s = new BigInteger(1, md.digest()).toString(16);
            while (s.length() < 32) s = "0" + s;
            return s;
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }
    
    public static String getString(String requested) {
        // Assim funciona as strings, se não achar outro jeito de resolver o problema vai assim mesmo
        try {
            return new String(requested.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return requested;
        }
    }
    
    public static MongoOperations getMongoOperations() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DataConfig.class);
        return (MongoOperations) ctx.getBean("mongoTemplate");
    }
    
    public static Empresa getEmpresa() {
        List<Empresa> empresas = getMongoOperations().findAll(Empresa.class);
        if (empresas != null && empresas.size() > 0) {
            return empresas.get(0);
        }
        return null;
    }
    
}

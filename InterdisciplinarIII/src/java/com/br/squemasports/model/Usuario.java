
package com.br.squemasports.model;

import com.br.squemasports.general.JView;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Usuario implements Documento {

    public final static String URL_MVC = "/usuario";
    public final static String URL_WS = "/ws/usuario";
    
    private String id;
    private String nome;
    private @JsonIgnore String senha;
    private @JsonIgnore String sal;
    private boolean status;

    @Override
    public String getUrlMvc() { return URL_MVC; }
    @Override
    public String getUrlWs() { return URL_WS; }
    
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}

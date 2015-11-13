
package com.br.squemasports.model;

import com.br.squemasports.general.Util;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Usuario implements Documento {

    public final static String URL_MVC = "/usuario";
    public final static String URL_WS = "/ws/usuario";
    
    private String id;
    private String nome;
    private String login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null) {
            this.sal = null;
            this.senha = null;
        } else {
            this.sal = Util.md5(ObjectId.get().toHexString());
            this.senha = Util.md5(this.getSal() + senha);
        }
    }

    public String getSal() {
        return sal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}

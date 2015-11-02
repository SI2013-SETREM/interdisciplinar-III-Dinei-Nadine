
package com.br.squemasports.viewmodel;

import com.br.squemasports.model.Usuario;

public class UsuarioViewModel {
    
    private String id;
    private String nome;
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public void fill(Usuario usuario) {
        usuario.setNome(nome);
        usuario.setStatus(status);
    }
    
}


package com.br.squemasports.viewmodel;

import com.br.squemasports.model.UnidadeMedida;

public class UnidadeMedidaViewModel {
    
    private String id;
    private String nome;
    private String sigla;

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    public void fill(UnidadeMedida unidadeMedida) {
        unidadeMedida.setNome(nome);
        unidadeMedida.setSigla(sigla);
    }
    
}

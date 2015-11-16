
package com.br.squemasports.model;

import com.br.squemasports.viewmodel.ProdutoComponenteViewModel;

public class ProdutoComponente {
    
    private Componente componente;
    private float quantidade;
    private String comentario;

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public void fill(ProdutoComponenteViewModel pcvm) {
        pcvm.setComponente(componente);
        if (componente != null) {
            pcvm.setComponenteId(componente.getId());
        }
        pcvm.setQuantidade(quantidade);
        pcvm.setComentario(comentario);
    }
    
}

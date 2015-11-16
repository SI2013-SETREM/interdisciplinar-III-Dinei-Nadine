
package com.br.squemasports.viewmodel;

import com.br.squemasports.general.Util;
import com.br.squemasports.model.Componente;
import com.br.squemasports.model.ProdutoComponente;
import org.springframework.format.annotation.NumberFormat;

public class ProdutoComponenteViewModel {
    
    private String componenteId;
    private Componente componente;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Float quantidade;
    private String comentario;

    public String getComponenteId() {
        return componenteId;
    }

    public void setComponenteId(String componenteId) {
        this.componenteId = componenteId;
    }
    
    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public void fill(ProdutoComponente pc) {
        pc.setComponente(componente);
        if (quantidade != null) {
            pc.setQuantidade(quantidade);
        } else {
            pc.setQuantidade(0);
        }
        pc.setComentario(Util.getString(comentario));
    }
}

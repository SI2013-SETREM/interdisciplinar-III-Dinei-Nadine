
package com.br.squemasports.viewmodel;

import com.br.squemasports.model.ProdutoSetor;
import com.br.squemasports.model.Setor;
import org.springframework.format.annotation.NumberFormat;

public class ProdutoSetorViewModel {
    
    private String setorId;
    private Setor setor;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Float minutos;
    private float custoMinuto; //Apenas para exibição nos detalhes do produto

    public String getSetorId() {
        return setorId;
    }

    public void setSetorId(String setorId) {
        this.setorId = setorId;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Float getMinutos() {
        return minutos;
    }

    public void setMinutos(Float minutos) {
        this.minutos = minutos;
    }

    public float getCustoMinuto() {
        return custoMinuto;
    }

    public void setCustoMinuto(float custoMinuto) {
        this.custoMinuto = custoMinuto;
    }

    public void fill(ProdutoSetor pm) {
        pm.setSetor(setor);
        if (minutos != null) {
            pm.setMinutos(minutos);
        } else {
            pm.setMinutos(0);
        }
        // Custo é só para exibição, não é cadastrado
    }
}

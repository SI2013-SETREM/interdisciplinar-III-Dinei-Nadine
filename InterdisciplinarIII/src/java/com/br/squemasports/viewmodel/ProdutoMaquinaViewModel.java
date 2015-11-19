
package com.br.squemasports.viewmodel;

import com.br.squemasports.model.Maquina;
import com.br.squemasports.model.ProdutoMaquina;
import org.springframework.format.annotation.NumberFormat;

public class ProdutoMaquinaViewModel {
    
    private String maquinaId;
    private Maquina maquina;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Float minutos;

    public String getMaquinaId() {
        return maquinaId;
    }

    public void setMaquinaId(String maquinaId) {
        this.maquinaId = maquinaId;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Float getMinutos() {
        return minutos;
    }

    public void setMinutos(Float minutos) {
        this.minutos = minutos;
    }

    public void fill(ProdutoMaquina pm) {
        pm.setMaquina(maquina);
        if (minutos != null) {
            pm.setMinutos(minutos);
        } else {
            pm.setMinutos(0);
        }
    }
}


package com.br.squemasports.viewmodel;

import com.br.squemasports.general.Util;
import com.br.squemasports.model.Maquina;

public class MaquinaViewModel {
    
    private String id;
    private String fabricanteModelo;
    private float custoMinuto;

    public String getId() {
        return id;
    }

    public String getFabricanteModelo() {
        return fabricanteModelo;
    }

    public void setFabricanteModelo(String fabricanteModelo) {
        this.fabricanteModelo = fabricanteModelo;
    }

    public float getCustoMinuto() {
        return custoMinuto;
    }

    public void setCustoMinuto(float custoMinuto) {
        this.custoMinuto = custoMinuto;
    }

    public void fill(Maquina maquina) {
        maquina.setFabricanteModelo(Util.getString(fabricanteModelo));
        maquina.setCustoMinuto(custoMinuto);
    }
    
}

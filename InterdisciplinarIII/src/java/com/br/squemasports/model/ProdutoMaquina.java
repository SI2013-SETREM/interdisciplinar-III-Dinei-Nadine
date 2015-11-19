
package com.br.squemasports.model;

import com.br.squemasports.viewmodel.ProdutoMaquinaViewModel;

public class ProdutoMaquina {
    
    private Maquina maquina;
    private float minutos;

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public float getMinutos() {
        return minutos;
    }

    public void setMinutos(float minutos) {
        this.minutos = minutos;
    }

    public void fill(ProdutoMaquinaViewModel vm) {
        vm.setMaquina(maquina);
        if (maquina != null) {
            vm.setMaquinaId(maquina.getId());
        }
        vm.setMinutos(minutos);
    }
    
}

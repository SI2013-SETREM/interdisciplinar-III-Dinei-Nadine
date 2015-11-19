
package com.br.squemasports.model;

import com.br.squemasports.viewmodel.ProdutoSetorViewModel;

public class ProdutoSetor {
    
    private Setor setor;
    private float minutos;
    private float custoMinuto; //Apenas para exibição nos detalhes do produto

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }
    
    public float getMinutos() {
        return minutos;
    }

    public void setMinutos(float minutos) {
        this.minutos = minutos;
    }

    public float getCustoMinuto() {
        return custoMinuto;
    }

    public void setCustoMinuto(float custoMinuto) {
        this.custoMinuto = custoMinuto;
    }
    
    public void fill(ProdutoSetorViewModel vm) {
        vm.setSetor(setor);
        if (setor != null) {
            vm.setSetorId(setor.getId());
        }
        vm.setMinutos(minutos);
        vm.setCustoMinuto(custoMinuto);
    }
    
}

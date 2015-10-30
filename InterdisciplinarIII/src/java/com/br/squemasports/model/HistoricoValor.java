
package com.br.squemasports.model;

import java.util.Date;

public class HistoricoValor {
    private float valor;
    private Date dataEstipulacao;
    private Fornecedor fornecedor;

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getDataEstipulacao() {
        return dataEstipulacao;
    }

    public void setDataEstipulacao(Date dataEstipulacao) {
        this.dataEstipulacao = dataEstipulacao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    
}

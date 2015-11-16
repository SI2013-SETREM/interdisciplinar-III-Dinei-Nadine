
package com.br.squemasports.viewmodel;

import com.br.squemasports.model.Fornecedor;
import com.br.squemasports.model.HistoricoValor;
import java.util.Date;
import org.springframework.format.annotation.NumberFormat;

public class HistoricoValorViewModel {
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private float valor;
    private Date dataEstipulacao;
    private String fornecedorId;
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

    public String getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(String fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    public void fill(HistoricoValor historico) {
        historico.setValor(valor);
        historico.setDataEstipulacao(dataEstipulacao);
        historico.setFornecedor(fornecedor);
    }
    
}


package com.br.squemasports.viewmodel;

import com.br.squemasports.general.Util;
import com.br.squemasports.model.CategoriaComponente;
import com.br.squemasports.model.Componente;
import com.br.squemasports.model.Fornecedor;
import com.br.squemasports.model.HistoricoValor;
import com.br.squemasports.model.UnidadeMedida;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.NumberFormat;

public class ComponenteViewModel {

    private String id;
    private String nome;
    private String unidadeMedidaId;
    private UnidadeMedida unidadeMedida;
    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private float aliquotaICMS;
    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private float aliquotaIPI;
    private String categoriaId;
    private CategoriaComponente categoria;
    private String fornecedorId;
    private Fornecedor fornecedor;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private float valorUnitario;
    private List<HistoricoValorViewModel> historicoValores;
    private String comentario;

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

    public String getUnidadeMedidaId() {
        return unidadeMedidaId;
    }

    public void setUnidadeMedidaId(String unidadeMedidaId) {
        this.unidadeMedidaId = unidadeMedidaId;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public float getAliquotaICMS() {
        return aliquotaICMS;
    }

    public void setAliquotaICMS(float aliquotaICMS) {
        this.aliquotaICMS = aliquotaICMS;
    }

    public float getAliquotaIPI() {
        return aliquotaIPI;
    }

    public void setAliquotaIPI(float aliquotaIPI) {
        this.aliquotaIPI = aliquotaIPI;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public CategoriaComponente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaComponente categoria) {
        this.categoria = categoria;
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

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(float valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public List<HistoricoValorViewModel> getHistoricoValores() {
        return historicoValores;
    }

    public void setHistoricoValores(List<HistoricoValorViewModel> historicoValores) {
        this.historicoValores = historicoValores;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public void fill(Componente componente) {
        componente.setNome(Util.getString(nome));
        componente.setUnidadeMedida(unidadeMedida);
        componente.setAliquotaICMS(aliquotaICMS);
        componente.setAliquotaIPI(aliquotaIPI);
        componente.setCategoria(categoria);
        componente.setFornecedor(fornecedor);
        componente.setValorUnitario(valorUnitario);
        if (historicoValores != null) {
            List<HistoricoValor> historicos = new ArrayList<>();
            for (HistoricoValorViewModel vm : historicoValores) {
                HistoricoValor hv = new HistoricoValor();
                vm.fill(hv);
                historicos.add(hv);
            }
            componente.setHistoricoValores(historicos.toArray(new HistoricoValor[0]));
        } else {
            componente.setHistoricoValores(null);
        }
    }
    
}

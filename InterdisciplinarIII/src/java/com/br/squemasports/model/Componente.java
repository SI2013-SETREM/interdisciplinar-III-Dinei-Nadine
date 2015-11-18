
package com.br.squemasports.model;

import com.br.squemasports.viewmodel.ComponenteViewModel;
import com.br.squemasports.viewmodel.HistoricoValorViewModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Componente implements Documento {

    public final static String URL_MVC = "/componente";
    public final static String URL_WS = "/ws/componente";
    
    @Id
    private String id;
    private String nome;
    private UnidadeMedida unidadeMedida;
    private float aliquotaICMS;
    private float aliquotaIPI;
    private CategoriaComponente categoria;
    private Fornecedor fornecedor;
    private float valorUnitario;
    private HistoricoValor[] historicoValores;
    private String comentario;

    @Override
    public String getUrlMvc() { return URL_MVC; }
    @Override
    public String getUrlWs() { return URL_WS; }
    
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public CategoriaComponente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaComponente categoria) {
        this.categoria = categoria;
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

    public HistoricoValor[] getHistoricoValores() {
        return historicoValores;
    }

    public void setHistoricoValores(HistoricoValor[] historicoValores) {
        this.historicoValores = historicoValores;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public void fill(ComponenteViewModel vm) {
        vm.setId(id);
        vm.setNome(nome);
        vm.setUnidadeMedida(unidadeMedida);
        if (unidadeMedida != null)
            vm.setUnidadeMedidaId(unidadeMedida.getId());
        vm.setAliquotaICMS(aliquotaICMS);
        vm.setAliquotaIPI(aliquotaIPI);
        vm.setCategoria(categoria);
        if (categoria != null) 
            vm.setCategoriaId(categoria.getId());
        vm.setFornecedor(fornecedor);
        if (fornecedor != null) 
            vm.setFornecedorId(fornecedor.getId());
        vm.setValorUnitario(valorUnitario);
        if (historicoValores != null) {
            List<HistoricoValorViewModel> historicos = new ArrayList<>();
            for (HistoricoValor valor : historicoValores) {
                HistoricoValorViewModel hvvm = new HistoricoValorViewModel();
                hvvm.fill(valor);
                historicos.add(hvvm);
            }
            vm.setHistoricoValores(historicos);
        } else {
            vm.setHistoricoValores(null);
        }
        vm.setComentario(comentario);
    }
    
}

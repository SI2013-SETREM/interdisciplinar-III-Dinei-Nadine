
package com.br.squemasports.model;

import com.br.squemasports.viewmodel.ProdutoComponenteViewModel;
import com.br.squemasports.viewmodel.ProdutoViewModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Produto implements Documento {

    public final static String URL_MVC = "/produto";
    public final static String URL_WS = "/ws/produto";
    
    @Id
    private String id;
    private String referencia;
    private String nome;
    private CategoriaProduto categoria;
    private ProdutoComponente[] produtoComponentes;
    private float minutosUnidade;

    @Override
    public String getUrlMvc() { return URL_MVC; }
    @Override
    public String getUrlWs() { return URL_WS; }
    
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }

    public ProdutoComponente[] getProdutoComponentes() {
        return produtoComponentes;
    }

    public void setProdutoComponentes(ProdutoComponente[] produtoComponentes) {
        this.produtoComponentes = produtoComponentes;
    }

    public float getMinutosUnidade() {
        return minutosUnidade;
    }

    public void setMinutosUnidade(float minutosUnidade) {
        this.minutosUnidade = minutosUnidade;
    }
    
    public void fill(ProdutoViewModel pvm) {
        pvm.setId(id);
        pvm.setReferencia(referencia);
        pvm.setNome(nome);
        pvm.setCategoria(categoria);
        if (categoria != null) {
            pvm.setCategoriaId(categoria.getId());
        }
        if (produtoComponentes != null) {
            List<ProdutoComponenteViewModel> listPcvm = new ArrayList<>();
            for (ProdutoComponente pc : produtoComponentes) {
                if (pc != null) {
                    ProdutoComponenteViewModel pcvm = new ProdutoComponenteViewModel();
                    pc.fill(pcvm);
                    listPcvm.add(pcvm);
                }
            }
            pvm.setProdutoComponentes(listPcvm);
        } else {
            pvm.setProdutoComponentes(null);
        }
        pvm.setMinutosUnidade(minutosUnidade);
    }
    
    public Float getCustoTotal() {
        return Produto.getCustoTotal(this);
    }
    
    public static float getCustoTotal(Produto produto) {
        float valor = 0;
        if (produto != null && produto.getProdutoComponentes() != null) {
            for (ProdutoComponente pc : produto.getProdutoComponentes()) {
                if (pc != null) {
                    Componente c = pc.getComponente();
                    if (c != null) {
                        valor += c.getValorUnitario() * pc.getQuantidade();
                    }
                }
            }
        }
        return valor;
    }
    public static float getCustoTotal(ProdutoViewModel produto) {
        float valor = getCustoTotalComponentes(produto);
        return valor;
    }
    
    public static float getCustoTotalComponentes(ProdutoViewModel produto) {
        float valor = 0;
        if (produto != null && produto.getProdutoComponentes() != null) {
            for (ProdutoComponenteViewModel pc : produto.getProdutoComponentes()) {
                if (pc != null) {
                    Componente c = pc.getComponente();
                    if (c != null) {
                        valor += c.getValorUnitario() * pc.getQuantidade();
                    }
                }
            }
        }
        return valor;
    }

    @Override
    public String toString() {
        return ((this.referencia != null) ? this.referencia + " - " : "") + this.nome;
    }
    
}

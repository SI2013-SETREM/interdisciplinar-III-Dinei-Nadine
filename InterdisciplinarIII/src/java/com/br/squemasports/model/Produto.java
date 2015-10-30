
package com.br.squemasports.model;

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
    
}

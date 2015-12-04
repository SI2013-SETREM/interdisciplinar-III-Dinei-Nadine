
package com.br.squemasports.viewmodel;

import com.br.squemasports.model.Documento;

public class AndroidProdutosViewModel implements Documento {

    public final static String URL_MVC = "/produto";
    public final static String URL_WS = "/ws/produto";
    
    private String id;
    private String nome;
    private String categoria;
    private float custo;
    
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }
    
}

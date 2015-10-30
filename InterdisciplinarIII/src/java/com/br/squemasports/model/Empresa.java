
package com.br.squemasports.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Empresa implements Documento {

    public final static String URL_MVC = "/empresa";
    public final static String URL_WS = "/ws/empresa";

    @Id
    private String id;
    private String nome;
    private float taxaInconformidade;
    private float taxaInadimplencia;
    private float taxaImpostos;
    private Setor[] setores;
    private Custo[] custos;
    private Maquina[] maquinas;

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

    public float getTaxaInconformidade() {
        return taxaInconformidade;
    }

    public void setTaxaInconformidade(float taxaInconformidade) {
        this.taxaInconformidade = taxaInconformidade;
    }

    public float getTaxaInadimplencia() {
        return taxaInadimplencia;
    }

    public void setTaxaInadimplencia(float taxaInadimplencia) {
        this.taxaInadimplencia = taxaInadimplencia;
    }

    public float getTaxaImpostos() {
        return taxaImpostos;
    }

    public void setTaxaImpostos(float taxaImpostos) {
        this.taxaImpostos = taxaImpostos;
    }

    public Setor[] getSetores() {
        return setores;
    }

    public void setSetores(Setor[] setores) {
        this.setores = setores;
    }

    public Custo[] getCustos() {
        return custos;
    }

    public void setCustos(Custo[] custos) {
        this.custos = custos;
    }

    public Maquina[] getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(Maquina[] maquinas) {
        this.maquinas = maquinas;
    }
    
}


package com.br.squemasports.viewmodel;

import com.br.squemasports.general.Util;
import com.br.squemasports.model.Custo;
import com.br.squemasports.model.Empresa;
import com.br.squemasports.model.Setor;
import java.util.List;
import org.springframework.format.annotation.NumberFormat;

public class EmpresaViewModel {
    
    private String id;
    private String nome;
    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private float taxaInconformidade;
    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private float taxaInadimplencia;
    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private float taxaImpostos;
    private List<Setor> setores;
    private List<Custo> custos;

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

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

    public List<Custo> getCustos() {
        return custos;
    }

    public void setCustos(List<Custo> custos) {
        this.custos = custos;
    }
    
    public void fill(Empresa documento) {
        documento.setNome(Util.getString(nome));
        documento.setTaxaInconformidade(taxaInconformidade);
        documento.setTaxaInadimplencia(taxaInadimplencia);
        documento.setTaxaImpostos(taxaImpostos);
        if (setores != null) {
            setores.stream()
                    .forEach(x -> x.setNome(Util.getString(x.getNome())));
            documento.setSetores(setores.toArray(new Setor[0]));
        } else {
            documento.setSetores(null);
        }
        if (custos != null) {
            custos.stream()
                    .forEach(x -> x.setNome(Util.getString(x.getNome())));
            documento.setCustos(custos.toArray(new Custo[0]));
        } else {
            documento.setCustos(null);
        }
    }
    
}

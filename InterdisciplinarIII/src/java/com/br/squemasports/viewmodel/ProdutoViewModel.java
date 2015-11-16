package com.br.squemasports.viewmodel;

import com.br.squemasports.general.Util;
import com.br.squemasports.model.CategoriaProduto;
import com.br.squemasports.model.Produto;
import com.br.squemasports.model.ProdutoComponente;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.NumberFormat;

public class ProdutoViewModel {

    private String id;
    private String referencia;
    private String nome;
    private String categoriaId;
    private CategoriaProduto categoria;
    private List<ProdutoComponenteViewModel> produtoComponentes;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private float minutosUnidade;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private float custoFinal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }

    public List<ProdutoComponenteViewModel> getProdutoComponentes() {
        return produtoComponentes;
    }

    public void setProdutoComponentes(List<ProdutoComponenteViewModel> produtoComponentes) {
        this.produtoComponentes = produtoComponentes;
    }

    public float getMinutosUnidade() {
        return minutosUnidade;
    }

    public void setMinutosUnidade(float minutosUnidade) {
        this.minutosUnidade = minutosUnidade;
    }

    public float getCustoFinal() {
        return custoFinal;
    }

    public void setCustoFinal(float custoFinal) {
        this.custoFinal = custoFinal;
    }

    public void fill(Produto produto) {
        produto.setReferencia(Util.getString(referencia));
        produto.setNome(Util.getString(nome));
        produto.setCategoria(categoria);
        if (produtoComponentes != null) {
            List<ProdutoComponente> listProdutoComponente = new ArrayList<>();
            produtoComponentes.stream()
                    .filter(pcvm -> pcvm != null)
                    .forEach(pcvm -> {
                        ProdutoComponente pc = new ProdutoComponente();
                        pcvm.fill(pc);
                        listProdutoComponente.add(pc);
                    });
            produto.setProdutoComponentes(listProdutoComponente
                    .toArray(new ProdutoComponente[0]));
        } else {
            produto.setProdutoComponentes(null);
        }
        produto.setMinutosUnidade(minutosUnidade);
    }

    @Override
    public String toString() {
        return ((this.referencia != null) ? this.referencia + " - " : "") + this.nome;
    }
    
}

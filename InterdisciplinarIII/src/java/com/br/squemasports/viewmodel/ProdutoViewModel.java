package com.br.squemasports.viewmodel;

import com.br.squemasports.general.Util;
import com.br.squemasports.model.CategoriaProduto;
import com.br.squemasports.model.Produto;
import com.br.squemasports.model.ProdutoComponente;
import com.br.squemasports.model.ProdutoMaquina;
import com.br.squemasports.model.ProdutoSetor;
import java.util.ArrayList;
import java.util.List;

public class ProdutoViewModel {

    private String id;
    private String referencia;
    private String nome;
    private String categoriaId;
    private CategoriaProduto categoria;
    private List<ProdutoComponenteViewModel> produtoComponentes;
    private List<ProdutoMaquinaViewModel> produtoMaquinas;
    private List<ProdutoSetorViewModel> produtoSetores;

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

    public List<ProdutoMaquinaViewModel> getProdutoMaquinas() {
        return produtoMaquinas;
    }

    public void setProdutoMaquinas(List<ProdutoMaquinaViewModel> produtoMaquinas) {
        this.produtoMaquinas = produtoMaquinas;
    }

    public List<ProdutoSetorViewModel> getProdutoSetores() {
        return produtoSetores;
    }

    public void setProdutoSetores(List<ProdutoSetorViewModel> produtoSetores) {
        this.produtoSetores = produtoSetores;
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
        if (produtoMaquinas != null) {
            List<ProdutoMaquina> listDocumento = new ArrayList<>();
            produtoMaquinas.stream()
                    .filter(vm -> vm != null && !(Float.compare(vm.getMinutos(), (float) 0) == 0))
                    .forEach(vm -> {
                        ProdutoMaquina pc = new ProdutoMaquina();
                        vm.fill(pc);
                        listDocumento.add(pc);
                    });
            produto.setProdutoMaquinas(listDocumento
                    .toArray(new ProdutoMaquina[0]));
        } else {
            produto.setProdutoMaquinas(null);
        }
        if (produtoSetores != null) {
            List<ProdutoSetor> listDocumento = new ArrayList<>();
            produtoSetores.stream()
                    .filter(vm -> vm != null && !(Float.compare(vm.getMinutos(), (float) 0) == 0))
                    .forEach(vm -> {
                        ProdutoSetor ps = new ProdutoSetor();
                        vm.fill(ps);
                        listDocumento.add(ps);
                    });
            produto.setProdutoSetores(listDocumento
                    .toArray(new ProdutoSetor[0]));
        } else {
            produto.setProdutoSetores(null);
        }
    }

    @Override
    public String toString() {
        return ((this.referencia != null) ? this.referencia + " - " : "") + this.nome;
    }
    
}

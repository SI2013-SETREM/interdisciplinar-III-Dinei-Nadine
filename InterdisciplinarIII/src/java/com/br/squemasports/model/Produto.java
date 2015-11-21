
package com.br.squemasports.model;

import com.br.squemasports.general.Util;
import com.br.squemasports.viewmodel.ProdutoComponenteViewModel;
import com.br.squemasports.viewmodel.ProdutoMaquinaViewModel;
import com.br.squemasports.viewmodel.ProdutoSetorViewModel;
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
    private ProdutoMaquina[] produtoMaquinas;
    private ProdutoSetor[] produtoSetores;
    
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

    public ProdutoMaquina[] getProdutoMaquinas() {
        return produtoMaquinas;
    }

    public void setProdutoMaquinas(ProdutoMaquina[] produtoMaquinas) {
        this.produtoMaquinas = produtoMaquinas;
    }

    public ProdutoSetor[] getProdutoSetores() {
        return produtoSetores;
    }

    public void setProdutoSetores(ProdutoSetor[] produtoSetores) {
        this.produtoSetores = produtoSetores;
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
        if (produtoMaquinas != null) {
            List<ProdutoMaquinaViewModel> listVm = new ArrayList<>();
            for (ProdutoMaquina pm : produtoMaquinas) {
                if (pm != null) {
                    ProdutoMaquinaViewModel pmvm = new ProdutoMaquinaViewModel();
                    pm.fill(pmvm);
                    listVm.add(pmvm);
                }
            }
            pvm.setProdutoMaquinas(listVm);
        } else {
            pvm.setProdutoMaquinas(null);
        }
        if (produtoSetores != null) {
            List<ProdutoSetorViewModel> listVm = new ArrayList<>();
            for (ProdutoSetor ps : produtoSetores) {
                if (ps != null) {
                    ProdutoSetorViewModel pmvm = new ProdutoSetorViewModel();
                    ps.fill(pmvm);
                    listVm.add(pmvm);
                }
            }
            pvm.setProdutoSetores(listVm);
        } else {
            pvm.setProdutoSetores(null);
        }
    }
    
    public Float getCustoTotal() {
        return Produto.getCustoTotal(this);
    }
    public static float getCustoTotal(Produto produto) {
        ProdutoViewModel pvm = new ProdutoViewModel();
        produto.fill(pvm);
        return Produto.getCustoTotal(pvm);
    }
    public static float getCustoTotal(ProdutoViewModel produto) {
        float valor = getCustoTotalComponentes(produto) 
                + getCustoTotalSetores(produto)
                + getCustoTotalMaquinas(produto);
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
    
    public static float getCustoTotalMaquinas(ProdutoViewModel produto) {
        float valor = 0;
        if (produto != null && produto.getProdutoMaquinas()!= null) {
            for (ProdutoMaquinaViewModel pm : produto.getProdutoMaquinas()) {
                if (pm != null) {
                    Maquina maquina = pm.getMaquina();
                    if (maquina != null) {
                        valor += pm.getMinutos() * maquina.getCustoMinuto();
                    }
                }
            }
        }
        return valor;
    }
    
    public static float getCustoTotalSetores(ProdutoViewModel produto) {
        float valor = 0;
        if (produto != null && produto.getProdutoSetores()!= null) {
            for (ProdutoSetorViewModel ps : produto.getProdutoSetores()) {
                if (ps != null) {
                    valor += ps.getMinutos() * ps.getCustoMinuto();
                }
            }
        }
        return valor;
    }
    
    public void calculaRateioCustosSetores() {
        Empresa empresa = Util.getEmpresa();
        if (empresa.getSetores() != null && this.getProdutoSetores() != null) {
            for (ProdutoSetor produtoSetor : this.getProdutoSetores()) {
                Setor setor = produtoSetor.getSetor();
                if (setor == null) 
                    continue;

                float minutosTrabalhoMensal = (
                    setor.getFuncionarios() * setor.getHorasSemana() //horas semanais
                    * 4 //converte para valor mensal (4 semanas/mês)
                    * 60 //converte para minutos
                    * setor.getEficienciaProdutiva() //aplica o índice de produtividade/ociosidade
                );

                // Custo médio do salário
                float rateioCustoMinutoSetor = (setor.getSalarioMedio() * setor.getFuncionarios() / minutosTrabalhoMensal);
                if (empresa.getCustos() != null) {
                    // Faz o rateio dos custos e soma junto
                    for (Custo custo : empresa.getCustos()) {
                        float valorCusto = custo.getValor();
                        if (custo.isMultiplicaFuncionarios()) 
                            valorCusto *= setor.getFuncionarios();
                        // Calcula o custo/minuto do setor (com ociosidade) (na planilha se chama valor/minuto/homem com ociosidade)
                        rateioCustoMinutoSetor += (valorCusto / minutosTrabalhoMensal);
                    }
                }
                produtoSetor.setCustoMinuto(rateioCustoMinutoSetor);
            }
        } else {
            this.setProdutoSetores(null); //Se não tiver custos gerais nem setores, pode ignorar os setores do produto, pois não sabemos qual é o custo por homem
        }
    }

    @Override
    public String toString() {
        return ((this.referencia != null) ? this.referencia + " - " : "") + this.nome;
    }
    
}

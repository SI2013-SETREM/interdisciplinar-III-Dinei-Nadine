
package com.br.squemasports.viewmodel;

import com.br.squemasports.model.Fornecedor;
import java.util.List;
import java.util.stream.Collectors;

public class FornecedorViewModel {
    
    private String id;
    private String nome;
    private String endereco;
    private List<String> emails;
    private List<String> telefones;

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

    public List<String> getEmails() {
        return emails;
    }
    
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getTelefones() {
        return telefones;
    }
    
    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public void fill(Fornecedor fornecedor) {
        fornecedor.setNome(nome);
        fornecedor.setEndereco(endereco);
        if (emails != null) {
            fornecedor.setEmails(emails.stream()
                    .filter(x -> (x != null && !"".equals(x)))
                    .collect(Collectors.toList())
                    .toArray(new String[0]));
        } else {
            fornecedor.setEmails(null);
        }
        if (telefones != null) {
            fornecedor.setTelefones(telefones.stream()
                    .filter(x -> (x != null && !"".equals(x)))
                    .collect(Collectors.toList())
                    .toArray(new String[0]));
        } else {
            fornecedor.setTelefones(null);
        }
    }
    
}

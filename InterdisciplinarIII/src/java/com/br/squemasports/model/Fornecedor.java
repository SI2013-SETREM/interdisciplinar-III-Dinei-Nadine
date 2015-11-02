
package com.br.squemasports.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.br.squemasports.general.Util;
import java.util.function.Function;
import org.unbescape.html.HtmlEscape;

@Document
public class Fornecedor implements Documento {
    
    public final static String URL_MVC = "/fornecedor";
    public final static String URL_WS = "/ws/fornecedor";
    
    @Id
    private String id;
    private String nome;
    private String endereco;
    private String[] emails;
    private String[] telefones;
    
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

    public String[] getEmails() {
        return emails;
    }
    public String getEmailsList(boolean link) {
        if (emails != null) {
            Function<String, String> map = link 
                    ? x -> "<a href=\"mailto:" + HtmlEscape.escapeHtml5(x) + "\">" + HtmlEscape.escapeHtml5(x) + "</a>" 
                    : null;
            return Util.reduce(emails, map, ", ");
        }
        return "";
    }
    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public String[] getTelefones() {
        return telefones;
    }
    public String getTelefonesList(boolean link) {
        if (telefones != null) {
            Function<String, String> map = link 
                    ? x -> "<a href=\"tel:" + HtmlEscape.escapeHtml5(x) + "\">" + HtmlEscape.escapeHtml5(x) + "</a>" 
                    : null;
            return Util.reduce(telefones, map, ", ");
        }
        return "";
    }
    public void setTelefones(String[] telefones) {
        this.telefones = telefones;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
}


package com.br.squemasports.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Custo implements Documento {

    public final static String URL_MVC = "/custo";
    public final static String URL_WS = "/ws/custo";
    
    public enum TipoCusto {
        VARIAVEL, 
        FIXO;

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    @Id
    private String id;
    private float valor;
    private float multiplicaFuncionarios;
    private TipoCusto tipo;

    @Override
    public String getUrlMvc() { return URL_MVC; }
    @Override
    public String getUrlWs() { return URL_WS; }
    
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getMultiplicaFuncionarios() {
        return multiplicaFuncionarios;
    }

    public void setMultiplicaFuncionarios(float multiplicaFuncionarios) {
        this.multiplicaFuncionarios = multiplicaFuncionarios;
    }

    public TipoCusto getTipo() {
        return tipo;
    }

    public void setTipo(TipoCusto tipo) {
        this.tipo = tipo;
    }
    
}

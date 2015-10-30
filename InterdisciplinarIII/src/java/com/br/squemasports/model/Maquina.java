
package com.br.squemasports.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Maquina implements Documento {

    public final static String URL_MVC = "/maquina";
    public final static String URL_WS = "/ws/maquina";
    
    @Id
    private String id;
    private String fabricanteModelo;
    private float custoMinuto;

    @Override
    public String getUrlMvc() { return URL_MVC; }
    @Override
    public String getUrlWs() { return URL_WS; }
    
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    public String getFabricanteModelo() {
        return fabricanteModelo;
    }

    public void setFabricanteModelo(String fabricanteModelo) {
        this.fabricanteModelo = fabricanteModelo;
    }

    public float getCustoMinuto() {
        return custoMinuto;
    }

    public void setCustoMinuto(float custoMinuto) {
        this.custoMinuto = custoMinuto;
    }
    
}


package com.br.squemasports.viewmodel;

public class MensagemMVC {
    
    public static final String ATTRIBUTE_NAME = "mensagem";
    
    public enum GRAVIDADE {
        SUCESSO, INFORMACAO, ALERTA, ERRO
    }
    
    private GRAVIDADE gravidade;
    private String msg;

    public MensagemMVC() {
    }

    public MensagemMVC(GRAVIDADE gravidade, String msg) {
        this.gravidade = gravidade;
        this.msg = msg;
    }
    
    public GRAVIDADE getGravidade() {
        return gravidade;
    }

    public void setGravidade(GRAVIDADE gravidade) {
        this.gravidade = gravidade;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getCssClass() {
        switch(gravidade) {
            case SUCESSO:
                return "alert-success";
            case INFORMACAO:
                return "alert-info";
            case ALERTA:
                return "alert-warning";
            case ERRO:
            default:
                return "alert-danger";
        }
    }
    
}

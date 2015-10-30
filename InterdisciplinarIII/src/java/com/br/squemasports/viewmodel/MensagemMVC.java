
package com.br.squemasports.viewmodel;

public class MensagemMVC {
    public enum GRAVIDADE {
        ERRO, ALERTA
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
    
}

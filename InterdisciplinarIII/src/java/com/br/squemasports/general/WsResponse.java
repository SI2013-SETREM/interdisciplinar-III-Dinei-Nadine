
package com.br.squemasports.general;

import com.br.squemasports.model.Documento;

public class WsResponse<T extends Documento> {
    
    private int status;
    private String mensagem;
    private T documento;
    
    public WsResponse(T document) {
        this.documento = document;
        this.status = 1;
    }
    public WsResponse(T document, String mensagem) {
        this.documento = document;
        this.status = 0;
        this.mensagem = mensagem;
    }
    public WsResponse(T documento, int status) {
        this.documento = documento;
        this.status = status;
    }
    public WsResponse(T documento, int status, String mensagem) {
        this.documento = documento;
        this.status = status;
        this.mensagem = mensagem;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public T getDocumento() {
        return documento;
    }

    public void setDocumento(T documento) {
        this.documento = documento;
    }
    
}

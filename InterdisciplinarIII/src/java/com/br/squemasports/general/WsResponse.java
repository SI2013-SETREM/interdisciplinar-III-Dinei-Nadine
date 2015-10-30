
package com.br.squemasports.general;

import com.br.squemasports.model.Documento;
import org.springframework.http.HttpStatus;

public class WsResponse<T extends Documento> {
    
    private HttpStatus httpStatus;
    private String mensagem;
    private T documento;
    
    public WsResponse(T document) {
        this.documento = document;
        this.httpStatus = HttpStatus.OK;
    }
    public WsResponse(T document, String mensagem) {
        this.documento = document;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.mensagem = mensagem;
    }
    public WsResponse(T documento, HttpStatus httpStatus) {
        this.documento = documento;
        this.httpStatus = httpStatus;
    }
    public WsResponse(T documento, HttpStatus httpStatus, String mensagem) {
        this.documento = documento;
        this.httpStatus = httpStatus;
        this.mensagem = mensagem;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
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


package com.br.squemasports.general;

import com.br.squemasports.model.Documento;

public class WsRequest<T extends Documento>{
    public enum Operacao {
        LIST, 
        INSERT, 
        UPDATE, 
        DELETE
    }
    
    private Operacao operacao;
    private T documento;
    
    public WsRequest(Operacao operation) {
        this.operacao = operation;
    }
    public WsRequest(Operacao operation, T documento) {
        this.operacao = operation;
        this.documento = documento;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public T getDocumento() {
        return documento;
    }

    public void setDocumento(T documento) {
        this.documento = documento;
    }
    
}


package com.br.squemasports.general;

import org.springframework.http.HttpStatus;

public class WsResponse<T> {
    
    private HttpStatus httpStatus;
    private String message;
    private T document;
    
    public WsResponse(T document) {
        this.document = document;
        this.httpStatus = HttpStatus.OK;
    }
    public WsResponse(T document, String message) {
        this.document = document;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.message = message;
    }
    public WsResponse(T document, HttpStatus httpStatus) {
        this.document = document;
        this.httpStatus = httpStatus;
    }
    public WsResponse(T document, HttpStatus httpStatus, String message) {
        this.document = document;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        this.document = document;
    }
    
}

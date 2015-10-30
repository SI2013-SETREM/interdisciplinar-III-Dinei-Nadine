
package com.br.squemasports.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Documento {
    public String getId();
    public void setId(String id);
    
    @JsonIgnore
    public String getUrlMvc();
    @JsonIgnore
    public String getUrlWs();
}


package com.br.squemasports.general;

import com.br.squemasports.model.Documento;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

public class MV extends ModelAndView {

    public MV() {
    }
    
    public MV(Class<? extends Documento> tipoDocumento) {
        this.setDocument(tipoDocumento);
    }
    
    public MV(Class<? extends Documento> tipoDocumento, String viewName) {
        super(viewName);
        this.setDocument(tipoDocumento);
    }

    public MV(Class<? extends Documento> tipoDocumento, View view) {
        super(view);
        this.setDocument(tipoDocumento);
    }
    
    public MV(String viewName) {
        super(viewName);
    }

    public MV(View view) {
        super(view);
    }

    public MV(String viewName, Map<String, ?> model) {
        super(viewName, model);
    }

    public MV(View view, Map<String, ?> model) {
        super(view, model);
    }

    public MV(String viewName, String modelName, Object modelObject) {
        super(viewName, modelName, modelObject);
    }

    public MV(View view, String modelName, Object modelObject) {
        super(view, modelName, modelObject);
    }
    
    public void setDocument(Class<? extends Documento> T) {
        try {
            Field f = T.getDeclaredField("URL_MVC");
            String url = (String) f.get(null);
            this.addObject("URL_MVC", url);
            
            f = T.getDeclaredField("URL_WS");
            url = (String) f.get(null);
            this.addObject("URL_WS", url);
        } catch (Exception ex) {
            Logger.getLogger(MV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

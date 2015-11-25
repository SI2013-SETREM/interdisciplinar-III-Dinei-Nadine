
package com.br.squemasports.controller.ws;

import com.br.squemasports.dao.UsuarioRepository;
import com.br.squemasports.general.WsResponse;
import com.br.squemasports.model.Usuario;
import com.br.squemasports.viewmodel.LoginViewModel;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/login")
public class WsLoginController {
    
    @Autowired
    private UsuarioRepository repo;
    
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public WsResponse<Usuario> login(@RequestBody LoginViewModel lvm, HttpServletResponse response) {
        response.addHeader("Content-type", "application/json;charset=UTF-8");
        Usuario usuario = null;
        if (lvm != null) {
            List<Usuario> lstUsuarios = repo.findByLogin(lvm.getLogin());
            Optional<Usuario> optUsuario = lstUsuarios.stream()
                    .filter(u -> u.checkSenha(lvm.getSenha()))
                    .findFirst();
            usuario = optUsuario.orElse(null);
        }
        if (usuario != null) {
            return new WsResponse<>(usuario);
        } else {
            return new WsResponse<>(null, "Falha no login");
        }
    }
    
}

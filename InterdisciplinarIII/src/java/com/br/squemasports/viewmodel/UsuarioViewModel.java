
package com.br.squemasports.viewmodel;

import com.br.squemasports.model.Usuario;

public class UsuarioViewModel {
    
    private String id;
    private String nome;
    private String login;
    private String senha;
    private String confirmaSenha;
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public MensagemMVC fill(Usuario usuario) {
        MensagemMVC r = new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "");
        
        usuario.setNome(nome);
        usuario.setStatus(status);
        if (senha != null && confirmaSenha != null) {
            if (!senha.equals(confirmaSenha)) {
                r.setGravidade(MensagemMVC.GRAVIDADE.ERRO);
                r.setMsg("As senhas não conferem");
            } else {
                usuario.setSenha(senha);
            }
        }
        return r;
    }
    
}


package com.br.squemasports.viewmodel;

import com.br.squemasports.general.Util;
import com.br.squemasports.model.Usuario;

public class UsuarioViewModel {
    
    private String id;
    private String nome;
    private String login;
    private String senhaAntiga;
    private String senha;
    private String confirmaSenha;

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

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
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
    
    public MensagemMVC fill(Usuario usuario) {
        MensagemMVC r = new MensagemMVC(MensagemMVC.GRAVIDADE.SUCESSO, "");
        
        usuario.setNome(Util.getString(nome));
        usuario.setLogin(Util.getString(login));
        this.setSenha(Util.getString(senha));
        this.setConfirmaSenha(Util.getString(confirmaSenha));
        if (this.checkSenha()) {
            usuario.setSenha(senha);
        } else {
            r.setGravidade(MensagemMVC.GRAVIDADE.ERRO);
            r.setMsg("As senhas não conferem");
        }
        return r;
    }
    
    public boolean checkSenha() {
        this.setSenha(Util.getString(senha));
        this.setConfirmaSenha(Util.getString(confirmaSenha));
        
        if (senha != null && confirmaSenha != null) {
            if (!senha.equals(confirmaSenha)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}

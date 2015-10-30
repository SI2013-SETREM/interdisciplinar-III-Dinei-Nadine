
package com.br.squemasports.model;

public class Setor {
    
    public enum StatusSetor {
        ATIVO, 
        INATIVO;
        
        @Override
        public String toString() {
            return super.toString().substring(0, 1); //Só a primeira letra
        }
    }
    
    private String nome;
    private int funcionarios;
    private float horasSemana;
    private float eficienciaProdutiva;
    private StatusSetor status;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(int funcionarios) {
        this.funcionarios = funcionarios;
    }

    public float getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(float horasSemana) {
        this.horasSemana = horasSemana;
    }

    public float getEficienciaProdutiva() {
        return eficienciaProdutiva;
    }

    public void setEficienciaProdutiva(float eficienciaProdutiva) {
        this.eficienciaProdutiva = eficienciaProdutiva;
    }

    public StatusSetor getStatus() {
        return status;
    }

    public void setStatus(StatusSetor status) {
        this.status = status;
    }
    
    
}

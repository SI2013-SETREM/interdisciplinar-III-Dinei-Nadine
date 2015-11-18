
package com.br.squemasports.viewmodel;

public class SetorViewModel {
    
    private String nome;
    private int funcionarios;
    private float horasSemana;
    private float eficienciaProdutiva;

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

}

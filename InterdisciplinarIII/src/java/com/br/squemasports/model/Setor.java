
package com.br.squemasports.model;

import org.springframework.format.annotation.NumberFormat;

public class Setor {
    
    private String nome;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int funcionarios;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private float horasSemana;
    @NumberFormat(style = NumberFormat.Style.PERCENT)
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

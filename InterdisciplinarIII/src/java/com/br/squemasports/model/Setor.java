
package com.br.squemasports.model;

import org.springframework.format.annotation.NumberFormat;

public class Setor {
    
    //Tem um ID para poder ser referenciado por outros documentos, apesar de não ter uma coleção específica
    private String id;
    private String nome;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int funcionarios;
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "#0.00")
    private float horasSemana;
    @NumberFormat(style = NumberFormat.Style.PERCENT)
    private float eficienciaProdutiva;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private float salarioMedio;

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

    public float getSalarioMedio() {
        return salarioMedio;
    }

    public void setSalarioMedio(float salarioMedio) {
        this.salarioMedio = salarioMedio;
    }

}

package net.flpes.avaliacaolp.models;

import java.time.LocalDate;

public class Aluno {
    private String cpf;
    private String nome;
    private LocalDate dataNasc;
    private double peso;
    private double altura;

    // Constructor REQUIRES all properties on parameters
    public Aluno(String cpf, String nome, LocalDate dataNasc, double peso, double altura) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.peso = peso;
        this.altura = altura;
    }

    // Getters and Setters for all properties

    // CPF
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    // Nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    // Data de Nascimento
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }
    // Peso
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    // Altura
    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }
}

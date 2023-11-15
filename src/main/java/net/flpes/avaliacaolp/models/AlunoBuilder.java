package net.flpes.avaliacaolp.models;

import java.time.LocalDate;

public class AlunoBuilder {

    private String nome;
    private String cpf;
    private double peso;
    private double altura;
    private LocalDate nasc;

    // Builds an instance of Aluno with current values
    public Aluno build(){
        return new Aluno(cpf, nome, nasc, peso, altura);
    }

    // Nome
    public AlunoBuilder named(String nome){
        this.nome = nome;
        return this;
    }
    // CPF
    public AlunoBuilder withCpf(String cpf){
        this.cpf = cpf;
        return this;
    }
    // Data de Nascimento
    public AlunoBuilder bornOn(LocalDate data){
        this.nasc = data;
        return this;
    }
    // Peso
    public AlunoBuilder weighing(double pesoEmKg){
        this.peso = pesoEmKg;
        return this;
    }
    // Altura
    public AlunoBuilder standingAt(double alturaEmCm){
        this.altura = alturaEmCm;
        return this;
    }

}

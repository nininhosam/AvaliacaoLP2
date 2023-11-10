package net.flpes.avaliacaolp2.models;

import java.time.LocalDate;

public class AlunoBuilder {

    private String nome;
    private String cpf;
    private double peso;
    private double altura;
    private LocalDate nasc;
    public Aluno build(){
        return new Aluno(cpf, nome, nasc, peso, altura);
    }
    public AlunoBuilder named(String nome){
        this.nome = nome;
        return this;
    }
    public AlunoBuilder withCpf(String cpf){
        this.cpf = cpf;
        return this;
    }
    public AlunoBuilder bornOn(LocalDate data){
        this.nasc = data;
        return this;
    }
    public AlunoBuilder weighing(double pesoEmKg){
        this.peso = pesoEmKg;
        return this;
    }
    public AlunoBuilder standingAt(double alturaEmCm){
        this.altura = alturaEmCm;
        return this;
    }

}

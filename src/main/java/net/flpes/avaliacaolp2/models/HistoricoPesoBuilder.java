package net.flpes.avaliacaolp2.models;

import java.time.LocalDate;

public class HistoricoPesoBuilder {

    private String cpf;
    private double peso;
    private double altura;
    private LocalDate dataCalculo;
    public HistoricoPeso build(){
        return new HistoricoPeso(cpf, dataCalculo, peso, altura);
    }
    public HistoricoPesoBuilder(){
        dataCalculo = LocalDate.now();
    }
    public HistoricoPesoBuilder ofCpf(String cpf){
        this.cpf = cpf;
        return this;
    }
    public HistoricoPesoBuilder measuredOn(LocalDate data){
        this.dataCalculo = data;
        return this;
    }
    public HistoricoPesoBuilder weighing(double pesoEmKg){
        this.peso = pesoEmKg;
        return this;
    }
    public HistoricoPesoBuilder standingAt(double alturaEmCm){
        this.altura = alturaEmCm;
        return this;
    }

}

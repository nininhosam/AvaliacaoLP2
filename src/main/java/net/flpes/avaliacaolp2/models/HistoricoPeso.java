package net.flpes.avaliacaolp2.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HistoricoPeso {
    private String cpf;
    private LocalDate dataCalculo;
    private double peso;
    private double altura;

    public HistoricoPeso(String cpf, LocalDate dataCalc, double peso, double altura) {
        this.cpf = cpf;
        this.dataCalculo = dataCalc;
        this.peso = peso;
        this.altura = altura;
    }
    private String interpretaIMC(double imc){
        if (16 > imc) return "Magreza grau III";
        if (17 > imc) return "Magreza grau II";
        if (18.5 > imc) return "Magreza grau I";
        if (25 > imc) return "Eutrofia";
        if (30 > imc) return "Pré-obesidade";
        if (35 > imc) return "Obesidade moderada (grau I)";
        if (40 > imc) return "Obesidade severa (grau II)";
        return "Obesidade muito severa (grau III)";
    }
    public void calcularIMC(){
        double imc = this.peso/Math.pow(this.altura/100, 2);
        System.out.println("No dia" + this.dataCalculo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                "Com cpf" + this.cpf +
                "O(a)" +
                "tinha o IMC de" + imc +
                "que representa: " + interpretaIMC(imc));

        // gravar em um arquivo[data, cpf, nome, imc, interpretação]
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataCalculo() {
        return dataCalculo;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataCalculo = dataNasc;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
}

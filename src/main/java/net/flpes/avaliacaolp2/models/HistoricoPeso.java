package net.flpes.avaliacaolp2.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoricoPeso {
    private int id;
    private Aluno aluno;
    private LocalDateTime dataCalculo;

    public HistoricoPeso(Aluno aluno, LocalDateTime dataCalc, int id) {
        this.aluno = aluno;
        this.dataCalculo = dataCalc;
        this.id = id;
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
        double imc = this.aluno.getPeso()/Math.pow(this.aluno.getAltura()/100, 2);
        //Delete this
        System.out.println("No dia " + this.dataCalculo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " Com cpf " + this.aluno.getCpf() +
                " O(a) " + this.aluno.getNome() +
                " tinha o IMC de " + imc +
                " que representa: " + interpretaIMC(imc));

        // gravar em um arquivo[data, cpf, nome, imc, interpretação]
    }

    public String getCpf() {
        return aluno.getCpf();
    }

    public void setCpf(String cpf) {
        this.aluno.setCpf(cpf);
    }

    public LocalDateTime getDataCalculo() {
        return dataCalculo;
    }

    public void setDataCalculo(LocalDateTime dataNasc) {
        this.dataCalculo = dataNasc;
    }

    public double getPeso() { return aluno.getPeso(); }
    public void setPeso(double peso) { this.aluno.setPeso(peso); }

    public double getAltura() { return this.aluno.getAltura(); }

    public void setAltura(double altura) {
        this.aluno.setAltura(altura);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}

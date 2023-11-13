package net.flpes.avaliacaolp2.models;

import net.flpes.avaliacaolp2.utils.DBUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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

    public double calcularIMC(){
        double imc = this.aluno.getPeso()/Math.pow(this.aluno.getAltura()/100, 2);
        return Math.floor(imc*100)/100;
    }
    public void gravarArquivo() throws IOException {
        double imc = calcularIMC();
        String data = this.dataCalculo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String cpf = this.aluno.getCpf();
        String nome = this.aluno.getNome();
        String resultado = interpretaIMC(imc);

        File imcLog = new File(System.getenv("TEMP")+"/PesoLog.txt");
        if (!imcLog.exists()) {
            imcLog.createNewFile();
        }

        FileWriter writer = new FileWriter(imcLog, true);
        BufferedWriter bWriter = new BufferedWriter(writer);
        bWriter.write(String.format("%s | %s | %s | %s | %s", data, cpf, nome, imc, resultado));
        bWriter.newLine();
        bWriter.close();
    }

    public String getCpf() {
        return aluno.getCpf();
    }

    public void setCpf(String cpf) {
        this.aluno.setCpf(cpf);
    }

    public String getNome() {
        return aluno.getNome();
    }

    public void setNome(String nome) {
        this.aluno.setNome(nome);
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

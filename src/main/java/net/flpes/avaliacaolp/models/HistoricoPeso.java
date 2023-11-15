package net.flpes.avaliacaolp.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoricoPeso {
    private int id;
    private Aluno aluno;
    private LocalDateTime dataCalculo;

    // Constructor REQUIRES all properties on parameter
    public HistoricoPeso(Aluno aluno, LocalDateTime dataCalc, int id) {
        this.aluno = aluno;
        this.dataCalculo = dataCalc;
        this.id = id;
    }
    // Accepts IMC value ; Returns a String with its corresponding meaning
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

    // Returns IMC using the formula Peso/Altura^2
    public double calcularIMC(){
        double imc = this.aluno.getPeso()/Math.pow(this.aluno.getAltura()/100, 2);
        return Math.floor(imc*100)/100;
    }

    // Writes to results to PesoLog.txt
    public void gravarArquivo() throws IOException {
        // Values written to file
        double imc = calcularIMC();
        String data = this.dataCalculo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String cpf = this.aluno.getCpf();
        String nome = this.aluno.getNome();
        String resultado = interpretaIMC(imc);

        // Gets current PesoLog.txt, or creates one if it doesn't exist. ===== PesoLog.txt is found on TEMP directory
        File imcLog = new File(System.getenv("TEMP")+"/PesoLog.txt");
        if (!imcLog.exists()) {
            imcLog.createNewFile();
        }

        // Writes values to file by Appending (doesn't overwrite previous content)
        FileWriter writer = new FileWriter(imcLog, true);
        BufferedWriter bWriter = new BufferedWriter(writer);
        bWriter.write(String.format("%s | %s | %s | %s | %s", data, cpf, nome, imc, resultado));
        bWriter.newLine();
        bWriter.close();
    }

    // Getters and Setters for all properties
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

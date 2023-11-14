package net.flpes.avaliacaolp2.models;

import java.time.LocalDateTime;

public class HistoricoPesoBuilder {
    private Aluno aluno = new AlunoBuilder().build();
    private LocalDateTime dataCalculo;
    private int id;

    // Builds an instance of HistoricoPeso with current values
    public HistoricoPeso build(){
        return new HistoricoPeso(aluno, dataCalculo, id);
    }

    // Constructor sets default dataCalculo to Now.
    public HistoricoPesoBuilder(){
        dataCalculo = LocalDateTime.now();
    }

    // Aluno
    public HistoricoPesoBuilder ofAluno(Aluno aluno){
        // Can't overwrite this.aluno, so copy over parameter's properties instead.
        this.aluno.setPeso(aluno.getPeso());
        this.aluno.setCpf(aluno.getCpf());
        this.aluno.setNome(aluno.getNome());
        this.aluno.setAltura(aluno.getAltura());
        this.aluno.setDataNasc(aluno.getDataNasc());
        return this;
    }
    // Data do Calculo [Uses current date if not specified]
    public HistoricoPesoBuilder measuredOn(LocalDateTime data){
        this.dataCalculo = data;
        return this;
    }
    // Peso
    public HistoricoPesoBuilder weighing(double pesoEmKg){
        this.aluno.setPeso(pesoEmKg);
        return this;
    }
    // Altura
    public HistoricoPesoBuilder standingAt(double alturaEmCm){
        this.aluno.setAltura(alturaEmCm);
        return this;
    }
    // ID
    public HistoricoPesoBuilder identifiedBy(int id){
        this.id = id;
        return this;
    }

}

package net.flpes.avaliacaolp2.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoricoPesoBuilder {
    private Aluno aluno = new AlunoBuilder().build();
    private LocalDateTime dataCalculo;
    private int id;

    public HistoricoPeso build(){
        return new HistoricoPeso(aluno, dataCalculo, id);
    }
    public HistoricoPesoBuilder(){
        dataCalculo = LocalDateTime.now();
    }
    public HistoricoPesoBuilder ofAluno(Aluno aluno){
        this.aluno.setPeso(aluno.getPeso());
        this.aluno.setCpf(aluno.getCpf());
        this.aluno.setNome(aluno.getNome());
        this.aluno.setAltura(aluno.getAltura());
        this.aluno.setDataNasc(aluno.getDataNasc());
        return this;
    }
    public HistoricoPesoBuilder measuredOn(LocalDateTime data){
        this.dataCalculo = data;
        return this;
    }
    public HistoricoPesoBuilder weighing(double pesoEmKg){
        this.aluno.setPeso(pesoEmKg);
        return this;
    }
    public HistoricoPesoBuilder standingAt(double alturaEmCm){
        this.aluno.setAltura(alturaEmCm);
        return this;
    }
    public HistoricoPesoBuilder identifiedBy(int id){
        this.id = id;
        return this;
    }

}

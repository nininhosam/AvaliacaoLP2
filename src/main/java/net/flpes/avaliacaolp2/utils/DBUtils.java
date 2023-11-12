package net.flpes.avaliacaolp2.utils;

import net.flpes.avaliacaolp2.keys.DBKeys;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.models.AlunoBuilder;
import net.flpes.avaliacaolp2.models.HistoricoPeso;
import net.flpes.avaliacaolp2.models.HistoricoPesoBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class DBUtils {
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DBKeys.getSQLDatabase(), DBKeys.getSQLUser(), DBKeys.getSQLPassword());
        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
    }

    public static void addAluno(Aluno aluno){
        String sql = "INSERT INTO alunos(cpf,nome,dataNasc,peso,altura) VALUES(?,?,?,?,?)";
        try {

            Connection connection =  getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setString(3, String.valueOf(aluno.getDataNasc()));
            stmt.setString(4, String.valueOf(aluno.getPeso()));
            stmt.setString(5, String.valueOf(aluno.getAltura()));
            stmt.execute();
            stmt.close();
            connection.close();

        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static List<Aluno> getAlunos(){
        String sql = "SELECT nome, cpf FROM alunos";
        List<Aluno> todosAlunos = new ArrayList<>();
        try{

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.isBeforeFirst()){
                System.out.println("Alunos not found");
            }else {
                while(rs.next()){
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    AlunoBuilder builder = new AlunoBuilder();
                    Aluno aluno = builder
                            .named(nome)
                            .withCpf(cpf)
                            .build();
                    todosAlunos.add(aluno);

                }
            }
        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
        return todosAlunos;

    }
    public static void removeAluno(String cpf){
        String sql = "delete from alunos where cpf=?";
        try {

            Connection connection =  getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, cpf);
            stmt.execute();
            stmt.close();
            connection.close();

        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static void updateAluno(Aluno aluno){
        String sql = "update alunos set nome=?, dataNasc=?, peso=?, altura=? where cpf=?";
        try {

            Connection connection =  getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, String.valueOf(aluno.getDataNasc()));
            stmt.setString(3, String.valueOf(aluno.getPeso()));
            stmt.setString(4, String.valueOf(aluno.getAltura()));
            stmt.setString(5, aluno.getCpf());
            stmt.execute();
            stmt.close();
            connection.close();

        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static Aluno getAluno(String cpf){
        String sql = "SELECT * FROM alunos WHERE CPF = ?";
        Aluno aluno;
        try{
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            if(!rs.isBeforeFirst()){
                System.out.println("Aluno not found");

            }else {
                rs.next();
                AlunoBuilder builder = new AlunoBuilder()
                        .named(rs.getString("nome"))
                        .withCpf(rs.getString("cpf"))
                        .bornOn(LocalDate.parse(rs.getString("dataNasc")))
                        .weighing(Double.parseDouble(rs.getString("peso")))
                        .standingAt(Double.parseDouble(rs.getString("altura")));
                aluno = builder.build();
                return aluno;
            }

        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
        aluno = new AlunoBuilder().build();
        return aluno;
    }

    public static void addHistoricoEntry(HistoricoPeso hist){
        String sql = "insert into historico(cpf, datacalculo, peso, altura) values (?, ?, ?, ?)";
        try {

            Connection connection =  getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, hist.getCpf());
            stmt.setString(2, String.valueOf(hist.getDataCalculo()));
            stmt.setString(3, String.valueOf(hist.getPeso()));
            stmt.setString(4, String.valueOf(hist.getAltura()));
            stmt.execute();
            stmt.close();
            connection.close();

        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static List<HistoricoPeso> getHistoricoCompleto(Aluno aluno){
        String sql = "select historico.id , alunos.nome as aluno, historico.peso, historico.altura, historico.datacalculo as 'Data do calculo' from alunos join historico where historico.cpf=alunos.cpf and historico.cpf=?";
        List<HistoricoPeso> todosHistoryEntries = new ArrayList<>();
        try{

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getCpf());
            ResultSet rs = stmt.executeQuery();

            if(!rs.isBeforeFirst()){
                System.out.println("Entries not found");
            }else {
                while(rs.next()){

                    String peso = rs.getString("peso");
                    String altura = rs.getString("altura");
                    String dataCalc = rs.getString("Data do calculo");
                    String id = rs.getString("id");
                    HistoricoPesoBuilder builder = new HistoricoPesoBuilder();
                    HistoricoPeso entry = builder
                            .ofAluno(aluno)
                            .weighing(Double.parseDouble(peso))
                            .standingAt(Double.parseDouble(altura))
                            .measuredOn(LocalDateTime.parse(dataCalc.replace(" ", "T")))
                            .identifiedBy(Integer.parseInt(id))
                            .build();
                    todosHistoryEntries.add(entry);

                }
            }
        }catch (SQLException exception){
            throw new RuntimeException(exception);
        }
        return todosHistoryEntries;
    }
    public static HistoricoPeso getHistoricoEntry (HistoricoPeso entrada){
        //SQL query to get a single entry WHERE id = id
        return new HistoricoPesoBuilder().build();
    }
    public static void removeHistoricoEntry(String id){
        System.out.println(id);
        //SQL query to remove entry WHERE id = id
    }
}

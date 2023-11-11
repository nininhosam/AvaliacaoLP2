package net.flpes.avaliacaolp2.utils;

import net.flpes.avaliacaolp2.keys.DBKeys;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.models.AlunoBuilder;

import java.sql.*;
import java.time.LocalDate;
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
                System.out.println("User not found");
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
    public static void removeAluno(Aluno aluno){
        String sql = "delete from alunos where cpf=?";
        try {

            Connection connection =  getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, aluno.getCpf());
            stmt.execute();
            stmt.close();
            connection.close();

        }
        catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    public static void updateAluno(Aluno aluno){
        String sql = "update alunos set nome=?, peso=?, altura=? where cpf = ?";
        try {

            Connection connection =  getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, String.valueOf(aluno.getPeso()));
            stmt.setString(3, String.valueOf(aluno.getAltura()));
            stmt.setString(4, aluno.getCpf());
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
                System.out.println("User not found");

            }else {
                rs.next();
                AlunoBuilder builder = new AlunoBuilder()
                        .named(rs.getString("cpf"))
                        .withCpf(rs.getString("nome"))
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

//    public static void logInUser(ActionEvent event, User user){
//        Connection connection = null;
//        PreparedStatement stmt = null;
//        ResultSet rspassword = null;
//        try {
//            connection = getConnection();
//            stmt = connection.prepareStatement("SELECT password from users where username=?");
//            stmt.setString(1, user.getUsername());
//            rspassword = stmt.executeQuery();
//
//            if(!rspassword.isBeforeFirst()){
//                System.out.println("User not found");
//            }else {
//                while (rspassword.next()){
//                    String retrievedpassword = rspassword.getString("password");
//                    if (retrievedpassword.equals(user.getPassword())){
//                        GUIUtils.changeScene(event,"/fxml/upload.fxml","Duck - Upload");
//                    }else {
//                        System.out.println("password does not match username");
//                    }
//                }
//            }
//
//        }catch(SQLException e){
//            e.printStackTrace();
//        }finally {
//            if (rspassword != null){
//                try {
//                    rspassword.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//            if (stmt != null){
//                try {
//                    stmt.close();
//                }catch (SQLException e){
//                    e.printStackTrace();
//                }
//            }
//            if (connection != null){
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }

}

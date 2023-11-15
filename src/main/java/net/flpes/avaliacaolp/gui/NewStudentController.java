package net.flpes.avaliacaolp.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.flpes.avaliacaolp.models.Aluno;
import net.flpes.avaliacaolp.models.AlunoBuilder;
import net.flpes.avaliacaolp.utils.DBUtils;
import net.flpes.avaliacaolp.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class NewStudentController implements Initializable {
    @FXML
    private TextField tf_Nome;
    @FXML
    private TextField tf_CPF;
    @FXML
    private TextField tf_Peso;
    @FXML
    private TextField tf_Altura;
    @FXML
    private DatePicker dp_Nasc;
    @FXML
    private Button btn_Register;
    @FXML
    private Button btn_Back;

    private void emptyAlunoFields(){
        // Self-explanatory
        tf_Nome.setText("");
        tf_CPF.setText("");
        dp_Nasc.setValue(null);
        tf_Peso.setText("");
        tf_Altura.setText("");
    }
    private boolean cpfIsValid(String string){
        // Uses RegEx to certify CPF is valid cpf ===== A valid CPF is 11 numbers long, with optional "." and "-";
        return string.matches("([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2})");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Adds aluno to database ===== Empties fields if successful
        btn_Register.setOnAction(event -> {

            if (!cpfIsValid(tf_CPF.getText())) System.out.println("CPF invalid!");
            else{
            String treatedCPF = tf_CPF.getText().replace(".","").replace("-", "");
            AlunoBuilder builder = new AlunoBuilder()
                    .named(tf_Nome.getText())
                    .withCpf(treatedCPF)
                    .bornOn(dp_Nasc.getValue())
                    .weighing(Double.parseDouble(tf_Peso.getText()))
                    .standingAt(Double.parseDouble(tf_Altura.getText()));
            Aluno aluno = builder.build();
            DBUtils.addAluno(aluno);
            emptyAlunoFields();
            }
        });

        // Redirects to List of Students
        btn_Back.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students")
        );

    }
}
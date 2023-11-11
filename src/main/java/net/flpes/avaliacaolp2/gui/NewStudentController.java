package net.flpes.avaliacaolp2.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.models.AlunoBuilder;
import net.flpes.avaliacaolp2.utils.DBUtils;
import net.flpes.avaliacaolp2.utils.GUIUtils;

import java.net.URL;
import java.time.LocalDate;
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
        tf_Nome.setText("");
        tf_CPF.setText("");
        dp_Nasc.setValue(null);
        tf_Peso.setText("");
        tf_Altura.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_Register.setOnAction(event -> {
            AlunoBuilder builder = new AlunoBuilder()
                    .named(tf_Nome.getText())
                    .withCpf(tf_CPF.getText())
                    .bornOn(dp_Nasc.getValue())
                    .weighing(Double.parseDouble(tf_Peso.getText()))
                    .standingAt(Double.parseDouble(tf_Altura.getText()));
            Aluno aluno = builder.build();
            DBUtils.addAluno(aluno);
            emptyAlunoFields();
        });

        btn_Back.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students")
        );

    }
}
package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.models.AlunoBuilder;
import net.flpes.avaliacaolp2.utils.DBUtils;

import java.time.LocalDate;

public class NewStudentController {
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
    private Button btn_Send;

    @FXML
    protected void onSendButtonClick() {
        AlunoBuilder builder = new AlunoBuilder()
                .named(tf_Nome.getText())
                .withCpf(tf_CPF.getText())
                .bornOn(dp_Nasc.getValue())
                .weighing(Double.parseDouble(tf_Peso.getText()))
                .standingAt(Double.parseDouble(tf_Altura.getText()));
        Aluno aluno = builder.build();
        DBUtils.addAluno(aluno);
    }
}
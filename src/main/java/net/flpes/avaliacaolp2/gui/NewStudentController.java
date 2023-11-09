package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.flpes.avaliacaolp2.models.Aluno;
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
        String nome = tf_Nome.getText();
        String cpf =  tf_CPF.getText();
        double peso = Double.parseDouble(tf_Peso.getText());
        double altura = Double.parseDouble(tf_Altura.getText());
        LocalDate nasc = dp_Nasc.getValue();
        Aluno aluno = new Aluno(cpf, nome, nasc, peso, altura);
        DBUtils.addAluno(aluno);
    }
}
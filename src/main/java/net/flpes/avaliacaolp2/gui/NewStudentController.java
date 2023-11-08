package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
        String CPF =  tf_CPF.getText();
        double Peso = Double.parseDouble(tf_Peso.getText());
        double Altura = Double.parseDouble(tf_Altura.getText());
        LocalDate Nasc = dp_Nasc.getValue();
    }
}
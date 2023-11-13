package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.utils.DBUtils;
import net.flpes.avaliacaolp2.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryEntryEditController implements Initializable {
    @FXML
    private TextField tf_CPF;
    @FXML
    private TextField tf_Nome;
    @FXML
    private TextField tf_Peso;
    @FXML
    private TextField tf_Altura;
    @FXML
    private DatePicker dp_dataCalc;
    @FXML
    private Button btn_Save;
    @FXML
    private Button btn_Back;
    void lockTf(TextField tf, String value){
        // Sets a fixed value to TextField and locks it from being editted or focused.
        tf.setText(value);
        tf.setEditable(false);
        tf.setFocusTraversable(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set student details
        Aluno aluno = DBUtils.getAluno(StudentListController.alunoCpf);
        lockTf(tf_Nome, aluno.getNome());
        lockTf(tf_CPF, aluno.getCpf());
        tf_Peso.setText(String.valueOf(aluno.getPeso()));
        tf_Altura.setText(String.valueOf(aluno.getAltura()));



        btn_Save.setOnAction(event -> {
            aluno.setPeso(Double.parseDouble(tf_Peso.getText()));
            aluno.setAltura(Double.parseDouble(tf_Altura.getText()));
            DBUtils.updateAluno(aluno);
        });

        btn_Back.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students")
        );


    }
}
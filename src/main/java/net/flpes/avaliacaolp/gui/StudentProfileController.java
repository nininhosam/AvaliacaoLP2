package net.flpes.avaliacaolp.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.flpes.avaliacaolp.models.Aluno;
import net.flpes.avaliacaolp.utils.DBUtils;
import net.flpes.avaliacaolp.utils.GUIUtils;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class StudentProfileController implements Initializable {
    @FXML
    private TextField tf_Nome;
    @FXML
    private TextField tf_CPF;
    @FXML
    private TextField tf_Nascimento;
    @FXML
    private TextField tf_Peso;
    @FXML
    private TextField tf_Altura;
    @FXML
    private Button btn_Save;
    @FXML
    private Button btn_History;
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
        // Set student's current details
        Aluno aluno = DBUtils.getAluno(StudentListController.alunoCpf);
        lockTf(tf_Nome, aluno.getNome());
        lockTf(tf_CPF, aluno.getCpf());
        lockTf(tf_Nascimento, aluno.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        tf_Peso.setText(String.valueOf(aluno.getPeso()));
        tf_Altura.setText(String.valueOf(aluno.getAltura()));

        // Updates Student on DB ===== Does *not* redirect on update.
        btn_Save.setOnAction(event -> {
            // Updates Student with the values on the textfield.
            aluno.setPeso(Double.parseDouble(tf_Peso.getText()));
            aluno.setAltura(Double.parseDouble(tf_Altura.getText()));
            DBUtils.updateAluno(aluno);
        });

        // Returns to List of Students
        btn_Back.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students")
        );

        // Redirects to History List
        btn_History.setOnAction(event -> {
            GUIUtils.changeScene(event, "StudentHistory.fxml", "Student Health History");
        });

    }
}
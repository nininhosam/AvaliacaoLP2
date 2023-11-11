package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.models.AlunoBuilder;
import net.flpes.avaliacaolp2.utils.DBUtils;
import net.flpes.avaliacaolp2.utils.GUIUtils;
import org.w3c.dom.Text;

import java.net.URL;
import java.text.DateFormat;
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
        // Set student details
        Aluno aluno = DBUtils.getAluno(StudentListController.alunoCpf);
        lockTf(tf_Nome, aluno.getNome());
        lockTf(tf_CPF, aluno.getCpf());
        lockTf(tf_Nascimento, aluno.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
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

        btn_History.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students")
        );

    }
}
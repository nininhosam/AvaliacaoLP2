package net.flpes.avaliacaolp2.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import net.flpes.avaliacaolp2.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;


public class StudentListController implements Initializable {
    @FXML
    private Button btn_toRegister;
    @FXML
    private Button btn_toAluno;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_toRegister.setOnAction(event ->
                GUIUtils.changeScene(event, "NewStudent.fxml", "Add new student"));
        btn_toAluno.setOnAction(event ->
                GUIUtils.changeScene(event, "NewStudent.fxml", "student")
        );
    }
}
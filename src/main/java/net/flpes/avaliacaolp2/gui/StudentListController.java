package net.flpes.avaliacaolp2.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.utils.DBUtils;
import net.flpes.avaliacaolp2.utils.GUIUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class StudentListController implements Initializable {
    @FXML
    private Button btn_toRegister;
    @FXML
    private Button btn_toAluno;
    @FXML
    private VBox vb_list;
    HBox selected = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Aluno aluno : Objects.requireNonNull(DBUtils.getAlunos())) {

            Label lbCpf = new Label(aluno.getCpf());
            Label lbNome = new Label(aluno.getNome());
            HBox hBox = new HBox();
            lbCpf.setPrefWidth(80);
            hBox.getChildren().add(lbCpf);
            hBox.getChildren().add(lbNome);
            hBox.setSpacing(20);
            hBox.onMouseClickedProperty().set((MouseEvent t)->{
                if (selected != null){
                    selected.setBackground(Background.EMPTY);
                }
                this.selected = hBox;
                hBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                btn_toAluno.setBackground(new Background(new BackgroundFill(Color.rgb( 51, 204, 170), CornerRadii.EMPTY, Insets.EMPTY)));
            });
            vb_list.getChildren().add(hBox);

        }

        btn_toRegister.setOnAction(event ->
                GUIUtils.changeScene(event, "NewStudent.fxml", "Add new student"));
        btn_toAluno.setOnAction(event ->
                GUIUtils.changeScene(event, "NewStudent.fxml", "student")
        );
    }
}
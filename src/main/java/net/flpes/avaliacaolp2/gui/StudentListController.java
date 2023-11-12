package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.utils.DBUtils;
import net.flpes.avaliacaolp2.utils.GUIUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class StudentListController implements Initializable {
    @FXML
    private Button btn_Add;
    @FXML
    private Button btn_Select;
    @FXML
    private Button btn_Update;
    @FXML
    private Button btn_Delete;
    @FXML
    private VBox vb_list;
    HBox selected = null;
    public static String alunoCpf = null;
    private boolean confirmation = false;
    void activateButton(Button button, String actClass){
        button.getStyleClass().add(actClass);
    }
    void deactivateButton(Button button, String actClass){
        button.getStyleClass().removeAll(actClass);
        button.getStyleClass().add("buttonOff");

    }
    void activateSelectDependent(){
        activateButton(btn_Select, "buttonOn");
        activateButton(btn_Update, "buttonUpdate");
        activateButton(btn_Delete, "buttonDelete");
    }
    void deactivateSelectDependent(){
        deactivateButton(btn_Select, "buttonOn");
        deactivateButton(btn_Update, "buttonUpdate");
        deactivateButton(btn_Delete, "buttonDelete");
        deactivateButton(btn_Delete, "buttonDanger");
    }

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
                    selected.getStyleClass().clear();
                }
                this.selected = hBox;
                hBox.getStyleClass().add("optionSelected");
                activateSelectDependent();
                deactivateButton(btn_Delete, "buttonDanger");
                confirmation = false;
            });
            vb_list.getChildren().add(hBox);

        }

        btn_Add.setOnAction(event ->
                GUIUtils.changeScene(event, "NewStudent.fxml", "Add new student"));

        btn_Update.setOnAction(event -> {
            if (selected != null) {
                alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event, "StudentProfile.fxml", "Student Profile");
            }
        });

        btn_Delete.setOnAction(event -> {
            if (selected != null) {
                if (!confirmation) {
                    deactivateButton(btn_Delete, "buttonDelete");
                    activateButton(btn_Delete, "buttonDanger");
                    confirmation = true;
                } else {
                    alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                    try {
                        DBUtils.removeAluno(alunoCpf);

                        vb_list.getChildren().remove(selected);
                        deactivateSelectDependent();
                        selected = null;
                        confirmation = false;
                    } catch (Exception exc){
                        System.out.println("Failed to delete user");
                    }

                }
            }
        });

        btn_Select.setOnAction(event -> {
            if (selected != null) {
                alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event, "StudentHistory.fxml", "Student Health History");
            }
        });
    }
}
package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.models.HistoricoPeso;
import net.flpes.avaliacaolp2.models.HistoricoPesoBuilder;
import net.flpes.avaliacaolp2.utils.DBUtils;
import net.flpes.avaliacaolp2.utils.GUIUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class StudentHistoryController implements Initializable {
    @FXML
    private Button btn_Back;
    @FXML
    private Button btn_Add;
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
        activateButton(btn_Update, "buttonUpdate");
        activateButton(btn_Delete, "buttonDelete");
    }
    void deactivateSelectDependent(){
        deactivateButton(btn_Update, "buttonUpdate");
        deactivateButton(btn_Delete, "buttonDelete");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        for (Aluno aluno : Objects.requireNonNull(DBUtils.getAlunos())) {
//
//            Label lbCpf = new Label(aluno.getCpf());
//            Label lbNome = new Label(aluno.getNome());
//            HBox hBox = new HBox();
//            lbCpf.setPrefWidth(80);
//            hBox.getChildren().add(lbCpf);
//            hBox.getChildren().add(lbNome);
//            hBox.setSpacing(20);
//            hBox.onMouseClickedProperty().set((MouseEvent t)->{
//                if (selected != null){;
//                    selected.getStyleClass().clear();
//                }
//                this.selected = hBox;
//                hBox.getStyleClass().add("optionSelected");
//                activateSelectDependent();
//            });
//            vb_list.getChildren().add(hBox);
//
//        }

        btn_Back.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students"));


        btn_Update.setOnAction(event -> {
            if (selected != null) {
                alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                //Mayhaps another tab?
//                GUIUtils.changeScene(event, "StudentProfile.fxml", "Student Profile");
                //DBUtils.addHistorico(historico)
            }
        });

        btn_Delete.setOnAction(event -> {
            if (selected != null) {
                alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                vb_list.getChildren().remove(selected);
                deactivateSelectDependent();
                selected = null;
                //DBUtils.removeHistorico(historico);
            }
        });

        btn_Add.setOnAction(event ->{
            //Adicionar histórico ao banco e VBox
        });
    }
}
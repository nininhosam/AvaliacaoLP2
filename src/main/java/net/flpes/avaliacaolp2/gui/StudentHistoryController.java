package net.flpes.avaliacaolp2.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.flpes.avaliacaolp2.models.Aluno;
import net.flpes.avaliacaolp2.models.HistoricoPeso;
import net.flpes.avaliacaolp2.models.HistoricoPesoBuilder;
import net.flpes.avaliacaolp2.utils.DBUtils;
import net.flpes.avaliacaolp2.utils.GUIUtils;

import java.net.URL;
import java.time.format.DateTimeFormatter;
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
    @FXML
    private Label lb_TitleName;
    HBox selected = null;
    public String entryId;
    private boolean confirmation = false;
    private Aluno referencia = DBUtils.getAluno(StudentListController.alunoCpf);
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
        deactivateButton(btn_Delete, "buttonDanger");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lb_TitleName.setText(referencia.getNome());
        for (HistoricoPeso entrada : Objects.requireNonNull(DBUtils.getHistoricoCompleto(referencia))) {

            Label lbId = new Label(String.valueOf(entrada.getId()));
            Label lbData = new Label(entrada.getDataCalculo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm")));
            Label lbAltura = new Label(String.valueOf(entrada.getAltura()));
            Label lbPeso = new Label(String.valueOf(entrada.getPeso()));
            HBox hBox = new HBox();

            lbPeso.setPrefWidth(80);
            hBox.getChildren().add(lbId);
            hBox.getChildren().add(lbData);
            hBox.getChildren().add(lbAltura);
            hBox.getChildren().add(lbPeso);
            hBox.setSpacing(20);
            hBox.onMouseClickedProperty().set((MouseEvent t)->{

                if (selected != null){;
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



        btn_Back.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students"));



        btn_Update.setOnAction(event -> {
            if (selected != null) {

                // THIS MAYBE NOT USED. or maybe will, for the update method -
                //Mayhaps another tab?
//                GUIUtils.changeScene(event, "StudentProfile.fxml", "Student Profile");
                //DBUtils.addHistorico(historico)

            }
        });



        btn_Delete.setOnAction(event -> {
            if (selected != null) {
                if (!confirmation) {

                    deactivateButton(btn_Delete, "buttonDelete");
                    activateButton(btn_Delete, "buttonDanger");
                    confirmation = true;

                } else {

                    entryId = ((Label) selected.getChildren().get(0)).getText();
                    try {
                        DBUtils.removeHistoricoEntry(entryId);
                        System.out.println(entryId);
                        vb_list.getChildren().remove(selected);
                        deactivateSelectDependent();
                        selected = null;
                        confirmation = false;
                    } catch (Exception exc) {
                        System.out.println("Failed to delete user");
                    }

                }
            }
        });



        btn_Add.setOnAction(event ->{
            //Adicionar VBox
            HistoricoPesoBuilder builder = new HistoricoPesoBuilder();
                        builder.ofAluno(referencia)
                                .standingAt(referencia.getAltura())
                                .weighing(referencia.getPeso());
            HistoricoPeso historico = builder.build();
            DBUtils.addHistoricoEntry(historico);
        });
    }


}
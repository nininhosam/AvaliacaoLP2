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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;


public class StudentHistoryController implements Initializable {
    @FXML
    private Button btn_Back;
    @FXML
    private Button btn_Archive;
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
    public static String entryId;
    private boolean confirmation = false;
    private final Aluno referencia = DBUtils.getAluno(StudentListController.alunoCpf);
    void activateButton(Button button, String actClass){
        button.getStyleClass().add(actClass);
    }
    void deactivateButton(Button button, String actClass){
        button.getStyleClass().removeAll(actClass);
        button.getStyleClass().add("buttonOff");

    }
    void activateSelectDependent(){
        activateButton(btn_Archive, "buttonAdd");
        activateButton(btn_Update, "buttonUpdate");
        activateButton(btn_Delete, "buttonDelete");
    }
    void deactivateSelectDependent(){
        deactivateButton(btn_Archive, "buttonAdd");
        deactivateButton(btn_Update, "buttonUpdate");
        deactivateButton(btn_Delete, "buttonDelete");
        deactivateButton(btn_Delete, "buttonDanger");
    }

    private HBox addNewRow(String id, String data, String altura, String peso){
        Label lbId = new Label(id);
        Label lbData = new Label(data);
        Label lbAltura = new Label(altura);
        Label lbPeso = new Label(peso);
        HBox hBox = new HBox();
        lbId.setPrefWidth(15);
        lbData.setPrefWidth(100);
        lbPeso.setPrefWidth(40);
        lbAltura.setPrefWidth(40);
        hBox.getChildren().add(lbId);
        hBox.getChildren().add(lbData);
        hBox.getChildren().add(lbAltura);
        hBox.getChildren().add(lbPeso);
        hBox.setSpacing(20);
        vb_list.getChildren().add(hBox);
        return hBox;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lb_TitleName.setText(referencia.getNome());
        HBox titles = addNewRow("ID", "Data Medida", "Altura", "Peso");
        titles.getStyleClass().add("tableTitle");
        for (HistoricoPeso entrada : Objects.requireNonNull(DBUtils.getHistoricoCompleto(referencia))) {

            String id =String.valueOf(entrada.getId());
            String data = entrada.getDataCalculo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm"));
            String altura = String.valueOf(entrada.getAltura());
            String peso = String.valueOf(entrada.getPeso());

            HBox hBox = addNewRow(id, data, altura, peso);
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

        }



        btn_Back.setOnAction(event ->
                GUIUtils.changeScene(event, "StudentList.fxml", "List of students"));

        btn_Archive.setOnAction(event -> {
            if (selected != null) {
                entryId = ((Label) selected.getChildren().get(0)).getText();
                HistoricoPeso hist = DBUtils.getHistoricoEntry(entryId);
                try {
                    hist.gravarArquivo();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        btn_Update.setOnAction(event -> {
            if (selected != null) {
                entryId = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event, "HistoryEntryEdit.fxml", "Student Profile");
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
            HistoricoPesoBuilder builder = new HistoricoPesoBuilder();
                        builder.ofAluno(referencia)
                                .standingAt(referencia.getAltura())
                                .weighing(referencia.getPeso());
            HistoricoPeso historico = builder.build();
            DBUtils.addHistoricoEntry(historico);
            GUIUtils.changeScene(event, "StudentHistory.fxml", "Student Health History");
        });
    }


}
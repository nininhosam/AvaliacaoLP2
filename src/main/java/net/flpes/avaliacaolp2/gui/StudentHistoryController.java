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

import java.io.IOException;
import java.net.URL;
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

        // Activates buttons that depend on a selected field to remain active
        activateButton(btn_Archive, "buttonAdd");
        activateButton(btn_Update, "buttonUpdate");
        activateButton(btn_Delete, "buttonDelete");

    }
    void deactivateSelectDependent(){

        // Deactivates buttons that depend on a selected field to remain active
        deactivateButton(btn_Archive, "buttonAdd");
        deactivateButton(btn_Update, "buttonUpdate");
        deactivateButton(btn_Delete, "buttonDelete");
        deactivateButton(btn_Delete, "buttonDanger");

    }

    private HBox addNewRow(String id, String data, String altura, String peso){

        // Standard method to add a row with all defined columns
        Label lbId = new Label(id);
        lbId.setPrefWidth(15);

        Label lbData = new Label(data);
        lbData.setPrefWidth(100);

        Label lbAltura = new Label(altura);
        lbAltura.setPrefWidth(40);

        Label lbPeso = new Label(peso);
        lbPeso.setPrefWidth(40);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(lbId, lbData, lbAltura, lbPeso);
        hBox.setSpacing(20);
        vb_list.getChildren().add(hBox);

        return hBox;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set table title
        lb_TitleName.setText(referencia.getNome());
        HBox titles = addNewRow("ID", "Data Medida", "Altura", "Peso");
        titles.getStyleClass().add("tableTitle");

        // Set table elements ====== Based on the static element "StudentListController.alunoCpf"
        for (HistoricoPeso entrada : Objects.requireNonNull(DBUtils.getHistoricoCompleto(referencia))) {

            // Database query results
            String id =String.valueOf(entrada.getId());
            String data = entrada.getDataCalculo().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm"));
            String altura = String.valueOf(entrada.getAltura());
            String peso = String.valueOf(entrada.getPeso());
            // Add to table
            HBox hBox = addNewRow(id, data, altura, peso);

            hBox.onMouseClickedProperty().set((MouseEvent t)->{

                // If there is a selected row, Unselect it first
                if (selected != null){;
                    selected.getStyleClass().clear();
                }
                // Select this row  ===== resets Delete button to its default state
                this.selected = hBox;
                hBox.getStyleClass().add("optionSelected");
                activateSelectDependent();
                deactivateButton(btn_Delete, "buttonDanger");
                confirmation = false;

            });

        }


        // Returns to List of Students
        btn_Back.setOnAction(event -> {
            GUIUtils.changeScene(event, "StudentList.fxml", "List of students");
        });

        // Writes to pesoLog.txt, located on /Temp file
        btn_Archive.setOnAction(event -> {
            if (selected != null) {
                // Gets current entry from DB overwriting and using static "entryId";
                entryId = ((Label) selected.getChildren().get(0)).getText();
                HistoricoPeso hist = DBUtils.getHistoricoEntry(entryId);
                try {
                    // Adds text to /Temp/pesoLog.txt
                    hist.gravarArquivo();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Two step deletion of current entry
        btn_Delete.setOnAction(event -> {
            if (selected != null) {
                if (!confirmation) {
                    // If it's the first time clicking, Enters a CONFIRM state
                    deactivateButton(btn_Delete, "buttonDelete");
                    activateButton(btn_Delete, "buttonDanger");
                    confirmation = true;

                } else {
                    // If it's in a CONFIRM state, deletes current Entry, and overwrites entryId
                    entryId = ((Label) selected.getChildren().get(0)).getText();
                    try {
                        // Removes entry from DB and from the displayed table ===== Exits CONFIRM state
                        DBUtils.removeHistoricoEntry(entryId);
                        vb_list.getChildren().remove(selected);
                        deactivateSelectDependent();
                        selected = null;
                        confirmation = false;
                    } catch (Exception exc) {
                        System.out.println("Failed to delete entry");
                    }

                }
            }
        });

        // Redirects to Entry Edit window
        btn_Update.setOnAction(event -> {
            if (selected != null) {
                // Overwrite static "entryID" to selected entry's ID
                entryId = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event, "HistoryEntryEdit.fxml", "Edit History Entry");
            }
        });

        // Adds new entry to DB using student's current weight and height, and the current date
        btn_Add.setOnAction(event ->{
            // Student is built based on StudentListController.alunoCpf
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
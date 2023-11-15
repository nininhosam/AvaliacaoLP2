package net.flpes.avaliacaolp.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.flpes.avaliacaolp.models.Aluno;
import net.flpes.avaliacaolp.utils.DBUtils;
import net.flpes.avaliacaolp.utils.GUIUtils;

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

        // Activates buttons that depend on a selected field to remain active
        activateButton(btn_Select, "buttonOn");
        activateButton(btn_Update, "buttonUpdate");
        activateButton(btn_Delete, "buttonDelete");
    }
    void deactivateSelectDependent(){

        // Deactivates buttons that depend on a selected field to remain active
        deactivateButton(btn_Select, "buttonOn");
        deactivateButton(btn_Update, "buttonUpdate");
        deactivateButton(btn_Delete, "buttonDelete");
        deactivateButton(btn_Delete, "buttonDanger");
    }

    private HBox addNewRow(String cpf, String nome){

        // Standard method to add a row with all defined columns
        Label lbCpf = new Label(cpf);
        lbCpf.setPrefWidth(80);

        Label lbNome = new Label(nome);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(lbCpf, lbNome);
        hBox.setSpacing(20);
        vb_list.getChildren().add(hBox);

        return hBox;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set table title
        HBox titles = addNewRow("CPF", "Aluno");
        titles.getStyleClass().add("tableTitle");

        // Set table elements
        for (Aluno aluno : Objects.requireNonNull(DBUtils.getAlunos())) {

            // Database query results
            String cpf = aluno.getCpf();
            String nome = aluno.getNome();
            // Add to table
            HBox hBox = addNewRow(cpf, nome);

            hBox.onMouseClickedProperty().set((MouseEvent t)->{

                // If there is a selected row, Unselect it first
                if (selected != null){
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

        // Redirects to Add New Student window
        btn_Add.setOnAction(event ->
                GUIUtils.changeScene(event, "NewStudent.fxml", "Add New Student"));

        // Redirects to selected student's Health History page ===== Sets static alunoCpf to selected Student's Cpf.
        btn_Select.setOnAction(event -> {
            if (selected != null) {
                alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event, "StudentHistory.fxml", "Student Health History");
            }
        });

        // Redirects to selected student's Profile Edit window ===== Sets static alunoCpf to selected Student's Cpf.
        btn_Update.setOnAction(event -> {
            if (selected != null) {
                alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                GUIUtils.changeScene(event, "StudentProfile.fxml", "Edit Student Profile");
            }
        });

        // Two step deletion of current entry
        btn_Delete.setOnAction(event -> {
            if (selected == null) return;
            if (!confirmation) {
                // If it's the first time clicking, Enters a CONFIRM state
                deactivateButton(btn_Delete, "buttonDelete");
                activateButton(btn_Delete, "buttonDanger");
                confirmation = true;
            } else {
                // If it's in a CONFIRM state, deletes current Student, and overwrites alunoCpf
                alunoCpf = ((Label) selected.getChildren().get(0)).getText();
                try {
                    // Removes student from DB and from the displayed table ===== Exits CONFIRM state
                    DBUtils.removeAluno(alunoCpf);
                    vb_list.getChildren().remove(selected);
                    deactivateSelectDependent();
                    selected = null;
                    confirmation = false;
                } catch (Exception exc){
                    System.out.println("Failed to delete user");
                }

            }
        });

    }
}
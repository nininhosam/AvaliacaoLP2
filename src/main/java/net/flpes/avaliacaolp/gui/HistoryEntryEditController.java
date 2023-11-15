package net.flpes.avaliacaolp.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import net.flpes.avaliacaolp.models.HistoricoPeso;
import net.flpes.avaliacaolp.utils.DBUtils;
import net.flpes.avaliacaolp.utils.GUIUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryEntryEditController implements Initializable {
    @FXML
    private TextField tf_CPF;
    @FXML
    private TextField tf_Nome;
    @FXML
    private TextField tf_Peso;
    @FXML
    private TextField tf_Altura;
    @FXML
    private DatePicker dp_dataCalc;
    @FXML
    private Button btn_Save;
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
        // Set entry's current details
        HistoricoPeso entry = DBUtils.getHistoricoEntry(StudentHistoryController.entryId);
        lockTf(tf_Nome, entry.getNome());
        lockTf(tf_CPF, entry.getCpf());
        tf_Peso.setText(String.valueOf(entry.getPeso()));
        tf_Altura.setText(String.valueOf(entry.getAltura()));

        // Redirects to History List when done
        btn_Save.setOnAction(event -> {
            // Updates Entry with the values on the textfield.
            entry.setPeso(Double.parseDouble(tf_Peso.getText()));
            entry.setAltura(Double.parseDouble(tf_Altura.getText()));
            if (dp_dataCalc.getValue() != null) entry.setDataCalculo(dp_dataCalc.getValue().atStartOfDay());
            DBUtils.updateHistorico(entry);
            GUIUtils.changeScene(event, "StudentHistory.fxml", "Student Health History");
        });

        // Returns to History List
        btn_Back.setOnAction(event ->
            GUIUtils.changeScene(event, "StudentHistory.fxml", "Student Health History")
        );


    }
}
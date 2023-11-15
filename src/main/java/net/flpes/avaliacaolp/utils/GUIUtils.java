package net.flpes.avaliacaolp.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIUtils {
    // Self-explanatory, Utility for changing the current window
    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader(GUIUtils.class.getResource("/net/flpes/avaliacaolp/" +fxmlFile));
            root = loader.load();
        }catch (IOException exception){
            exception.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }
}
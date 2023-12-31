package net.flpes.avaliacaolp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentList.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 500);
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/net/flpes/images/BalanceIcon.png"))));
        stage.setTitle("List of Students");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
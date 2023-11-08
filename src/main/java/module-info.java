module net.flpes.avaliacaolp2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.flpes.avaliacaolp2 to javafx.fxml;
    exports net.flpes.avaliacaolp2;
    exports net.flpes.avaliacaolp2.gui;
    opens net.flpes.avaliacaolp2.gui to javafx.fxml;
}
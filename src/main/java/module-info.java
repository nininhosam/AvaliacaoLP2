module net.flpes.avaliacaolp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens net.flpes.avaliacaolp to javafx.fxml;
    exports net.flpes.avaliacaolp;
    exports net.flpes.avaliacaolp.gui;
    opens net.flpes.avaliacaolp.gui to javafx.fxml;
}
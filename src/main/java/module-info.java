module org.pokergame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens org.pokergame.gui to javafx.fxml;
    exports org.pokergame.gui;
}
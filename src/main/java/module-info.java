module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;



    exports com.example.demo.gui;
    opens com.example.demo.gui to javafx.fxml;
}
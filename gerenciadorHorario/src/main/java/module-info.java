module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.example.controller to javafx.fxml;
    opens org.example.classes to javafx.base;


    exports org.example;
}
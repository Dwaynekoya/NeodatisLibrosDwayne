module NeodatisLibrosDwayne {
    requires javafx.controls;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires neodatis.odb;
    requires java.sql;

    exports vista;
    opens vista to javafx.fxml;
    exports modelo;
    opens modelo to neodatis.odb;
    exports tests;
    exports controlador;
}
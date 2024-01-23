package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ControlMain {
    //variables para acceder a elementos de la interfaz gráfica: los nombres deben coincidir con sus fx:id
    @FXML
    private ListView listaAutores;
    @FXML
    private ListView listaLibros;

    public ControlMain() {
        llenarListaAutores();
        llenarListaLibros();
    }

    private static void llenarListaLibros() {
    }

    private static void llenarListaAutores() {
    }
}

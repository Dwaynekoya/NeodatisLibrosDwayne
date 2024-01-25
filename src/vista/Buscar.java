package vista;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import modelo.Autor;
import modelo.Libro;

import java.io.IOException;

public class Buscar extends Application {
    @FXML
    private Label labelVacioAutor;
    @FXML
    private Label labelVacioLibro;
    private Scene sceneBuscar;
    private ListView<Autor> listaAutores; private ListView<Libro> listaLibros;


    @Override
    public void start(Stage stage) throws Exception {
        launchBuscar(stage);
    }

    private void launchBuscar(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("buscar.fxml"));
        } catch (IOException e) {
            System.out.println("Error asociando vista para añadir elementos");
            throw new RuntimeException(e);
        }
        sceneBuscar = new Scene(root);
        stage.setScene(sceneBuscar);
        stage.show();
    }

    public void buscarAutor(ActionEvent actionEvent) {
        //TODO: Consulta And
        //primero checkea cuantos campos hay llenos. si todos estan vacios no deja buscar y muestra label.
        //si solo hay 1 usa busqueda normal
    }

    public void buscarLibro(ActionEvent actionEvent) {
        //TODO: Consulta And
        //primero checkea cuantos campos hay llenos. si todos estan vacios no deja buscar y muestra label.
        //si solo hay 1 usa busqueda normal
    }
    /**
     * Cierra la ventana cuando se pulsa el botón adecuado
     * @param actionEvent
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}

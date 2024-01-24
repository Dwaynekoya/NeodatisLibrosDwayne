package vista;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Buscar extends Application {
    private Scene sceneBuscar;
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
    }

    /**
     * Cierra la ventana cuando se pulsa el botón adecuado
     * @param actionEvent
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}

package vista;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Detalles extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        launchDetalles(stage);
    }

    private void launchDetalles(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("detalles.fxml")));
        } catch (IOException e) {
            System.out.println("Error asociando vista para ver y modificar detalles de un elemento");
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(root));
        Platform.runLater(()->fillDetalles());
    }

    private void fillDetalles() {

    }
}

package vista;

import controlador.ControlBBDD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainScreen extends Application {
    private Scene mainScreen;
    Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage=stage;
        stage.setOnCloseRequest(windowEvent -> cerrarBBDD());
        launchMain();
        stage.show();
    }

    private void cerrarBBDD() {
        //LAST STEP: close db
        ControlBBDD.cerrarBBDD();
    }

    private void launchMain() {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainscreen.fxml")));
        } catch (IOException e) {
            System.out.println("Error asociando vista principal a la pantalla.");
            throw new RuntimeException(e);
        }
        mainScreen = new Scene(root);
        stage.setScene(mainScreen);
    }

}

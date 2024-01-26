package vista;


import controlador.ControlBBDD;
import controlador.ControlLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login extends Application{
    private Scene login;

    @Override
    public void start(Stage stage) throws Exception {
        launchLogin(stage);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> ControlBBDD.cerrarBBDD());
    }

    private void launchLogin(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        } catch (IOException e) {
            System.out.println("Error asociando vista de inicio de sesión a la pantalla.");
            throw new RuntimeException(e);
        }
        login = new Scene(root);
        stage.setScene(login);
    }

}

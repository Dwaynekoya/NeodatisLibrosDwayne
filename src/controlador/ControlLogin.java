package controlador;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;
import org.neodatis.odb.Objects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ControlLogin extends Application {

    //variables para acceder a elementos de la interfaz gráfica: los nombres deben coincidir con sus fx:id
    @FXML
    public TextField textoUsuario;
    @FXML
    private PasswordField textoContra;
    @FXML
    private Label labelDatosIncorrectos;
    private String username;
    private String password;

    /**
     * Método para entrar a la aplicación principal
     * @param actionEvent: botón que ha lanzado el metodo
     */
    public void iniciarSesion(ActionEvent actionEvent) {
        recogerDatos();
        Map criteriosBusqueda = new HashMap<>();
        criteriosBusqueda.put("nombreUsuario",username);
        criteriosBusqueda.put("contra", password);
        Objects usuario = ControlBBDD.busquedaCompleja(Usuario.class, criteriosBusqueda);
        //busquedaCompleja devuelve null si no hay coincidencias.
        if (usuario!=null){
            openMainWindow(actionEvent);
        } else {
            labelDatosIncorrectos.setText("Datos incorrectos.");
            labelDatosIncorrectos.setVisible(true);
        }
    }

    /**
     * Método para lanzar la ventana principal.
     * @param actionEvent -> evento del botón
     */
    private void openMainWindow(ActionEvent actionEvent) {
        try {
            new ControlMainScreen().start(new Stage());
        } catch (Exception e) {
            System.out.println("Error creando ventana principal.");
            throw new RuntimeException(e);
        }
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    /**
     * Guarda un objeto Usuario en la base de datos usuarios.ND
     * @param actionEvent -> evento del botón
     */
    public void crearUser(ActionEvent actionEvent) {
        if (!recogerDatos()){
            labelDatosIncorrectos.setText("Rellene ambos campos.");
            labelDatosIncorrectos.setVisible(true);
            return;
        }
        Usuario usuario = new Usuario(username, password);
        boolean added = ControlBBDD.addObject(usuario);
        if (!added){
            labelDatosIncorrectos.setText("Ya existe un usuario con ese nombre.");
            labelDatosIncorrectos.setVisible(true);
        }
    }
    /**
     * Toma los valores de los textfields y los asigna a Strings a nivel de clase
     * @return false si hay un campo vacío, true si ambos han sido completados
     */
    private boolean recogerDatos() {
        username = textoUsuario.getText();
        password = textoContra.getText();
        if (username.isEmpty() ||  password.isEmpty()) return false;
        return true;
    }
    private Scene login;

    @Override
    public void start(Stage stage) throws Exception {
        launchLogin(stage);
        stage.setTitle("Gestión editorial");
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> ControlBBDD.cerrarBBDD()); //si cerramos esta vista, se cierra la BBDD
    }

    /**
     * Método que asocia la vista xml a la clase controlador
     * @param stage-> ventana a mostrar
     */
    private void launchLogin(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(java.util.Objects.requireNonNull(getClass().getResource("../vista/login.fxml")));
        } catch (IOException e) {
            System.out.println("Error asociando vista de inicio de sesión a la pantalla.");
            throw new RuntimeException(e);
        }
        login = new Scene(root);
        stage.setScene(login);
    }
}

package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import vista.MainScreen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ControlLogin {

    //variables para acceder a elementos de la interfaz gráfica: los nombres deben coincidir con sus fx:id
    @FXML
    public TextField textoUsuario;
    @FXML
    private PasswordField textoContra;
    @FXML
    private Label labelDatosIncorrectos;
    private String username;
    private String password;
    private final String nombreBBDD = "usuarios.ND";
    private ODB odb;

    public ControlLogin() {
        odb = ODBFactory.open(nombreBBDD);
    }

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

    private void openMainWindow(ActionEvent actionEvent) {
        try {
            new MainScreen().start(new Stage());
        } catch (Exception e) {
            System.out.println("Error creando ventana principal.");
            throw new RuntimeException(e);
        }
        odb.close();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    /**
     * Guarda un objeto Usuario en la base de datos usuarios.ND
     * @param actionEvent
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
}

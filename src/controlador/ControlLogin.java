package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import vista.MainScreen;

import java.util.Arrays;


public class ControlLogin {

    //variables para acceder a elementos de la interfaz gráfica: los nombres deben coincidir con sus fx:id
    @FXML
    public TextField textoUsuario;
    @FXML
    private PasswordField textoContra;
    private String username;
    private char[] password;
    private final String nombreBBDD = "usuarios.ND";
    private ODB odb;

    public ControlLogin() {
        odb = ODBFactory.open(nombreBBDD);
    }

    public void iniciarSesion(ActionEvent actionEvent) {
        recogerDatos();
        //TODO: buscar en base de datos
        //for now:
        if (username.matches("admin")&& Arrays.equals(password, "admin".toCharArray())){
            openMainWindow(actionEvent);
        }
        //TODO: mostrar mensaje: login incorrecto
        System.out.println("LOGIN INCORRECTO");
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
            //TODO: MOSTRAR MENSAJE SI FALTA UN CAMPO POR RELLENAR
            return;
        }
        Usuario usuario = new Usuario(username, password);
        //TODO: comprobacion de que no existe el usuario (comprobar que no se repite el nombre)
        odb.store(usuario);
        odb.commit();
    }
    /**
     * Toma los valores de los textfields y los asigna a Strings a nivel de clase
     * @return
     */
    private boolean recogerDatos() {
        //TODO: Comprobacion de que no hay campos vacíos
        //si uno esta vacio devuelve false, en caso de que devuelva false no se continua con los otros metodos
        username = textoUsuario.getText();
        password = textoContra.getText().toCharArray();
        return true;
    }
}

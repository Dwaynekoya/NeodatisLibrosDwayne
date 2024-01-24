package vista;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Add extends Application {
    private Scene add;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private CheckBox checkActivo;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextArea txtSinopsis;
    @FXML
    private Spinner spinnerFecha;
    @FXML
    private ComboBox comboboxAutor;

    /**
     * Crea objeto de tipo Autor si todos los campos están llenos
     * Inserta el objeto en la base de datos usando la clase ControlBBDD
     * @param actionEvent
     */
    public void addAutor(ActionEvent actionEvent) {
        String nombre = txtNombre.getText();
        //TODO
    }
    /**
     * Crea objeto de tipo Libro si todos los campos están llenos
     * Inserta el objeto en la base de datos usando la clase ControlBBDD
     * @param actionEvent
     */
    public void addLibro(ActionEvent actionEvent) {
        //todo
    }
    /**
     * Cierra la ventana cuando se pulsa el botón adecuado
     * @param actionEvent
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void start(Stage stage) throws Exception {
        launchAdd(stage);
    }

    private void launchAdd(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("add.fxml"));
        } catch (IOException e) {
            System.out.println("Error asociando vista para añadir elementos");
            throw new RuntimeException(e);
        }
        add = new Scene(root);
        stage.setScene(add);
    }
}

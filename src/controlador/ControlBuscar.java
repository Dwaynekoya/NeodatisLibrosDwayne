package controlador;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Autor;
import modelo.Libro;
import org.neodatis.odb.Objects;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControlBuscar extends Application implements Initializable {
    @FXML
    private Label labelVacioAutor;
    @FXML
    private Label labelVacioLibro;
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
    private DatePicker datePicker;
    @FXML
    private ComboBox comboboxAutor;
    private Scene sceneBuscar;
    @FXML
    private ListView<Autor> listaResultadosAutor;
    @FXML
    private ListView<Libro> listaResultadosLibro;
    private Map criteriosBusquedaAutor, criteriosBusquedaLibro;

    @Override
    public void start(Stage stage) throws Exception {
        launchBuscar(stage);
    }

    /**
     * asocia el fxml de la vista a este controlador
     * @param stage: ventana donde mostraremos la vista
     */
    private void launchBuscar(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../vista/buscar.fxml"));
        } catch (IOException e) {
            System.out.println("Error asociando vista para añadir elementos");
            throw new RuntimeException(e);
        }
        sceneBuscar = new Scene(root);
        stage.setScene(sceneBuscar);
        stage.setTitle("Busqueda");
        stage.show();

    }

    /**
     * usado para asegurarnos de que los objetos del fxml han sido inicializados antes de usarlos en los metodos a los que
     * se llama en este
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(this::listenersCampos);
        Platform.runLater(()-> comboboxAutor.setItems(ControlBBDD.listaObservableAutores()));
    }

    /**
     * Busca un autor según los campos de búsqueda en la ventana
     * @param actionEvent: boton pulsado
     */

    public void buscarAutor(ActionEvent actionEvent) {
        if (criteriosBusquedaAutor.isEmpty()) {
            ObservableList result =FXCollections.observableArrayList(ControlBBDD.buscar("activo", checkActivo.isSelected(), Autor.class));
            listaResultadosAutor.setItems(result);
            return;
        }
        Objects busqueda = ControlBBDD.busquedaCompleja(Autor.class,criteriosBusquedaAutor);
        if (busqueda ==null){
            listaResultadosAutor.getItems().clear();
        }else {
            ObservableList result =FXCollections.observableArrayList(busqueda);
            listaResultadosAutor.setItems(result);
        }
    }

    /**
     * Busca un libro según los campos de búsqueda en la ventana
     * @param actionEvent: boton pulsado
     */
    public void buscarLibro(ActionEvent actionEvent) {
        listaResultadosLibro.getItems().clear(); //clears list in case there are no results
        if (criteriosBusquedaLibro.isEmpty()) {
            labelVacioLibro.setVisible(true);
            return;
        }
        //Objects busqueda = ControlBBDD.buscar("fecha_lanzamiento", criteriosBusquedaLibro.get("fecha_lanzamiento"), Libro.class);
        Objects busqueda = ControlBBDD.busquedaCompleja(Libro.class,criteriosBusquedaLibro);
        if (busqueda !=null){
            ObservableList result =FXCollections.observableArrayList(busqueda);
            listaResultadosLibro.setItems(result);
        }
    }

    /**
     * Cierra la ventana cuando se pulsa el botón adecuado
     * @param actionEvent: boton pulsado
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
    /**
     * añade listeners a cada campo de busqueda en la vista
     */
    private void listenersCampos() {
        criteriosBusquedaAutor = new HashMap<>();
        criteriosBusquedaLibro = new HashMap<>();

        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                criteriosBusquedaAutor.remove("nombre");
            } else {
                criteriosBusquedaAutor.put("nombre", newValue);
            }

        });

        txtApellidos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                criteriosBusquedaAutor.remove("apellidos");
            } else {
                criteriosBusquedaAutor.put("apellidos", newValue);
            }
        });

        checkActivo.selectedProperty().addListener((observable, oldValue, newValue) -> {
            criteriosBusquedaAutor.put("activo", newValue);

        });

        txtTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                criteriosBusquedaLibro.remove("nombre");
            } else {
                criteriosBusquedaLibro.put("nombre", newValue);
            }
        });

        txtGenero.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                criteriosBusquedaLibro.remove("genero");
            } else {
                criteriosBusquedaLibro.put("genero", newValue);
            }
        });

        txtSinopsis.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                criteriosBusquedaLibro.remove("sinopsis");
            } else {
                criteriosBusquedaLibro.put("sinopsis", newValue);
            }
        });

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                java.util.Date selectedDate = java.sql.Date.valueOf(newValue);
                criteriosBusquedaLibro.put("fecha_lanzamiento", selectedDate);
            }
        });

        comboboxAutor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                criteriosBusquedaLibro.put("autor.nombre", ((Autor) newValue).getNombre());
                criteriosBusquedaLibro.put("autor.apellidos", ((Autor) newValue).getApellidos());
            }
        });
    }

}

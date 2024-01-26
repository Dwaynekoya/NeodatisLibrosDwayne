package vista;

import controlador.ControlBBDD;
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
import modelo.Autor;
import modelo.Libro;
import org.neodatis.odb.Objects;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Buscar extends Application implements Initializable {
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
    private boolean camposVaciosAutor; private boolean camposVaciosLibro;


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
        //Platform.runLater(()->listenersCampos());
        stage.show();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> listenersCampos());
        Platform.runLater(()-> comboboxAutor.setItems(ControlBBDD.listaObservable(
                ControlBBDD.buscar(null, null, Autor.class)
        )));
    }

    private void listenersCampos() {
        criteriosBusquedaAutor = new HashMap<>();
        criteriosBusquedaLibro = new HashMap<>();
        camposVaciosAutor = true;
        camposVaciosLibro = true;
        txtNombre.textProperty().addListener((observable, oldValue, newValue) ->
        {agregarCriterioSiNoVacio("nombre", newValue, criteriosBusquedaAutor); camposVaciosAutor=false;});
        txtApellidos.textProperty().addListener((observable, oldValue, newValue) ->
        {agregarCriterioSiNoVacio("apellidos", newValue, criteriosBusquedaAutor); camposVaciosAutor=false;});
        checkActivo.selectedProperty().addListener((observable, oldValue, newValue) ->
        {criteriosBusquedaAutor.put("activo", newValue); camposVaciosAutor=false;});
        txtTitulo.textProperty().addListener((observable, oldValue, newValue) ->
        {agregarCriterioSiNoVacio("nombre", newValue,criteriosBusquedaLibro); camposVaciosLibro=false;});
        txtGenero.textProperty().addListener((observable, oldValue, newValue) ->
        {agregarCriterioSiNoVacio("genero", newValue,criteriosBusquedaLibro); camposVaciosLibro=false;});
        txtSinopsis.textProperty().addListener((observable, oldValue, newValue) ->
        {agregarCriterioSiNoVacio("sinopsis", newValue,criteriosBusquedaLibro); camposVaciosLibro=false;});
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            agregarCriterio("fecha_lanzamiento", newValue,criteriosBusquedaLibro); camposVaciosLibro=false;
        });
        comboboxAutor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            agregarCriterio("autor", newValue, criteriosBusquedaLibro);camposVaciosLibro = false;

        });


    }
    private void agregarCriterio(String campo, Object valor, Map criteriosBusqueda) {
        criteriosBusqueda.put(campo, valor);
    }

    private void agregarCriterioSiNoVacio(String campo, String valor, Map criteriosBusqueda) {
        if (!valor.isEmpty()) {
            criteriosBusqueda.put(campo, valor);
        }
    }

    public void buscarAutor(ActionEvent actionEvent) {
        if (camposVaciosAutor) {
            ObservableList result =FXCollections.observableArrayList(ControlBBDD.buscar("activo", checkActivo.isSelected(), Autor.class));
            listaResultadosAutor.setItems(result);
            return;
        }
        Objects busqueda = ControlBBDD.busquedaCompleja(Autor.class,criteriosBusquedaAutor);
        if (busqueda ==null){
            listaResultadosAutor.getItems().clear();
        }else {
            ObservableList result =FXCollections.observableArrayList();
            listaResultadosAutor.setItems(result);
        }
        //primero checkea cuantos campos hay llenos. si todos estan vacios no deja buscar y muestra label.
        //si solo hay 1 usa busqueda normal
    }

    public void buscarLibro(ActionEvent actionEvent) {
        if (camposVaciosLibro) {
            labelVacioLibro.setVisible(true);
            return;
        }
        Objects busqueda = ControlBBDD.buscar("fecha_lanzamiento", criteriosBusquedaLibro.get("fecha_lanzamiento"), Libro.class);
        //Objects busqueda = ControlBBDD.busquedaCompleja(Libro.class,criteriosBusquedaLibro);
        if (busqueda ==null){
            listaResultadosLibro.getItems().clear();
        }else {
            ObservableList result =FXCollections.observableArrayList();
            listaResultadosLibro.setItems(result);
        }
    }

    /**
     * Cierra la ventana cuando se pulsa el botón adecuado
     * @param actionEvent
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }


}

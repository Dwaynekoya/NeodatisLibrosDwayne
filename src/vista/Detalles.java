package vista;

import controlador.ControlBBDD;
import controlador.ControlMainScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Autor;
import modelo.Libro;
import org.neodatis.odb.Objects;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Detalles extends Application {
    @FXML
    private Tab tabAutor;
    @FXML
    private Tab tabLibro;
    @FXML
    private ListView listaLibros;
    @FXML
    private TabPane tabPane;
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
    @FXML
    private Label labelProhibido;
    private Object selectedItem;
    private ControlMainScreen mainScreen;
    private Detalles detalles;

    @Override
    public void start(Stage stage) throws Exception {
        launchDetalles(stage);
    }

    private void launchDetalles(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("detalles.fxml"));
        } catch (IOException e) {
            System.out.println("Error asociando vista para ver y modificar detalles de un elemento");
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(root));
//        Platform.runLater(()->fillDetalles());
    }
    private void mostrarDetalles(Libro libro) {
        txtTitulo.setText(libro.getNombre());
        txtGenero.setText(libro.getGenero());
        txtSinopsis.setText(libro.getSinopsis());
        datePicker.setValue(libro.getFecha_lanzamiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comboboxAutor.getSelectionModel().select(libro.getAutor());
        Platform.runLater(()-> comboboxAutor.setItems(ControlBBDD.listaObservableAutores()));
    }

    private void mostrarDetalles(Autor autor) {
        txtNombre.setText(autor.getNombre());
        txtApellidos.setText(autor.getApellidos());
        checkActivo.setSelected(autor.isActivo());
        //comparar el autor directamente no funciona, fallo de neodatis?
        Map<String, Object> criterios = new HashMap<>();
        criterios.put("autor.nombre", autor.getNombre());
        criterios.put("autor.apellidos", autor.getApellidos());
        Objects librosAutor = ControlBBDD.busquedaCompleja(Libro.class, criterios);
        if (librosAutor==null) return;
        ObservableList result = FXCollections.observableArrayList(librosAutor);
        listaLibros.setItems(result);
        listaLibros.setOnMouseClicked(mouseEvent -> handleLibroSelection());
    }

    private void handleLibroSelection() {
        Libro selectedLibro = (Libro) listaLibros.getSelectionModel().getSelectedItem();
        if (selectedLibro != null) {
            showDetailsWindow(selectedLibro);
        }
    }

    private void showDetailsWindow(Object selectedItem) {
        if (this.detalles!=null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/detalles.fxml"));
            Scene scene = new Scene(loader.load());

            Detalles detailsController = loader.getController();
            detailsController.setDetails(selectedItem, mainScreen,this);

            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Detalles");
            detailsStage.setScene(scene);
            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDetails(Object selectedItem, ControlMainScreen mainScreen) {
        this.mainScreen=mainScreen;
        this.selectedItem=selectedItem;
        if (selectedItem instanceof Libro) {
            tabPane.getSelectionModel().select(tabLibro);
            tabAutor.setDisable(true);
            mostrarDetalles((Libro) selectedItem);
            addModificationListenersLibro();
        } else if (selectedItem instanceof Autor) {
            tabLibro.setDisable(true);
            mostrarDetalles((Autor) selectedItem);
            addModificationListenersAutor();
        }
    }
    public void setDetails(Object selectedItem, ControlMainScreen mainScreen, Detalles detalles) {
        this.mainScreen=mainScreen;
        this.selectedItem=selectedItem;
        this.detalles = detalles;
        if (selectedItem instanceof Libro) {
            tabPane.getSelectionModel().select(tabLibro);
            tabAutor.setDisable(true);
            mostrarDetalles((Libro) selectedItem);
            addModificationListenersLibro();
        } else if (selectedItem instanceof Autor) {
            tabLibro.setDisable(true);
            mostrarDetalles((Autor) selectedItem);
            addModificationListenersAutor();
        }
    }

    private void addModificationListenersAutor() {
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(((Autor)selectedItem).getNombre())) {
                ((Autor) selectedItem).setNombre(newValue);
            }
        });
        txtApellidos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(((Autor)selectedItem).getApellidos())) {
                ((Autor) selectedItem).setApellidos(newValue);
            }
        });
        checkActivo.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != ((Autor) selectedItem).isActivo()) {
                ((Autor) selectedItem).setActivo(newValue);
            }
        });
    }
    private void addModificationListenersLibro() {
        txtTitulo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(((Libro)selectedItem).getNombre())) {
                ((Libro) selectedItem).setNombre(newValue);
            }
        });

        txtGenero.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(((Libro)selectedItem).getGenero())) {
                ((Libro) selectedItem).setGenero(newValue);
            }
        });

        txtSinopsis.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(((Libro)selectedItem).getSinopsis())) {
                ((Libro) selectedItem).setSinopsis(newValue);
            }
        });

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            Date fechaLanzamiento;
            fechaLanzamiento = java.util.Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (!fechaLanzamiento.equals(((Libro)selectedItem).getFecha_lanzamiento())) {
                ((Libro) selectedItem).setFecha_lanzamiento(fechaLanzamiento);
            }
        });

        comboboxAutor.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(((Libro)selectedItem).getAutor())) {
                ((Libro) selectedItem).setAutor((Autor) newValue);
            }
        });
    }



    /**
     * Cierra la ventana cuando se pulsa el botón adecuado
     * @param actionEvent
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        this.mainScreen.fillLists();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void modificar(ActionEvent actionEvent) {
        labelProhibido.setVisible(false);
        ControlBBDD.modificar(selectedItem);
        cerrarVentana(actionEvent);
    }
    public void eliminar(ActionEvent actionEvent){
        labelProhibido.setVisible(false);
        if (selectedItem instanceof Autor){
            HashMap criterios = new HashMap<>();
            criterios.put("autor.nombre", ((Autor) selectedItem).getNombre());
            criterios.put("autor.apellidos", ((Autor) selectedItem).getApellidos());
            Objects libros = ControlBBDD.busquedaCompleja(Libro.class, criterios);
            if (libros!=null || libros.hasNext()){
                labelProhibido.setVisible(true);
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Quieres eliminar el " + selectedItem.getClass().getSimpleName().toLowerCase()+ "?");

        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        // Show the confirmation dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOK) {
                ControlBBDD.eliminar(selectedItem);
            } else {
                System.out.println("Operación cancelada.");
            }
        });
        cerrarVentana(actionEvent);
    }
}

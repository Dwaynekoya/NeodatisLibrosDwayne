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
import modelo.Autor;
import modelo.Libro;

import java.io.IOException;
import java.sql.Date;
import java.time.ZoneId;
import java.util.Objects;

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
    private Object selectedItem;
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
//        Platform.runLater(()->fillDetalles());
    }

    public void setDetails(Object selectedItem) {
        this.selectedItem=selectedItem;
        if (selectedItem instanceof Libro) {
            tabPane.getSelectionModel().select(tabLibro);
            tabAutor.setDisable(true);
            showDetails((Libro) selectedItem);
            addModificationListenersLibro();
        } else if (selectedItem instanceof Autor) {
            tabLibro.setDisable(true);
            showDetails((Autor) selectedItem);
            addModificationListenersAutor();
        }
    }



    private void showDetails(Libro libro) {
        //System.out.println("Detalles libro");
/*        lblTitle.setText("Title: " + libro.getNombre());
        lblAuthor.setText("Author: " + libro.getAutor().getNombre());
        lblDetails.setText("Other details related to the selected book...");*/
        txtTitulo.setText(libro.getNombre());
        txtGenero.setText(libro.getGenero());
        txtSinopsis.setText(libro.getSinopsis());
        datePicker.setValue(libro.getFecha_lanzamiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        comboboxAutor.getSelectionModel().select(libro.getAutor());

    }

    private void showDetails(Autor autor) {
        //System.out.println("Detalles autor " + autor.getNombre());
        txtNombre.setText(autor.getNombre());
        txtApellidos.setText(autor.getApellidos());
        checkActivo.setSelected(autor.isActivo());

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
            if (!newValue.equals(((Libro)selectedItem).getFecha_lanzamiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                ((Libro) selectedItem).setFecha_lanzamiento(Date.valueOf(newValue));
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
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void modificarLibro(ActionEvent actionEvent) {
    }

    public void modificarAutor(ActionEvent actionEvent) {
    }
}

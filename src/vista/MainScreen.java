package vista;

import controlador.ControlBBDD;
import controlador.ControlDetalles;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Autor;
import modelo.Libro;
import org.neodatis.odb.Objects;

import java.io.IOException;

public class MainScreen extends Application {
    @FXML
    private ListView<Autor> listaAutores;
    @FXML
    private ListView<Libro> listaLibros;
    @FXML
    private Button btnVentanaAdd;
    @FXML
    private Button btnVentanaBuscar;
    private Button btnRefrescar;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Gestión editorial");
        stage.setOnCloseRequest(windowEvent -> cerrarBBDD());
        launchMain();
        stage.show();
    }

    private void llenarListaLibros() {
        Objects<Libro> libros = ControlBBDD.buscar(null, null, Libro.class);
        ObservableList<Libro> dataObservableList = FXCollections.observableArrayList(libros);
        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> listaLibros.setItems(dataObservableList));
    }

    public void llenarListaAutores() {
        Objects<Autor> autores = ControlBBDD.buscar(null, null, Autor.class);
        ObservableList<Autor> dataObservableList = FXCollections.observableArrayList(autores);
        // Update the UI on the JavaFX Application Thread
        Platform.runLater(() -> listaAutores.setItems(dataObservableList));
    }

    private void cerrarBBDD() {
        ControlBBDD.cerrarBBDD();
    }

    private void launchMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/mainscreen.fxml"));
            loader.setController(this);  // Asigna el controlador aquí en lugar de en el fxml. Necesario para listas
            Parent root = loader.load();

            Scene mainScreen = new Scene(root);
            stage.setScene(mainScreen);

            fillLists();
            funcionamientoButtons();
        } catch (IOException e) {
            System.out.println("Error asociando vista principal a la pantalla.");
            throw new RuntimeException(e);
        }
    }

    private void funcionamientoButtons() {
        btnVentanaAdd.setOnAction(actionEvent -> {
           abrirVentana(new Add(listaAutores,listaLibros));
        });
        btnVentanaBuscar.setOnAction(actionEvent -> {
            abrirVentana(new Buscar(listaAutores,listaLibros));
        });
        btnRefrescar.setOnAction(actionEvent -> {
            llenarListaAutores();
            llenarListaLibros();
        });
    }

    private void abrirVentana(Application window) {
        try {
            window.start(new Stage());
        } catch (Exception e) {
            System.out.println("Error creando ventana " + window.getClass());
            throw new RuntimeException(e);
        }
    }

    private void fillLists() {
        llenarListaAutores();
        llenarListaLibros();
        ControlBBDD.visualizarTodoConsola();
        listaAutores.setOnMouseClicked(event -> handleAutorSelection());
        listaLibros.setOnMouseClicked(event -> handleLibroSelection());
    }

    private void handleLibroSelection() {
        Libro selectedLibro = (Libro) listaLibros.getSelectionModel().getSelectedItem();
        if (selectedLibro != null) {
            handleItemSelection(selectedLibro);
        }
    }

    private void handleAutorSelection() {
        Autor selectedAutor = (Autor) listaAutores.getSelectionModel().getSelectedItem();
        if (selectedAutor != null) {
            handleItemSelection(selectedAutor);
        }
    }
    //TODO: check whatever this does

    private void showDetailsWindow(String selectedItemTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/details.fxml"));
            Scene scene = new Scene(loader.load());

            ControlDetalles detailsController = loader.getController();
            detailsController.setDetails(selectedItemTitle);

            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Details");
            detailsStage.setScene(scene);
            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// ...

    // Update the method that handles item selection in the ListView
    private void handleItemSelection(Object seleccion) {
        if (seleccion instanceof Libro){
            showDetailsWindow((Libro) seleccion);
        }
        if (seleccion instanceof Autor){
            showDetailsWindow((Autor) seleccion);
        }
    }

    private void showDetailsWindow(Autor seleccion) {
    }
    private void showDetailsWindow(Libro seleccion) {
    }
}

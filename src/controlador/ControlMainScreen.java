package controlador;

import javafx.application.Application;
import javafx.application.Platform;
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

import java.io.IOException;

public class ControlMainScreen extends Application {
    @FXML
    private ListView<Autor> listaAutores;
    @FXML
    private ListView<Libro> listaLibros;
    @FXML
    private Button btnVentanaAdd;
    @FXML
    private Button btnVentanaBuscar;
    private Stage stage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Gestión editorial");
        stage.setOnCloseRequest(windowEvent -> ControlBBDD.cerrarBBDD());
        launchMain();
        stage.show();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/Add.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            ControlAdd controlAdd = loader.getController();
            controlAdd.setMainScreen(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        btnVentanaBuscar.setOnAction(actionEvent -> {
            abrirVentana(new ControlBuscar());
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

    public void fillLists() {
        Platform.runLater(() -> listaAutores.setItems(ControlBBDD.generarLista(Autor.class)));
        Platform.runLater(() -> listaLibros.setItems(ControlBBDD.generarLista(Libro.class)));
        //ControlBBDD.visualizarTodoConsola();
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
        Autor selectedAutor = listaAutores.getSelectionModel().getSelectedItem();
        if (selectedAutor != null) {
            handleItemSelection(selectedAutor);
        }
    }

    private void showDetailsWindow(Object selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/detalles.fxml"));
            Scene scene = new Scene(loader.load());

            ControlDetalles detailsController = loader.getController();
            detailsController.setDetails(selectedItem, this);

            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Detalles");
            detailsStage.setScene(scene);
            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleItemSelection(Object seleccion) {
        showDetailsWindow(seleccion);
    }

    private void showDetailsWindow(Autor seleccion) {
    }
    private void showDetailsWindow(Libro seleccion) {
    }
}
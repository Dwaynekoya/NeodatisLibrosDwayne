package controlador;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
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

    /**
     * En el método llamado para iniciar la ventana establecemos título, operación de cerrado y llamamos a launchMain()
     *  Mostramos el stage como ultimo paso.
     * @param primaryStage -> Ventana que mostramos
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Gestión editorial");
        stage.setOnCloseRequest(windowEvent -> ControlBBDD.cerrarBBDD());
        launchMain();
        stage.show();
    }

    /**
     * Asociación de xml al controlador, listeners de teclado y control del funcionamiento de listas y botones.
     */
    private void launchMain() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/mainscreen.fxml"));
            loader.setController(this);  // Asigna el controlador aquí en lugar de en el fxml. Necesario para listas
            Parent root = loader.load();
            Scene mainScreen = new Scene(root);
            mainScreen.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    shortcuts(keyEvent);
                }
            });
            stage.setScene(mainScreen);

            fillLists();
            funcionamientoButtons();
        } catch (IOException e) {
            System.out.println("Error asociando vista principal a la pantalla.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Atajos de teclado
     * @param keyEvent -> tecla pulsada
     */
    private void shortcuts(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case F -> abrirVentana(new ControlBuscar());
            case B -> abrirVentana(new ControlBuscar());
            case A -> abrirVentanaAdd();
            case ESCAPE -> stage.close();
        }
    }

    /**
     * Añade el funcionamiento a los botones que abren ventanas.
     */
    private void funcionamientoButtons() {
        btnVentanaAdd.setOnAction(actionEvent -> abrirVentanaAdd());
        btnVentanaBuscar.setOnAction(actionEvent -> {
            abrirVentana(new ControlBuscar());
        });
    }

    /**
     * Lanza una ventana Add. Incluye la asociación de la vista xml a la clase controlador.
     */
    private void abrirVentanaAdd() {
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
    }

    /**
     * Lanza una ventana.
     * @param window-> Ventana a lanzar, siempre es de tipo ControlBuscar
     */
    private void abrirVentana(Application window) {
        try {
            window.start(new Stage());
        } catch (Exception e) {
            System.out.println("Error creando ventana " + window.getClass());
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que llena las listas de la vista con los datos de la BBDD
     */

    public void fillLists() {
        Platform.runLater(() -> listaAutores.setItems(ControlBBDD.generarLista(Autor.class)));
        Platform.runLater(() -> listaLibros.setItems(ControlBBDD.generarLista(Libro.class)));
        //ControlBBDD.visualizarTodoConsola();
        listaAutores.setOnMouseClicked(event -> handleAutorSelection());
        listaLibros.setOnMouseClicked(event -> handleLibroSelection());
    }

    /**
     * Método que toma el libro seleccionado de la lista asociandolo a una variable
     */
    private void handleLibroSelection() {
        Libro selectedLibro = (Libro) listaLibros.getSelectionModel().getSelectedItem();
        if (selectedLibro != null) {
            handleItemSelection(selectedLibro);
        }
    }

    /**
     * Método que toma el autor seleccionado de la lista asociandolo a una variable
     */
    private void handleAutorSelection() {
        Autor selectedAutor = listaAutores.getSelectionModel().getSelectedItem();
        if (selectedAutor != null) {
            handleItemSelection(selectedAutor);
        }
    }
    /**
     * Método para crear y mostrar una ventana de detalles para un objeto seleccionado por el usuario
     * @param seleccion -> Objeto seleccionado por el usuario
     */
    private void handleItemSelection(Object seleccion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/detalles.fxml"));
            Scene scene = new Scene(loader.load());

            ControlDetalles detailsController = loader.getController();
            detailsController.setDetails(seleccion, this);

            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Detalles");
            detailsStage.setScene(scene);
            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

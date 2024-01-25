package vista;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import modelo.Autor;
import modelo.Libro;

import java.io.IOException;
import java.util.Objects;

public class Detalles extends Application {
    @FXML
    private Tab tabAutor;
    @FXML
    private Tab tabLibro;

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
        Platform.runLater(()->fillDetalles());
    }

    private void fillDetalles() {

    }
    public void setDetails(Object selectedItem) {
        if (selectedItem instanceof Libro) {
            tabAutor.setDisable(true);
            showDetails((Libro) selectedItem);
        } else if (selectedItem instanceof Autor) {
            tabLibro.setDisable(true);
            showDetails((Autor) selectedItem);
        }
    }

    private void showDetails(Libro libro) {
        System.out.println("Detalles libro");
/*        lblTitle.setText("Title: " + libro.getNombre());
        lblAuthor.setText("Author: " + libro.getAutor().getNombre());
        lblDetails.setText("Other details related to the selected book...");*/
    }

    private void showDetails(Autor autor) {
        System.out.println("Detalles autor " + autor.getNombre());
        /*lblTitle.setText("Author: " + autor.getNombre());
        lblAuthor.setText("Birth Date: " + autor.get());
        lblDetails.setText("Other details related to the selected author...");*/
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

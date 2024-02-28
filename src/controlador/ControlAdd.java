package controlador;

import javafx.application.Application;
import javafx.application.Platform;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ControlAdd extends Application {
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
    private Label labelRellenarAutor;
    @FXML
    private Label labelRellenarLibro;
    @FXML
    private Label labelDuplicadoAutor;
    @FXML
    private Label labelDuplicadoLibro;
    private ControlMainScreen mainScreen;


    @Override
    public void start(Stage stage) throws Exception {
        launchAdd(stage);
        stage.show();
    }
    /**
     * Crea objeto de tipo Autor si todos los campos están llenos
     * Inserta el objeto en la base de datos usando la clase ControlBBDD
     * @param actionEvent -> evento generado por el botón
     */
    public void addAutor(ActionEvent actionEvent) {
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        if (checkDatosCompletos(new String[]{nombre, apellidos})){
            Autor autor = new Autor(nombre,apellidos, checkActivo.isSelected());
            boolean added = ControlBBDD.addObject(autor);
            //listaAutores.refresh();
            if (!added) {
                labelDuplicadoAutor.setVisible(true);
            }
        }else {
            labelRellenarAutor.setVisible(true);
        }

    }

    /**
     * Comprueba que no haya campos vacíos
     * @param campos-> Strings con los valores de los campos de texto de la vista.
     * @return -> false si hay campos vacíos. True si todos los campos fueron rellenados.
     */
    private boolean checkDatosCompletos(String[] campos) {
        for (String campo: campos){
            if (campo.isEmpty()) return false;
        }
        return true;
    }

    /**
     * Crea objeto de tipo Libro si todos los campos están llenos
     * Inserta el objeto en la base de datos usando la clase ControlBBDD
     * @param actionEvent -> evento generado por el botón
     */
    public void addLibro(ActionEvent actionEvent) {
        String titulo = txtTitulo.getText();
        String genero = txtGenero.getText();
        String sinopsis = txtSinopsis.getText();
        if (checkDatosCompletos(new String[]{titulo,genero,sinopsis})){
            Date  fechaLanzamiento;
            LocalDate localDate = datePicker.getValue();
            Autor selectedAutor = (Autor) comboboxAutor.getSelectionModel().getSelectedItem();
            if (localDate != null && selectedAutor != null) {
                fechaLanzamiento = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Libro libro = new Libro(titulo,genero,fechaLanzamiento,selectedAutor);
                boolean added = ControlBBDD.addObject(new Libro(titulo,genero,fechaLanzamiento,selectedAutor));
                if (!added) {labelDuplicadoLibro.setVisible(true);}
                else {selectedAutor.getLibros().add(libro);}
                mainScreen.fillLists();
            }
        } else {
            //si falta algún campo se muestra label:
            labelRellenarLibro.setVisible(true);
        }


    }
    /**
     * Cierra la ventana cuando se pulsa el botón adecuado
     * @param actionEvent-> evento generado por el botón
     */
    public void cerrarVentana(ActionEvent actionEvent) {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    /**
     * Asigna la vista fxml al Stage creando una escena
     * @param stage -> ventana a la que se le asocia la nueva escena
     */
    private void launchAdd(Stage stage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("add.fxml"));
        } catch (IOException e) {
            System.out.println("Error asociando vista para añadir elementos");
            throw new RuntimeException(e);
        }
        Scene add = new Scene(root);
        stage.setTitle("Añadir elementos");
        stage.setScene(add);
//        fillCombobox();
    }

    /**
     * Llena el combobox de la vista para añadir libros con los autores presentes en la BBDD
     */
    @FXML
    private void fillCombobox() {
        Platform.runLater(()-> comboboxAutor.setItems(ControlBBDD.listaObservableAutores()));
    }

    public void setMainScreen(ControlMainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }
}

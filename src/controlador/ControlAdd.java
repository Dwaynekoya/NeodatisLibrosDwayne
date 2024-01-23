package controlador;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class ControlAdd {
    public void addAutor(ActionEvent actionEvent) {
        //TODO
    }

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
}

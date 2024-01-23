package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControlDetalles {
    @FXML
    private Label titleLabel;

    public void setDetails(String title) {
        titleLabel.setText(title);
        // Add logic to populate other details based on the selected item
    }
}

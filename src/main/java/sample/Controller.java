package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    int i = 0;
    @FXML
    private Label text_control;

    @FXML
    private void change_text() {
        text_control.setText(String.valueOf(i++));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text_control.setText("");
    }
}

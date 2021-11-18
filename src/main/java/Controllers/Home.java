package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    @FXML
    private AnchorPane container;

    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().clear();
            container.getChildren().add(component);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/fxml/Search.fxml");
    }

    public void translateButton(ActionEvent actionEvent) {
        showComponent("/fxml/Translate.fxml");
    }

    public void addButton(ActionEvent actionEvent) {
        showComponent("/fxml/Add.fxml");
    }

    public void bookmarkButton(ActionEvent actionEvent) {
        showComponent("/fxml/Bookmark.fxml");
    }

    public void searchButton(ActionEvent actionEvent) {
        showComponent("/fxml/Search.fxml");
    }
}

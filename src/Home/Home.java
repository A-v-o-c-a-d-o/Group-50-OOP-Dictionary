package Home;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    @FXML
    private AnchorPane container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/Search/Search.fxml");
    }

    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().clear();
            container.getChildren().add(component);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActionSearchBtn() throws Exception {
        showComponent("/Search/Search.fxml");
    }

    public void onActionAddBtn() throws Exception {
        showComponent("/Add/Add.fxml");
    }

    public void onActionTranslateBtn() throws Exception {
        showComponent("/TranslateAPI/Translate.fxml");
    }

    public void onActionBookmarkBtn() throws Exception {
        showComponent("/Bookmark/Bookmark.fxml");
    }
}

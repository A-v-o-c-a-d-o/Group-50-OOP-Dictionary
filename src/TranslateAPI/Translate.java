package TranslateAPI;

import com.darkprograms.speech.translator.GoogleTranslate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

public class Translate implements Initializable {
    @FXML
    private Pane rightFlag;
    @FXML
    private Pane leftFlag;
    @FXML
    private TextArea target;
    @FXML
    private TextArea explain;
    private String langTarget = "vi";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public String ggTranslate(String targetLanguage, String text) {
        String translatedTarget = null;
        try {
            translatedTarget = GoogleTranslate.translate(targetLanguage, text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return translatedTarget;
    }

    public void translate(ActionEvent actionEvent) throws Exception {
        String word = target.getText();
        word = word.replace(". ", ",");
        String meaning = ggTranslate(langTarget, word);
        explain.setText(meaning);
    }

    public void swapEnVi(MouseEvent mouseEvent) throws Exception {
        if (langTarget.compareTo("vi") == 0) {
            rightFlag.setLayoutX(399);
            leftFlag.setLayoutX(60);
            langTarget = "en";
        } else {
            rightFlag.setLayoutX(60);
            leftFlag.setLayoutX(399);
            langTarget = "vi";
        }
        target.clear();
        explain.clear();
    }
}

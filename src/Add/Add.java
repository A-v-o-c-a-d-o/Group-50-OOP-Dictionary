package Add;

import DictionaryCMD.DictionaryManagement;
import DictionaryCMD.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class Add implements Initializable {
    @FXML
    private ImageView canAdd;
    @FXML
    private ImageView cantAdd;
    @FXML
    private TextField addTarget;
    @FXML
    private TextArea addExplain;
    @FXML
    private TextField addPhonetic;

    public void save(ActionEvent actionEvent) throws Exception {
        String target = addTarget.getText().toLowerCase().trim();
        String explain = addExplain.getText().toLowerCase().trim();
        String pronounce = addPhonetic.getText().toLowerCase().trim();
        if (!target.equals("") && !explain.equals("") && !DictionaryManagement.includeDB(target)) {
            Word selectedWord = new Word();
            selectedWord.setWord_target(target);
            selectedWord.setWord_explain(explain);
            selectedWord.setWord_phonetics(pronounce);
            DictionaryManagement.insertToDatabase(selectedWord);
            canAdd.setVisible(true);
            cantAdd.setVisible(false);
        } else {
            cantAdd.setVisible(true);
            canAdd.setVisible(false);
        }
        addTarget.clear();
        addExplain.clear();
        addPhonetic.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canAdd.setVisible(false);
        cantAdd.setVisible(false);
    }
}

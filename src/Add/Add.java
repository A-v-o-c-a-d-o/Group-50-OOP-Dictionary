package Add;

import DictionaryCMD.Word;
import DictionaryCMD.DictionaryManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Add implements Initializable {
    @FXML
    private TextField addTarget;
    @FXML
    private TextArea addExplain;
    @FXML
    private TextField addPhonetic;

    public void save(ActionEvent actionEvent) throws SQLException {
        String target = addTarget.getText().toLowerCase().trim();
        String explain = addExplain.getText().toLowerCase().trim();
        String pronounce = addPhonetic.getText().toLowerCase().trim();
        if (!target.equals("") && !explain.equals("")) {
            Word selectedWord = new Word();
            selectedWord.setWord_target(target);
            selectedWord.setWord_explain(explain);
            selectedWord.setWord_phonetics(pronounce);
            DictionaryManagement.insertToDatabase(selectedWord);
        }
        addTarget.clear();
        addExplain.clear();
        addPhonetic.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

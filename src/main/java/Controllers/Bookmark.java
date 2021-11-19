package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DictionaryCMD.DictionaryManagement;
import DictionaryCMD.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Bookmark {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea definition;

    @FXML
    private Label pronounceWord;

    @FXML
    private ListView<Word> results;

    @FXML
    private TextField searchField;

    private ObservableList<Word> list = FXCollections.observableArrayList();

    @FXML
    void onActionDeleteWord(ActionEvent event) throws IOException, SQLException {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            DictionaryManagement.deleteFromBookmark(selectedWord);
            int selectedIdx = results.getSelectionModel().getSelectedIndex();
            int newSelectedIdx = (selectedIdx == results.getItems().size() - 1) ? selectedIdx - 1 : selectedIdx;
            results.getItems().remove(selectedIdx);
            results.getSelectionModel().select(newSelectedIdx);
            if (newSelectedIdx != -1) {
                selectedWord = results.getSelectionModel().getSelectedItem();
                definition.setText(selectedWord.getWord_explain());
            } else {
                definition.clear();
            }
        }
    }

    @FXML
    void onActionSearchBtn(KeyEvent event) throws SQLException {
        list = DictionaryManagement.searchFromBookmark(searchField.getText().toLowerCase().trim());
        results.setItems(list);
    }

    @FXML
    public void onMouseClickListView(MouseEvent mouseEvent) {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            definition.setText(selectedWord.getWord_explain());
            pronounceWord.setText("    " + selectedWord.getWord_phonetics());
        }
    }

    @FXML
    void onActionSpeakerBtn(ActionEvent event) {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            DictionaryManagement.speak(selectedWord);
        }
    }

    @FXML
    void initialize() {
        assert definition != null : "fx:id=\"definition\" was not injected: check your FXML file 'Bookmark.fxml'.";
        assert pronounceWord != null : "fx:id=\"pronounceWord\" was not injected: check your FXML file 'Bookmark.fxml'.";
        assert results != null : "fx:id=\"results\" was not injected: check your FXML file 'Bookmark.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'Bookmark.fxml'.";

        try {
            list = DictionaryManagement.loadFromBookmark();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        results.setItems(list);
    }

}

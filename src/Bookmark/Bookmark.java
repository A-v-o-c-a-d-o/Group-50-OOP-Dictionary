package Bookmark;

import DictionaryCMD.DictionaryManagement;
import DictionaryCMD.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Bookmark implements Initializable {
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Word> results;
    @FXML
    private TextArea definition;

    private ObservableList<Word> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void onMouseClickListView(MouseEvent mouseEvent) {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            definition.setText(selectedWord.getWord_explain());
        }
    }

    public void deleteMark(ActionEvent actionEvent) throws SQLException, IOException {
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
}
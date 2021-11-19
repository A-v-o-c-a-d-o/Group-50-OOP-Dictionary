package Controllers;

import DictionaryCMD.DictionaryManagement;
import DictionaryCMD.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Search implements Initializable {
    @FXML
    private Label pronounce;
    @FXML
    private ImageView star;
    @FXML
    private AnchorPane editTab;
    @FXML
    private TextArea editTextField;
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
            list = DictionaryManagement.searchFromDatabase("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        results.setItems(list);
        editTab.setVisible(false);
        star.setVisible(false);
    }

    public void onActionSearchButton(KeyEvent keyEvent) throws SQLException {
        list = DictionaryManagement.searchFromDatabase(searchField.getText().toLowerCase().trim());
        results.setItems(list);
    }

    public void onMouseClickListView(MouseEvent mouseEvent) throws SQLException {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            definition.setText(selectedWord.getWord_explain());
        }
        if (DictionaryManagement.includeBM(selectedWord.getWord_target())) star.setVisible(true);
        else star.setVisible(false);
        pronounce.setText(selectedWord.getWord_phonetics());
    }

    public void edit(ActionEvent actionEvent) {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            editTab.setVisible(true);
        }
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            DictionaryManagement.deleteInDatabase(selectedWord);

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

    public void bookmark(ActionEvent actionEvent) throws SQLException, IOException {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            if (DictionaryManagement.includeBM(selectedWord.getWord_target())) {
                DictionaryManagement.deleteFromBookmark(selectedWord);
                star.setVisible(false);
            } else {
                DictionaryManagement.addToBookmark(selectedWord);
                star.setVisible(true);
            }
        }
    }

    public void speaker(ActionEvent actionEvent) {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            DictionaryManagement.speak(selectedWord);
        }
    }

    public void confirm(ActionEvent actionEvent) throws SQLException {
        String descriptionEdited = editTextField.getText().toLowerCase().trim();

        if (!descriptionEdited.equals("")) {
            Word selectedWord = results.getSelectionModel().getSelectedItem();
            selectedWord.setWord_explain(descriptionEdited);
            DictionaryManagement.UpdateDatabase(selectedWord);

            editTab.setVisible(false);
            definition.setText(descriptionEdited);
            editTextField.clear();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        editTab.setVisible(false);
        editTextField.clear();
    }
}
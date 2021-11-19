package Search;

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
        editTab.setVisible(false);
        star.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onActionSearchButton(KeyEvent keyEvent) {
        try {
        list = DictionaryManagement.searchFromDatabase(searchField.getText().toLowerCase().trim());
        results.setItems(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onMouseClickListView(MouseEvent mouseEvent) throws SQLException {
        try {
            Word selectedWord = results.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                definition.setText(selectedWord.getWord_explain());
            }
            if (DictionaryManagement.includeBM(selectedWord.getWord_target())) star.setVisible(true);
            else star.setVisible(false);
            pronounce.setText(selectedWord.getWord_phonetics());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void edit(ActionEvent actionEvent) throws Exception {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            editTab.setVisible(true);
        }
    }

    public void delete(ActionEvent actionEvent) throws Exception {
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

    public void bookmark(ActionEvent actionEvent) throws Exception {
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

    public void speaker(ActionEvent actionEvent) throws Exception {
        Word selectedWord = results.getSelectionModel().getSelectedItem();
        if (selectedWord != null) {
            DictionaryManagement.speak(selectedWord);
        }
    }

    public void confirm(ActionEvent actionEvent) throws Exception {
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

    public void cancel(ActionEvent actionEvent) throws Exception {
        editTab.setVisible(false);
        editTextField.clear();
    }
}

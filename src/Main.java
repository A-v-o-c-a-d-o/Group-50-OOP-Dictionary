import java.sql.SQLException;

import DictionaryCMD.DictionaryManagement;
import DictionaryCMD.Word;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ListView<Word> l = new ListView<>();
        //DictionaryManagement.deleteFromBookmark(new Word("letuananh", "ten toi", "lta"));
        //DictionaryManagement.addToBookmark(new Word("letuananh", "ten toi", "lta"));
        l.getItems().addAll(DictionaryManagement.loadFromBookmark());
        //DictionaryManagement.insertToDatabase(new Word("letuananh", "ten toi", "lta"));
        //l.getItems().addAll(DictionaryManagement.searchFromDatabase("letuan"));
        Group root = new Group(l);
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }
}
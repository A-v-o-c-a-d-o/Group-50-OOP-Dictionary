import java.sql.SQLException;
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
        //DictionaryManagement.UpdateDatabase(new Word("letuananh", "ten tao", "lta"));
        //DictionaryManagement.insertToDatabase(new Word("letuananh", "ten toi", "lta"));
        //DictionaryManagement.deleteInDatabase("letuananh");
        //l.setItems(DictionaryManagement.searchFromDatabase("letuan"));
        l.getItems().addAll(DictionaryManagement.searchFromDatabase("letu"));
        Group root = new Group(l);
        primaryStage.setScene(new Scene(root, 500, 200));
        primaryStage.show();
    }
}
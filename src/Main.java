import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    Button translate = new Button("Translate");
    Button edit = new Button("Edit");
    Button history = new Button("History");
    Button bookmark = new Button("Bookmark");
    HBox menu;
    Scene menuScene, searchScene, editScene, historyScene, bookmarkScene;
    Background background = new Background(new BackgroundImage(new Image("D:/Study/Dictionary/lib/BackGround.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));

    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // menu scene
        setupMenu();
        setupMenuScene();
        primaryStage.setScene(menuScene);

        primaryStage.show();
    }

    private void setupMenu() {
        edit.setPrefWidth(50);

        menu = new HBox();
        menu.getChildren().addAll(translate, edit, history, bookmark);
        menu.setAlignment(Pos.BOTTOM_RIGHT);
        menu.setSpacing(10);
    }

    private void setupMenuScene() {
        Label dictionary = new Label("Dictionary");
        dictionary.setFont(new Font("Comic Sans MS", 100));
        dictionary.setTextFill(Color.BLUE);

        VBox mainPane = new VBox();
        mainPane.setBackground(background);
        mainPane.getChildren().addAll(dictionary, menu);
        mainPane.setAlignment(Pos.BOTTOM_CENTER);
        mainPane.setSpacing(200);

        menuScene = new Scene(mainPane, 960, 600);
    }

    /* private void setupSearchScene() {
        Label label = new Label("Translate");
        label.setFont(new Font("Comic Sans MS", 100));
        label.setTextFill(Color.BLUE);


    } */
}
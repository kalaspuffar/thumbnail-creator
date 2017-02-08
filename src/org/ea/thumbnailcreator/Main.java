package org.ea.thumbnailcreator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ThumbnailCreator.fxml"));
        primaryStage.setTitle("Thumbnail Creator");
        primaryStage.setScene(new Scene(root, 640.0, 569.0));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

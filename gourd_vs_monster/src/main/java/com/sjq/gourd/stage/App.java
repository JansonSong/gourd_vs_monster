package com.sjq.gourd.stage;

import com.sjq.gourd.constant.Constant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("/SceneController.fxml"));
            Parent root = fxmlLoader.load();
            Image cursorImage = new Image("/images/mouse_icon.png");
            ImageCursor Mouse = new ImageCursor(cursorImage, cursorImage.getWidth() / 6, 0);
            Scene scene = new Scene(root, Constant.STAGE_WIDTH, Constant.STAGE_HEIGHT);
            scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
            scene.setCursor(Mouse);
            primaryStage.setTitle("Gourd VS Monster");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/gourd_icon.png")));
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}


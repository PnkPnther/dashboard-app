package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

//"EXTENDS APPLICATION" EXTENDS APPLICATION CLASS TO GIVE JAVAFX FUNCTIONALITY
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //STAGE IS THE APP "WINDOW", CONTENT WITHIN THE "WINDOW" IS THE SCENE
        //FXML LOADER CALLS CONTROLLER CLASS INITIALIZE() METHOD AND PARSES FXML FILE AND GRAPHS SCENE
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //ADDS CSS STYLESHEET TO PROJECT
        String css = Objects.requireNonNull(this.getClass().getResource("stylesheet.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        //RESTRICTS RESIZING OF WINDOW BY USER
        stage.resizableProperty().setValue(false);
        //REMOVES DEFAULT TITLE BAR
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static void main(String[] args) {
        //LAUNCH IS A METHOD IN APPLICATION CLASS. WHEN MAIN METHOD RUNS, IT LAUNCHES THE JAVAFX APPLICATION AND CALLS
        //START METHOD
        launch(args);
    }
}
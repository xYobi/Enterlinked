package com.haris.enterlinked;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class EnterLinkedApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(EnterLinkedApp.class.getResource("/com/haris/enterlinked/login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("EnterLinked");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();


    }


    public static void main(String[] args) {
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
        launch(args);
    }
}
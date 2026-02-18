package com.haris.enterlinked.service;

import com.haris.enterlinked.controller.ContentPageController;
import com.haris.enterlinked.controller.HomePageController;
import com.haris.enterlinked.model.Content;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root = null;
            try{
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));

            } catch (IOException e) {
                e.printStackTrace();
            }

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }
    public static void changeScene(Node source, String fxmlFile, String title){
        Parent root = null;
            try{
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));

            } catch (IOException e) {
                e.printStackTrace();
            }

        Stage stage = (Stage) source.getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }
    public static void changeContent(Node source, String fxmlFile, String title, Content content){
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            ContentPageController controller = loader.getController();
            controller.setContent(content);
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    public static void changeContent(Node source, String fxmlFile, String title, Content content,String previousFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            ContentPageController controller = loader.getController();
            controller.setContent(content);
            controller.setPreviousFXML(previousFXML);
            Stage stage = (Stage) source.getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

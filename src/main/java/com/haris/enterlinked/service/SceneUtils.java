package com.haris.enterlinked.service;

import com.haris.enterlinked.controller.HomePageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username){
        Parent root = null;
        if(username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root=loader.load();
                HomePageController homePageController = loader.getController();
                homePageController.setUserInformation(username);

            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }
    public static void changeScene(Node source, String fxmlFile, String title, String username){
        Parent root = null;
        if(username != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root=loader.load();
                HomePageController homePageController = loader.getController();
                homePageController.setUserInformation(username);

            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root=FXMLLoader.load(DBUtils.class.getResource(fxmlFile));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

}

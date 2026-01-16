package com.haris.enterlinked.controller;

import com.haris.enterlinked.service.DBUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private Button bt_logout;

    @FXML
    private Label label_welcome;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bt_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked logout");
                DBUtils.changeScene(event, "/com/haris/enterlinked/login-view.fxml","EnterLinked", null);
            }
        });

    }
    public void setUserInformation(String username){
        label_welcome.setText("Welcome "+username+"!");

    }

}
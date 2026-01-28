package com.haris.enterlinked.controller;

import com.haris.enterlinked.service.ContentService;
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
    private ContentService contentService = new ContentService();

    @FXML
    private Button bt_discovery;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
    public void setUserInformation(String username){


    }

}
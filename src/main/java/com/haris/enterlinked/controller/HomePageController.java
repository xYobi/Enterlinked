package com.haris.enterlinked.controller;

import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.service.DBUtils;
import com.haris.enterlinked.service.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnMouseClicked(e -> {
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked",null);
        });

    }
    public void setUserInformation (String username){

    }
}



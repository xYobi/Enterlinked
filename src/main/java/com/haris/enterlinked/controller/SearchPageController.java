package com.haris.enterlinked.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    @FXML
    private VBox filterPane;
    @FXML private Button bt_filter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterPane.setVisible(false);
        bt_filter.setOnAction(event-> toggleFilters());


    }
    @FXML
    private void toggleFilters(){
        filterPane.setVisible(!filterPane.isVisible());
    //    filterPane.setVisible(true);
    }


}


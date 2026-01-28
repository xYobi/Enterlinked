package com.haris.enterlinked.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    @FXML
    private VBox filterPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterPane.setVisible(false);
        filterPane.setManaged(false);

    }
    @FXML
    private void toggleFilters(){
        boolean show = !filterPane.isVisible();
        filterPane.setVisible(show);
    }


}


package com.haris.enterlinked.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LibraryPageController implements Initializable {

    @FXML
    private ComboBox <String> cb_Category;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cb_Category.getItems().addAll(
                "All","Movies",
                "Games",
                "Books"
        );
        cb_Category.getSelectionModel().selectFirst();



    }
}

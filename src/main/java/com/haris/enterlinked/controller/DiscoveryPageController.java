package com.haris.enterlinked.controller;

import com.haris.enterlinked.service.DBUtils;
import com.haris.enterlinked.service.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscoveryPageController implements Initializable {
    @FXML
    private ComboBox <String> categoryCombo;

    @FXML
    private ComboBox<String>typeCombo;

    @FXML private ComboBox<String>genreCombo;

    @FXML private Button bt_logout;
    @FXML private TextField searchField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnMouseClicked(e -> {
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked",null);
        });
        categoryCombo.getItems().addAll(
                "Movies",
               "Games",
                "Books",
                "All"

        );
        categoryCombo.getSelectionModel().selectFirst();
        typeCombo.getItems().addAll(
                "New",
                "Trending",
                "Popular",
                "Top Rated",
                "Recommended"

        );
        typeCombo.getSelectionModel().selectFirst();
        genreCombo.getItems().addAll(
                "Action",
                "Adventure",
                "Comedy",
                "Drama",
                "Fantasy",
                "Horror",
                "Mystery",
                "Sci-Fi",
                "Thriller",
                "RPG"

        );
        genreCombo.getSelectionModel().selectFirst();


    }
}


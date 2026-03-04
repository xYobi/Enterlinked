package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.navigation.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.haris.enterlinked.service.ContentService;

public class SearchPageController implements Initializable {

    @FXML private TextField searchField;
    @FXML private VBox resultsBox;
    private ContentService contentService = new ContentService();
    private int Cards_per_row = 8;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        searchField.setOnAction(event -> Search());
    }

    private void Search() {

        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter a title to search");
            alert.show();
            return;
        }


        resultsBox.getChildren().clear();
        System.out.println("Searching for: " + query);
        List<Content> results = contentService.searchByTitle(query);

        if (results.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No results found");
            alert.show();
            return;
        }
        HBox row = null;
        for (int i = 0; i < results.size(); i++) {
            if (i % Cards_per_row == 0) {
                row = new HBox(20);
                resultsBox.getChildren().add(row);
            }
            Content movie = results.get(i);
            VBox card = ContentCardFactory.create(movie);
            card.setOnMouseClicked(event -> SceneUtils.changeContent(card,
                    "/com/haris/enterlinked/view-content-page.fxml",
                    "EnterLinked", movie,
                    "/com/haris/enterlinked/search-page-view.fxml"));

            row.getChildren().add(card);

            }

        }
    }




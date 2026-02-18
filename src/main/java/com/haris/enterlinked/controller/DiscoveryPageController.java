package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.service.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoveryPageController implements Initializable {
    @FXML private ComboBox<String> categoryCombo;
    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<String> genreCombo;
    @FXML private Button bt_logout;
    @FXML private TextField searchField;
    private ContentService contentService = new ContentService();
    @FXML private VBox vb_results;
    private int Cards_per_row = 4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnMouseClicked(e -> {
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked");
        });
        categoryCombo.getItems().addAll(
                "All Mediums", "Games", "Movies", "Books");
        categoryCombo.getSelectionModel().selectFirst();

        typeCombo.getItems().addAll("Top Rated", "New", "Popular", "Recommended");
        typeCombo.getSelectionModel().selectFirst();

        genreCombo.getItems().addAll("All Genres", "Action", "Adventure", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Sci-Fi", "Thriller", "RPG");
        genreCombo.getSelectionModel().selectFirst();

        typeCombo.setOnAction(event -> loadMovies());
        loadMovies();
    }

    private void loadMovies() {

        vb_results.getChildren().clear();
        List<Content> movies = contentService.getMovies(typeCombo.getValue(),50);
        HBox row = null;
        for (int i = 0; i< movies.size(); i++){
            if(i % Cards_per_row ==0){
                row = new HBox(20);
                vb_results.getChildren().add(row);
            }
            Content movie = movies.get(i);
            VBox card = ContentCardFactory.create(movie);
            row.getChildren().add(card);
        }



    }
}


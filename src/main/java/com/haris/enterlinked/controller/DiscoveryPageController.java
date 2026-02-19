package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.service.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoveryPageController implements Initializable {
    @FXML private ComboBox<String> categoryCombo;
    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<String> genreCombo;
    @FXML private TextField searchField;
    @FXML private Label lb_title;
    @FXML private Label lb_description;
    @FXML private Label lb_rating;
    @FXML private Label lb_length;
    @FXML private ImageView i_poster;
    @FXML private VBox vb_results;
    @FXML private Button bt_more;

    private Content selectedMovie;
    private ContentService contentService = new ContentService();
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
        genreCombo.setOnAction(event -> loadMovies());

        loadMovies();
    }

    private void loadMovies() {

        vb_results.getChildren().clear();
        List<Content> movies = contentService.getMovies(typeCombo.getValue(), genreCombo.getValue(),50);
        HBox row = null;
        for (int i = 0; i< movies.size(); i++){
            if(i % Cards_per_row ==0){
                row = new HBox(20);
                vb_results.getChildren().add(row);
            }
            Content movie = movies.get(i);
            VBox card = ContentCardFactory.create(movie);
            card.setOnMouseClicked(event -> {
                selectedMovie = movie;
                lb_title.setText(movie.getTitle());
                lb_description.setText(movie.getDescription());
                lb_description.setWrapText(true);
                lb_length.setText(String.valueOf(movie.getLength())+ " minutes");
                lb_rating.setText(String.valueOf(movie.getRating())+"/10");
                i_poster.setImage(new Image(movie.getImageUrl(),true));

            });
            bt_more.setOnAction(event -> SceneUtils.changeContent(bt_more,
                    "/com/haris/enterlinked/view-content-page.fxml",
                    "EnterLinked", selectedMovie,
                    "/com/haris/enterlinked/discovery-page-view.fxml"));

            row.getChildren().add(card);
        }



    }
}


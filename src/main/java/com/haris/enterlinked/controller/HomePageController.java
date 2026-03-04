package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;

import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.navigation.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML private Label label_welcome;
    private ContentService contentService = new ContentService();
    @FXML private Button bt_discovery;
    @FXML private TextField searchField;
    @FXML private HBox highlyRated;
    @FXML private HBox hb_new;
    @FXML private HBox hb_popular;
    @FXML private HBox hb_recommended;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnMouseClicked(e -> {
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked");
        });

        populateRow(highlyRated,"Top Rated",7);
        populateRow(hb_new,"New",7);
        populateRow(hb_popular,"Popular",7);
        populateRow(hb_recommended,"Recommended",7);
    }

    private void populateRow(HBox row,String sortType, int Limit){
        row.getChildren().clear();
        List<Content> movies = contentService.getMovies(sortType,Limit);
        for(Content movie: movies){
            VBox card = ContentCardFactory.create(movie);
            card.setOnMouseClicked(event -> SceneUtils.changeContent(card, "/com/haris/enterlinked/view-content-page.fxml", "EnterLinked", movie,"/com/haris/enterlinked/home-page-view.fxml"));
            row.getChildren().add(card);
        }
    }
}



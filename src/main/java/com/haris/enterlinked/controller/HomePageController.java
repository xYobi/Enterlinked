package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;

import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.service.SceneUtils;
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
    @FXML
    private Button bt_logout;

    @FXML
    private Label label_welcome;
    private ContentService contentService = new ContentService();

    @FXML
    private Button bt_discovery;

    @FXML
    private TextField searchField;

    @FXML
    private HBox moviesRow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnMouseClicked(e -> {
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked");
        });
        loadMovies();


    }
    public void setUserInformation (String username){
    }

    private void loadMovies(){
        moviesRow.getChildren().clear();
        List<Content> movies = contentService.getMovies("Top Rated",7);

        for(Content movie: movies){
            VBox card = ContentCardFactory.create(movie);
            card.setOnMouseClicked(event -> SceneUtils.changeContent(card, "/com/haris/enterlinked/view-content-page.fxml", "EnterLinked", movie,"/com/haris/enterlinked/home-page-view.fxml"));
            moviesRow.getChildren().add(card);
        }
    }


}



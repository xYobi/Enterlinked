package com.haris.enterlinked.controller;

import com.haris.enterlinked.model.Content;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked",null);
        });
        loadMovies();


    }
    public void setUserInformation (String username){
    }
    private VBox createMovieCard(Content movie){
        ImageView poster = new ImageView();
        poster.setFitHeight(240);
        poster.setFitWidth(166);
        poster.setPreserveRatio(true);

        if(movie.getImageUrl() !=null){
            poster.setImage((new Image(movie.getImageUrl(), true)));
        }else{
            poster.setImage(new Image(getClass().getResourceAsStream("/com/haris/enterlinked/images.png")));
        }
        Label title = new Label(movie.getTitle());
        title.setWrapText(true);
        title.setMaxWidth(166);

        VBox card = new VBox(8,poster,title);
        return card;
    }
    private void loadMovies(){
        moviesRow.getChildren().clear();
        List<Content> movies = contentService.getPopularMovies(8);

        for(Content movie: movies){
            moviesRow.getChildren().add(createMovieCard(movie));
        }
    }


}



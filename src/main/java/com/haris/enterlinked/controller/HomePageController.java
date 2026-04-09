package com.haris.enterlinked.controller;
import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.navigation.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class HomePageController implements Initializable {
    private ContentService contentService = new ContentService();
    @FXML private TextField searchField;
    @FXML private HBox highlyRated;
    @FXML private HBox hb_new;
    @FXML private HBox hb_popular;
    @FXML private HBox hb_recommended;
    @FXML private Button bt_games;
    @FXML private Button bt_movies;
    @FXML private Button bt_books;
    private String selectedContenttype;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnMouseClicked(e -> {
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked");
        });
        bt_movies.setOnAction(event -> {selectedContenttype ="movie" ;refreshHomepage();});
        bt_games.setOnAction(event -> {selectedContenttype ="game";refreshHomepage();});
        bt_books.setOnAction(event -> {selectedContenttype ="book";refreshHomepage();});

        populateRow(highlyRated,"Top Rated",7,"game");
        populateRow(hb_new,"New",7,"game" );
        populateRow(hb_popular,"Popular",7,"game" );
        populateRow(hb_recommended,"Recommended",7,"game");
    }
    private void refreshHomepage(){
        populateRow(highlyRated,"Top Rated",7,selectedContenttype);
        populateRow(hb_new,"New",7,selectedContenttype);
        populateRow(hb_popular,"Popular",7,selectedContenttype);
        populateRow(hb_recommended,"Recommended",7,selectedContenttype);
    }

    private void populateRow(HBox row,String sortType, int Limit, String contentType){
        row.getChildren().clear();
        List<Content> movies = contentService.getByContentType(contentType,sortType,Limit);
        for(Content movie: movies){
            VBox card = ContentCardFactory.create(movie);
            card.setOnMouseClicked(event -> SceneUtils.changeContent(card, "/com/haris/enterlinked/view-content-page.fxml", "EnterLinked", movie,"/com/haris/enterlinked/home-page-view.fxml"));
            row.getChildren().add(card);
        }
    }
}



package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.navigation.SceneUtils;
import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.service.RecommendationService;
import com.haris.enterlinked.service.SavedContentService;
import com.haris.enterlinked.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ContentPageController implements Initializable {
    @FXML private Label lb_title;
    @FXML private Label lb_description;
    @FXML private Label lb_rating;
    @FXML private Label lb_year;
    @FXML private Label lb_length;
    @FXML private ImageView i_poster;
    private String previousFXML;
    @FXML Button bt_back;
    @FXML Button bt_add;
    @FXML HBox hb_similartop;
    @FXML HBox hb_similarbot;
    @FXML Button bt_games;
    @FXML Button bt_movies;
    @FXML Button bt_books;
    @FXML HBox hb_genre;
    private Content currentContent;
    private RecommendationService recommendationService= new RecommendationService();
    private SavedContentService save = new SavedContentService();
    private int movie_id;

    public void setPreviousFXML(String previousFXML) {
        this.previousFXML = previousFXML;
    }

    public void setContent(Content c){
        currentContent = c;
        lb_title.setText(c.getTitle());
        if(c.getDescription() != null) {
            lb_description.setText(c.getDescription());
        }
        lb_rating.setText(String.valueOf(c.getRating())+"/10");
        i_poster.setImage(new Image(c.getImageUrl(),true));
        lb_year.setText(String.valueOf(c.getRelease_year()));
        if(c.getMedium().equals("movie")) {
            lb_length.setText(String.valueOf(c.getLength()) + " Minutes");
        }
        else if (c.getMedium().equals("game")) {
            lb_length.setText("N/A");
        }
        if (c.getMedium().equals("book")){
            lb_length.setText(String.valueOf(c.getLength()+ " Pages"));
        }
        movie_id = c.getId();
        lb_description.setWrapText(true);
        lb_description.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        loadSimilarContent(c,c.getMedium());
        loadGenres(c);
    }
    private void loadSimilarContent(Content selected, String content_type){
        hb_similartop.getChildren().clear();
        hb_similarbot.getChildren().clear();

        List<Content> similarList = recommendationService.getSimilarContent(selected,5,content_type);
        for (int i = 0; i<similarList.size();i++){
            Content similar = similarList.get(i);
            VBox card = ContentCardFactory.create(similar);
            card.setOnMouseClicked(e->{SceneUtils.changeContent(card,"/com/haris/enterlinked/view-content-page.fxml","EnterLinked",similar,"/com/haris/enterlinked/home-page-view.fxml");});
            if(i<3){
                hb_similartop.getChildren().add(card);
            }
            else {
                hb_similarbot.getChildren().add(card);
            }
        }
    }
    private void loadGenres(Content content){
        hb_genre.getChildren().clear();
        if(content.getGenres() ==null){
            Button genreButton = new Button("No Genres");
            hb_genre.getChildren().add(genreButton);
            return;
        }
        String [] genres = content.getGenres().split(",");
        for (String g : genres){
            String genre = g.trim();
            Button genreButton = new Button(genre);
            hb_genre.getChildren().add(genreButton);
            genreButton.setOnAction(e->{SceneUtils.changeSceneWithGenre(hb_genre,"/com/haris/enterlinked/discovery-page-view.fxml","EnterLinked",genre);});
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bt_movies.setOnAction(e-> loadSimilarContent(currentContent,"movie"));
        bt_games.setOnAction(e-> loadSimilarContent(currentContent,"game"));
        bt_books.setOnAction(e->loadSimilarContent(currentContent,"book"));

        bt_back.setOnAction(e ->
                SceneUtils.changeScene(e, previousFXML, "EnterLinked")
        );
        bt_add.setOnAction(e ->{
            int userId = UserService.getCurrentUser().getUser_id();
            int movieId = movie_id;
            if(!save.checkSave(userId, movieId)){
                save.saveContent(userId,movieId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Content Saved");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Content Already Saved In Library");
                alert.show();
            }
        });

    }
}

package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.ContentService;
import com.haris.enterlinked.navigation.SceneUtils;
import com.haris.enterlinked.service.SavedContentService;
import com.haris.enterlinked.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    @FXML Button bt_add;

    private Content selectedContent;
    private ContentService contentService = new ContentService();
    private int Cards_per_row = 4;
    private SavedContentService save = new SavedContentService();


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

        genreCombo.getItems().addAll("All Genres", "Action", "Adventure","Animation", "Comedy", "Drama", "Family","Fantasy", "Horror", "Mystery", "Science Fiction", "Thriller", "RPG","Romance","War","Western");
        genreCombo.getSelectionModel().selectFirst();

        typeCombo.setOnAction(event -> loadMovies());
        genreCombo.setOnAction(event -> loadMovies());
        categoryCombo.setOnAction(event -> loadMovies());

        loadMovies();

        bt_add.setOnAction(e ->{
                int userId = UserService.getCurrentUser().getUser_id();
                int movieId = selectedContent.getId();
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

    private void loadMovies() {
        String sortType = typeCombo.getValue();
        String genre = genreCombo.getValue();
        String selectedCategory;
        switch (categoryCombo.getValue()) {
            case "Games":
                selectedCategory = "game";
                break;
            case "Movies":
                selectedCategory = "movie";
                break;
            case "Books":
                selectedCategory = "book";
                break;
            default:
                selectedCategory ="All Mediums";
                break;

        }
        vb_results.getChildren().clear();
        List<Content> contentList = contentService.getContentbyGenre(sortType,genre,selectedCategory,100);
        HBox row = null;
        for (int i = 0; i< contentList.size(); i++){
            if(i % Cards_per_row ==0){
                row = new HBox(20);
                vb_results.getChildren().add(row);
            }
            Content content = contentList.get(i);
            VBox card = ContentCardFactory.create(content);
            card.setOnMouseClicked(event -> {
                selectedContent = content;
                lb_title.setText(content.getTitle());
                lb_description.setText(content.getDescription());
                lb_description.setWrapText(true);
                if(selectedCategory.equals("movie")){
                    lb_length.setText(String.valueOf(content.getLength())+ " minutes");
                } else if (selectedCategory.equals("book")) {
                    lb_length.setText(String.valueOf(content.getLength())+ " pages");
                } else{
                    lb_length.setText("N/A");
                }
                lb_rating.setText(String.valueOf(content.getRating())+"/10");
                i_poster.setImage(new Image(content.getImageUrl(),true));

            });
            bt_more.setOnAction(event -> SceneUtils.changeContent(bt_more,
                    "/com/haris/enterlinked/view-content-page.fxml",
                    "EnterLinked", selectedContent,
                    "/com/haris/enterlinked/discovery-page-view.fxml"));

            row.getChildren().add(card);
        }
    }
}


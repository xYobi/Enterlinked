package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.navigation.SceneUtils;
import com.haris.enterlinked.service.SavedContentService;
import com.haris.enterlinked.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.naming.ContextNotEmptyException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LibraryPageController implements Initializable {

    @FXML
    private ComboBox<String> cb_Category;
    @FXML
    private TextField searchField;
    @FXML
    private VBox libraryContainer;
    private int Cards_per_row = 8;
    private SavedContentService savedContent = new SavedContentService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchField.setOnMouseClicked(e -> {
            SceneUtils.changeScene(searchField, "/com/haris/enterlinked/search-page-view.fxml", "Enterlinked");
        });
        cb_Category.getItems().addAll(
                "All", "Movies",
                "Games",
                "Books"
        );
        cb_Category.getSelectionModel().selectFirst();
        loadMovies();
    }

    private void loadMovies() {
        libraryContainer.getChildren().clear();
        int userId = UserService.getCurrentUser().getUser_id();
        List<Content> savedMovies = savedContent.getSavedMovies(userId);
        HBox row = null;
        for (int i = 0; i < savedMovies.size(); i++) {
            if (i % Cards_per_row == 0) {
                row = new HBox(20);
                libraryContainer.getChildren().add(row);
            }
            Content movie = savedMovies.get(i);
            VBox card = ContentCardFactory.create(movie);
            card.setOnMouseClicked(event -> SceneUtils.changeContent(card, "/com/haris/enterlinked/view-content-page.fxml", "EnterLinked", movie,"/com/haris/enterlinked/library-page-view.fxml"));
            row.getChildren().add(card);
        }
    }
}


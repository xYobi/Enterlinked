package com.haris.enterlinked.controller;

import com.haris.enterlinked.components.ContentCardFactory;
import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.navigation.SceneUtils;
import com.haris.enterlinked.service.SavedContentService;
import com.haris.enterlinked.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LibraryPageController implements Initializable {

    @FXML private ComboBox<String> cb_Category;
    @FXML private TextField searchField;
    @FXML private VBox libraryContainer;
    @FXML private Button bt_recently;
    @FXML private Button bt_alpha;
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
        loadContent(savedContent.getSavedContent(UserService.getCurrentUser().getUser_id()));
        cb_Category.setOnAction(e -> loadByCategory());

        bt_alpha.setOnAction(e->{
            int userId = UserService.getCurrentUser().getUser_id();
            loadContent(savedContent.getAlphabetical(userId));
        });
        bt_recently.setOnAction(event -> {
            int userId = UserService.getCurrentUser().getUser_id();
            loadContent(savedContent.getRecent(userId));
        });
    }
    private void loadByCategory() {
        int userId = UserService.getCurrentUser().getUser_id();

        switch (cb_Category.getValue()) {
            case "Movies":
                loadContent(savedContent.getSavedByType(userId, "movie"));
                break;
            case "Games":
                loadContent(savedContent.getSavedByType(userId, "game"));
                break;
            case "Books":
                loadContent(savedContent.getSavedByType(userId, "book"));
                break;
            default:
                loadContent(savedContent.getSavedContent(userId));
                break;
        }
    }
    private void loadContent(List<Content> savedContentList) {
        libraryContainer.getChildren().clear();
        HBox row = null;
        for (int i = 0; i < savedContentList.size(); i++) {
            if (i % Cards_per_row == 0) {
                row = new HBox(20);
                libraryContainer.getChildren().add(row);
            }
            Content content = savedContentList.get(i);
            StackPane card = ContentCardFactory.createLibraryCard(content);
            card.setOnMouseClicked(event -> SceneUtils.changeContent(card, "/com/haris/enterlinked/view-content-page.fxml", "EnterLinked", content,"/com/haris/enterlinked/library-page-view.fxml"));
            Button removeBtn = (Button) card.lookup(".remove-button");
            removeBtn.setOnAction(event -> {
                event.consume();
                int userId = UserService.getCurrentUser().getUser_id();
                boolean removed = savedContent.removeSavedContent(userId, content.getId());
                if (removed) {
                    loadByCategory();
                }});
            removeBtn.setOnMouseClicked(e -> e.consume());
            row.getChildren().add(card);
        }
    }
}


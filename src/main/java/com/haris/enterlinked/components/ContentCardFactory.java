package com.haris.enterlinked.components;

import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.SavedContentService;
import com.haris.enterlinked.service.UserService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ContentCardFactory {
    private ContentCardFactory(){};

    public static VBox create(Content content){
        ImageView poster = new ImageView();
        poster.setFitHeight(240);
        poster.setFitWidth(166);
        poster.setPreserveRatio(true);

        if(content.getImageUrl() !=null){
            poster.setImage((new Image(content.getImageUrl(), true)));
       }

        Label title = new Label(content.getTitle());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        title.setWrapText(true);
        title.setPrefWidth(166);
        title.setWrapText(true);
        title.setTextOverrun(OverrunStyle.ELLIPSIS);
        VBox card = new VBox(8,poster,title);
        card.getStyleClass().add("movie-card");
        return card;
    }

    public static StackPane createLibraryCard(Content content){
        VBox card = create(content);

        Button removeBtn = new Button("×");
        removeBtn.getStyleClass().add("remove-button");
        removeBtn.setVisible(false);
        removeBtn.setManaged(false);
        StackPane wrapper = new StackPane(card, removeBtn);
        wrapper.getStyleClass().add("content-card-wrapper");
        StackPane.setAlignment(removeBtn, Pos.TOP_RIGHT);
        wrapper.setOnMouseEntered(e -> {
            removeBtn.setVisible(true);
            removeBtn.setManaged(true);
        });
        wrapper.setOnMouseExited(e -> {
            removeBtn.setVisible(false);
            removeBtn.setManaged(false);
        });
        return wrapper;
    }
    


}

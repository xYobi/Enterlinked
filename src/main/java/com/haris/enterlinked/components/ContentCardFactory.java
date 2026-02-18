package com.haris.enterlinked.components;

import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.service.SceneUtils;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ContentCardFactory {
    private ContentCardFactory(){};

    public static VBox create(Content movie){
        ImageView poster = new ImageView();
        poster.setFitHeight(240);
        poster.setFitWidth(166);
        poster.setPreserveRatio(true);

        if(movie.getImageUrl() !=null){
            poster.setImage((new Image(movie.getImageUrl(), true)));
        }

        Label title = new Label(movie.getTitle());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        title.setWrapText(true);
        title.setPrefWidth(166);
        title.setWrapText(true);
        title.setTextOverrun(OverrunStyle.ELLIPSIS);
        VBox card = new VBox(8,poster,title);
        card.getStyleClass().add("movie-card");
        return card;
    }
}

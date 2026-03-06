package com.haris.enterlinked.controller;

import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.navigation.SceneUtils;
import com.haris.enterlinked.service.SavedContentService;
import com.haris.enterlinked.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
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
    private Content selectedMovie;
    private SavedContentService save = new SavedContentService();
    private int movie_id;
    public String getPreviousFXML() {
        return previousFXML;
    }

    public void setPreviousFXML(String previousFXML) {
        this.previousFXML = previousFXML;
    }




    public void setContent(Content c){
        lb_title.setText(c.getTitle());
        lb_description.setText(c.getDescription());
        lb_rating.setText(String.valueOf(c.getRating())+"/10");
       // lb_year.setText(String.valueOf(c.get));
        i_poster.setImage(new Image(c.getImageUrl(),true));
        lb_year.setText(String.valueOf(c.getRelease_year()));
        lb_length.setText(String.valueOf(c.getLength())+" Minutes");
        movie_id = c.getId();
        lb_description.setWrapText(true);      // allow multi-line
        lb_description.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bt_back.setOnAction(e ->
                SceneUtils.changeScene(e, previousFXML, "EnterLinked")
        );
        bt_add.setOnAction(e ->{
            int userId = UserService.getCurrentUser().getUser_id();
            int movieId = movie_id;
            if(!save.checkSave(userId, movieId)){
                save.saveMovie(userId,movieId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Movie Saved");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Movie Already Saved In Library");
                alert.show();
            }
        });

    }
}

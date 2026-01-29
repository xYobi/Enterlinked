package com.haris.enterlinked.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {
    @FXML
    private VBox filterPane;
    @FXML private Button bt_filter;
    @FXML private RadioButton rb_movies, rb_games, rb_books, rb_all;
    @FXML private RadioButton rb_1hr, rb_12hr, rb_23hr, rb_3hr;
    @FXML private RadioButton rb_relevance, rb_rating, rb_family;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterPane.setVisible(false);
        bt_filter.setOnAction(event-> toggleFilters());

        ToggleGroup typeGroup = new ToggleGroup();
        rb_movies.setToggleGroup(typeGroup);
        rb_games.setToggleGroup(typeGroup);
        rb_books.setToggleGroup(typeGroup);
        rb_all.setToggleGroup(typeGroup);
        rb_all.setSelected(true);

        ToggleGroup durationGroup = new ToggleGroup();
        rb_1hr.setToggleGroup(durationGroup);
        rb_12hr.setToggleGroup(durationGroup);
        rb_23hr.setToggleGroup(durationGroup);
        rb_3hr.setToggleGroup(durationGroup);

        ToggleGroup sortGroup = new ToggleGroup();
        rb_relevance.setToggleGroup(sortGroup);
        rb_rating.setToggleGroup(sortGroup);
        rb_family.setToggleGroup(sortGroup);
        rb_relevance.setSelected(true);
    }
    @FXML
    private void toggleFilters(){
        filterPane.setVisible(!filterPane.isVisible());
    //    filterPane.setVisible(true);
    }


}


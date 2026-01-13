package com.haris.enterlinked;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label EnterLinked;

    @FXML
    protected void handleStart() {
        EnterLinked.setText("Welcome to EnterLinked");
    }
}
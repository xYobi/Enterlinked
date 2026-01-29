package com.haris.enterlinked.controller;

import com.haris.enterlinked.service.DBUtils;
import com.haris.enterlinked.service.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML private Button bt_home;
    @FXML private Button bt_discovery;
    @FXML private Button bt_library;
    @FXML private Button bt_logout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bt_home.setOnAction(e ->
                SceneUtils.changeScene(e, "/com/haris/enterlinked/home-page-view.fxml", "EnterLinked", null)
        );

        bt_discovery.setOnAction(e ->
                SceneUtils.changeScene(e, "/com/haris/enterlinked/discovery-page-view.fxml", "EnterLinked", null)
        );

        bt_library.setOnAction(e ->
                SceneUtils.changeScene(e, "/com/haris/enterlinked/library-page-view.fxml", "EnterLinked", null)
        );

        bt_logout.setOnAction(e ->
                SceneUtils.changeScene(e, "/com/haris/enterlinked/login-view.fxml", "EnterLinked", null)
        );
    }
}

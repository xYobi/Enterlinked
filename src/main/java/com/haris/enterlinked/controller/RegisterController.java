package com.haris.enterlinked.controller;

import com.haris.enterlinked.service.DBUtils;
import com.haris.enterlinked.service.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;







public class RegisterController implements Initializable {

    @FXML
    private Button bt_register;
    @FXML
    private Button bt_backtologin;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bt_register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_username.getText().trim().isEmpty()&& !tf_password.getText().trim().isEmpty()){
                    DBUtils.registerUser(event,tf_username.getText(), tf_password.getText());
                }else{
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information");
                    alert.show();
                }
            }
        });
        bt_backtologin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneUtils.changeScene(event, "/com/haris/enterlinked/login-view.fxml","EnterLinked" );
            }
        });
    }
}

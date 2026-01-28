package com.haris.enterlinked.service;

import com.haris.enterlinked.controller.HomePageController;
import com.haris.enterlinked.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBUtils {

    public static void registerUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/enterlinked","root","password");
            System.out.println(connection);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username =?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                System.out.println("User Already Exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert   .setContentText("This username is currently in use");
                alert.show();
            }
            else {
                psInsert = connection.prepareStatement("INSERT INTO users (username,password) VALUES (?, ?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.executeUpdate();


                SceneUtils.changeScene(event,"/com/haris/enterlinked/home-page-view.fxml","EnterLinked",username);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ((psCheckUserExists != null)){
                try{
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if ((psInsert !=null)){
                try{
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection !=null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event,String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/enterlinked","root","password");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided Credentials are incorrect");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    if(retrievedPassword.equals(password)){
                        SceneUtils.changeScene(event,"/com/haris/enterlinked/home-page-view.fxml","EnterLinked",username);
                    }else {
                        System.out.println("Provided Credentials are incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided Credentials are incorrect");
                        alert.show();
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement !=null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                }
            }

            if (connection !=null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}

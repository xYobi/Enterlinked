package com.haris.enterlinked.service;

import com.haris.enterlinked.model.User;
import com.haris.enterlinked.navigation.SceneUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void registerUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DBUtils.getConnection();
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
                SceneUtils.changeScene(event,"/com/haris/enterlinked/home-page-view.fxml","EnterLinked");

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
        ResultSet rs = null;
        try{
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement("SELECT user_id ,username, password FROM users WHERE username = ?");
            preparedStatement.setString(1,username);
            rs = preparedStatement.executeQuery();

            if(!rs.isBeforeFirst()){
                System.out.println("User not found in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided Credentials are incorrect");
                alert.show();
            }else {
                while (rs.next()){
                    String retrievedPassword = rs.getString("password");

                    if(retrievedPassword.equals(password)){
                        SceneUtils.changeScene(event,"/com/haris/enterlinked/home-page-view.fxml","EnterLinked");
                        User u = new User();
                        u.setUser_id(rs.getInt("user_id"));
                        u.setUsername(rs.getString("username"));
                        currentUser = u;

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
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement !=null){
                try {
                    rs.close();
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

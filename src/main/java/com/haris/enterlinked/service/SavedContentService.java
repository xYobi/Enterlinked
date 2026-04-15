package com.haris.enterlinked.service;

import com.haris.enterlinked.model.Content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavedContentService {
    String baseSQL = "SELECT c.* FROM Content c JOIN saved_content sc ON c.id = sc.content_id WHERE sc.username_id = ?";

    public void saveContent(int userid, int contentid){
        String sql = "INSERT INTO saved_content(username_id,content_id) VALUES (?,?)";

        try(Connection con = DBUtils.getConnection(); PreparedStatement ps =con.prepareStatement(sql)) {
            ps.setInt(1,userid);
            ps.setInt(2,contentid);
            ps.executeUpdate();
            System.out.println("Saved Movie with userid:" + userid + " contentid = " + contentid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Content> getSavedByType(int userId, String type) {
        List<Content> content = new ArrayList<>();
        ContentService contentService = new ContentService();
        String sql = baseSQL+" AND c.content_type = ?";
        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, type);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    content.add(contentService.setContent(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }return content;
    }

        public boolean checkSave(int userid, int movieid){
        String sql = "SELECT 1 FROM saved_content where username_id = ? and content_id = ? LIMIT 1";
        try(Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,userid);
            ps.setInt(2,movieid);
            ResultSet rs =ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Content> getSavedContent(int userId){
        List<Content> content = new ArrayList<>();
        ContentService contentService = new ContentService();
        try(Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(baseSQL)){
            ps.setInt(1,userId);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    content.add(contentService.setContent(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public List<Content> getAlphabetical(int userId){
        List<Content> movies = new ArrayList<>();
        ContentService contentService = new ContentService();
        String sql = baseSQL + " ORDER BY c.title ASC";

        try(Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,userId);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    movies.add(contentService.setContent(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public List<Content> getRecent(int userID){
        List<Content> movies = new ArrayList<>();
        ContentService contentService = new ContentService();
        String sql= baseSQL + " ORDER BY saved_at DESC";
        try(Connection con =DBUtils.getConnection(); PreparedStatement ps =con.prepareStatement(sql)){
            ps.setInt(1,userID);

            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    movies.add(contentService.setContent(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }




}

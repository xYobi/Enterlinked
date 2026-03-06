package com.haris.enterlinked.service;

import com.haris.enterlinked.model.Content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SavedContentService {

    public void saveMovie(int userid, int movieid){
        String sql = "INSERT INTO saved_content(username_id,movie_id) VALUES (?,?)";

        try(Connection con = DBUtils.getConnection(); PreparedStatement ps =con.prepareStatement(sql)) {
            ps.setInt(1,userid);
            ps.setInt(2,movieid);
            ps.executeUpdate();
            System.out.println("Saved Movie with userid:" + userid + " movieid = " + movieid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkSave(int userid, int movieid){
        String sql = "SELECT 1 FROM saved_content where username_id = ? and movie_id = ? LIMIT 1";
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

    public List<Content> getSavedMovies(int userId){
        List<Content> movies = new ArrayList<>();
        ContentService contentService = new ContentService();
        String sql = """
                SELECT movies.title,movies.summary,movies.poster_path,movies.rating,movies.popularity,movies.vote_count,movies.release_year,movies.length_minutes,movies.id
                FROM movies
                JOIN saved_content ON movies.id = saved_content.movie_id
                WHERE saved_content.username_id = ?""";

        try(Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,userId);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    movies.add(contentService.setMovies(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }




}

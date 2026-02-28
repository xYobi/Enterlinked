package com.haris.enterlinked.service;
import com.haris.enterlinked.model.Content;
import eu.hansolo.tilesfx.addons.Switch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContentService {
    private String queryStart = "Select title,summary,poster_path,rating,popularity,vote_count,release_year,length_minutes FROM movies WHERE vote_count >= 1000 ";

    private Content setMovies(ResultSet rs) throws Exception{
        Content c = new Content();
        c.setTitle(rs.getString("title"));
        c.setDescription(rs.getString("summary"));
        c.setRating(rs.getDouble("rating"));
        c.setMedium("Movie");
        c.setPopularity(rs.getDouble("popularity"));
        c.setVote_count(rs.getInt("vote_count"));
        c.setRelease_year(rs.getInt("release_year"));
        c.setLength(rs.getInt("length_minutes"));
        // System.out.println(sql);
        String posterURL = rs.getString("poster_path");
        if(posterURL != null){
            c.setImageUrl("https://image.tmdb.org/t/p/w500"+ posterURL);
         //   System.out.println(c.getImageUrl());
        }
        else {
           String poster =  TMDBService.getPosterURL(c.getTitle());
           c.setImageUrl("https://image.tmdb.org/t/p/w500"+ poster);
            savePosterPath(c.getTitle(),poster);
         //   System.out.println(poster);
        //   System.out.println(c.getImageUrl()+"THIS IS THE ELSE ");
        }
        return c;
    }

    private String sort(String sortType){
        switch(sortType) {
            case "New":
                return " release_year DESC";

            case "Popular":
                return "popularity DESC";

            case  "Top Rated":
                return  "rating DESC";

            case "Recommended":
                return "rating DESC";

            default:
                return   "release_year DESC";
        }
    }

    public void savePosterPath(String title, String posterPath){
        String sql= "UPDATE movies SET poster_path = ? WHERE title =? LIMIT 1";
        try(Connection con = DBUtils.getConnection();PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1,posterPath);
            ps.setString(2,title);
            ps.executeUpdate();
          //  System.out.println("SAVED");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public List<Content> getMovies(String sortType, int limit){
        List<Content> movies = new ArrayList<>();
        String orderBy=sort(sortType);

        String sql = (queryStart +" ORDER BY " + orderBy +" LIMIT ?");

        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,limit);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    movies.add(setMovies(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }return movies;
    }


    public List<Content> searchByTitle(String query){

        String sql = "SELECT * FROM movies WHERE title LIKE ? AND vote_count >= 20 LIMIT 50";
        List<Content> content = new ArrayList<>();
        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1 , "%" +query + "%");
          //  System.out.println(ps);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    content.add(setMovies(rs));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return content;

    }

    public List<Content> getMovies(String sortType, String genre,int limit){
        List<Content> movies = new ArrayList<>();
        String orderBy=sort(sortType);

        StringBuilder sql = new StringBuilder(queryStart);
        if(!genre.equals("All Genres")){
            sql.append(" AND genres LIKE ? ");
        }
        sql.append(" ORDER BY ").append(orderBy).append(" LIMIT ?");
      //  System.out.println(sql);
        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql.toString())){


            if(!genre.equals("All Genres")){
                ps.setString(1,"%" + genre + "%");
                ps.setInt(2,limit);
            }
            else{
                ps.setInt(1,limit);
            }
           // System.out.println(sql);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    movies.add(setMovies(rs));

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

}

package com.haris.enterlinked.service;
import com.haris.enterlinked.model.Content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContentService {
    private String queryStart = "Select title,summary,poster_path,rating,popularity,vote_count,release_year,length_minutes FROM movies WHERE vote_count >= 1000 ";

    public List<Content> getMovies(String sortType, int limit){
        List<Content> movies = new ArrayList<>();
        String orderBy=null;
        switch(sortType) {
            case "New":
                orderBy = " release_year DESC";
                break;
            case "Popular":
                orderBy ="popularity DESC";
                break;
            case  "Top Rated":
                orderBy ="rating DESC";
                break;
            case "Recommended":
                orderBy="rating DESC";
                break;
            default:
                orderBy = "release_year DESC";
                break;
        }
        String sql = (queryStart +" ORDER BY " + orderBy +" LIMIT ?");

        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,limit);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    Content c = new Content();
                    c.setTitle(rs.getString("title"));
                    c.setDescription(rs.getString("summary"));
                    c.setRating(rs.getDouble("rating"));
                    c.setMedium("Movie");
                    c.setPopularity(rs.getDouble("popularity"));
                    c.setVote_count(rs.getInt("vote_count"));
                    c.setRelease_year(rs.getInt("release_year"));
                    c.setLength(rs.getInt("length_minutes"));
                    System.out.println(sql);
                    String posterURL = TMDBService.getPosterURL(c.getTitle());
                    if(posterURL != null){
                        c.setImageUrl(posterURL);
                    }
                    else {
                        c.setImageUrl(ContentService.class.getResource("/com/haris/enterlinked/images.png").toExternalForm());
                    }
                    movies.add(c);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    return movies;}




    public List<Content> getMovies(String sortType, String genre,int limit){
        List<Content> movies = new ArrayList<>();
        String orderBy=null;
        switch(sortType) {
            case "New":
                orderBy = " release_year DESC";
                break;
            case "Popular":
                orderBy ="popularity DESC";
                break;
            case  "Top Rated":
                orderBy ="rating DESC";
                break;
            case "Recommended":
                orderBy="rating DESC";
                break;
            default:
                orderBy = "release_year DESC";
                break;
        }
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
                    Content c = new Content();
                    c.setTitle(rs.getString("title"));
                    c.setDescription(rs.getString("summary"));
                    c.setRating(rs.getDouble("rating"));
                    c.setMedium("Movie");
                    c.setPopularity(rs.getDouble("popularity"));
                    c.setVote_count(rs.getInt("vote_count"));
                    c.setRelease_year(rs.getInt("release_year"));
                    c.setLength(rs.getInt("length_minutes"));
                    String posterURL = TMDBService.getPosterURL(c.getTitle());
                    if(posterURL != null){
                        c.setImageUrl(posterURL);
                    }
                    else {
                        c.setImageUrl(ContentService.class.getResource("/com/haris/enterlinked/images.png").toExternalForm());
                    }
                    movies.add(c);

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

}

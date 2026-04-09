package com.haris.enterlinked.service;

import com.haris.enterlinked.model.Content;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContentService {
    private String queryStart = "Select title,summary,poster_path,rating,popularity,vote_count,release_year,length_minutes,id,content_type FROM Content WHERE vote_count >= 1000 ";


    public  Content setContent(ResultSet rs){
        Content c = new Content();
        try {
            c.setTitle(rs.getString("title"));
            c.setDescription(rs.getString("summary"));
            c.setRating(rs.getDouble("rating"));
            c.setPopularity(rs.getDouble("popularity"));
            c.setVote_count(rs.getInt("vote_count"));
            c.setRelease_year(rs.getInt("release_year"));
            c.setLength(rs.getInt("length_minutes"));
            c.setId(rs.getInt("id"));
            c.setMedium(rs.getString("content_type"));
            // System.out.println(sql);

            String posterURL = rs.getString("poster_path");
            if (posterURL != null) {
                if (rs.getString("content_type").equals("game")) {
                    c.setImageUrl(posterURL);
                } else if (rs.getString("content_type").equals("book")) {
                    c.setImageUrl(posterURL);
                } else {
                    c.setImageUrl("https://image.tmdb.org/t/p/w154" + posterURL);
                }
                //   System.out.println(c.getImageUrl());
            } else {
                c.setImageUrl(getClass().getResource("/com/haris/enterlinked/No_Poster_Found.png").toExternalForm());
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public List<Content> getContentbyGenre(String sortType, String genre, String Content,int limit){
        List<Content> ContentList = new ArrayList<>();
        String orderBy=sort(sortType);

        StringBuilder sql = new StringBuilder(queryStart);
        if (!Content.equals("All Mediums")){
            sql.append(" AND content_type = ? ");
        }
        if(!genre.equals("All Genres")){
            sql.append(" AND genres LIKE ? ");
        }
        sql.append(" ORDER BY ").append(orderBy).append(" LIMIT ?");
      //  System.out.println(sql);
        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql.toString())){
            int i = 1;

            if (!Content.equals("All Mediums")){
                ps.setString(i++,Content);
            }
            if(!genre.equals("All Genres")){
                ps.setString(i++,"%" + genre + "%");
            }
                ps.setInt(i,limit);
           // System.out.println(sql);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    ContentList.add(setContent(rs));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ContentList;
    }

    public List<Content> getByContentType(String contentType,String sortType, int limit){
        List<Content> content = new ArrayList<>();
        String orderBy=sort(sortType);
        String sql;
        if(sortType == null){
             sql = (queryStart +" AND content_type = ? LIMIT ?");
        }
        else {
             sql = (queryStart +" AND content_type = ? ORDER BY " + orderBy +" LIMIT ?");
        }

        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,contentType);
            ps.setInt(2,limit);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    content.add(setContent(rs));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }return content;
    }

    public List<Content> searchByTitle(String query){

        String sql = "SELECT * FROM Content WHERE title LIKE ? AND vote_count >= 20 LIMIT 100";
        List<Content> content = new ArrayList<>();
        try (Connection con = DBUtils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1 , "%" +query + "%");
            //  System.out.println(ps);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    content.add(setContent(rs));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}

package com.haris.enterlinked.service;
import com.haris.enterlinked.model.Content;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContentService {
    public List<Content> getPopularMovies(int limit){
        List<Content> movies = new ArrayList<>();
        String sql = (""" 
                Select title,summary,poster_path,rating,popularity,vote_count FROM movies WHERE vote_count >= 10000 ORDER BY rating DESC LIMIT ?""");

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

                    String posterPath = rs.getString("poster_path");
                    if(posterPath != null && !posterPath.isBlank()){
                        c.setImageUrl(("https://image.tmdb.org/t/p/w500" +posterPath));
                        System.out.println(posterPath);
                    }
                    else {
                        c.setImageUrl("/com/haris/enterlinked/images.png");
                    }
                    movies.add(c);

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    return movies;}

}

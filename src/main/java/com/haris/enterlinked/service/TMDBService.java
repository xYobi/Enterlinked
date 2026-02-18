package com.haris.enterlinked.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TMDBService {

    private static final String Api_key = "4ffd18af95e3aaef1f2c8325fd6d9c75";

    public static String getPosterURL(String title){
        try{
            String q = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String url = "https://api.themoviedb.org/3/search/movie?api_key=" + Api_key + "&query=" + q;

            HttpResponse<String> res = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(URI.create(url)).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            JSONArray results = new JSONObject(res.body()).optJSONArray("results");
            if (results == null || results.isEmpty()) return null;

            String path = results.getJSONObject(0).optString("poster_path", null);
            return (path == null || path.isBlank()) ? null : "https://image.tmdb.org/t/p/w500" + path;

        } catch (Exception e) {
            e.printStackTrace();
        }

   return null;}

}

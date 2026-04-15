package com.haris.enterlinked.service;

import com.haris.enterlinked.model.Content;
import com.haris.enterlinked.model.ScoredContent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecommendationService {
    private SavedContentService scs = new SavedContentService();
    private ContentService cs = new ContentService();


    private boolean isStopWord(String word){
        return word.equals("the") || word.equals("and") || word.equals("a") || word.equals("an") || word.equals("of") || word.equals("in") || word.equals("on") || word.equals("at") || word.equals("to") || word.equals("for") || word.equals("with") || word.equals("by") || word.equals("from") || word.equals("up") || word.equals("about") || word.equals("into") || word.equals("over") || word.equals("after") || word.equals("before") || word.equals("between") || word.equals("out") || word.equals("against") || word.equals("during") || word.equals("without") || word.equals("within") || word.equals("along") || word.equals("across") || word.equals("through") || word.equals("is") || word.equals("are") || word.equals("was") || word.equals("were") || word.equals("be") || word.equals("been") || word.equals("being") || word.equals("have") || word.equals("has") || word.equals("had") || word.equals("do") || word.equals("does") || word.equals("did") || word.equals("will") || word.equals("would") || word.equals("shall") || word.equals("should") || word.equals("may") || word.equals("might") || word.equals("must") || word.equals("can") || word.equals("could") || word.equals("as") || word.equals("it") || word.equals("its") || word.equals("he") || word.equals("she") || word.equals("they") || word.equals("them") || word.equals("his") || word.equals("her") || word.equals("their") || word.equals("this") || word.equals("that") || word.equals("these") || word.equals("those") || word.equals("there") || word.equals("here") || word.equals("when") || word.equals("where") || word.equals("why") || word.equals("how");
    }


    private Set<String> getWordSet(String text){
        Set<String> wordSet = new HashSet<>();

        if(text == null){
            return  wordSet;
        }
        String cleanedText = text.toLowerCase().replaceAll("[^a-z0-9]"," ");
        String[] splitWords = cleanedText.split("\\s+");

        for(String word : splitWords){
            if(word.length() > 2 && !isStopWord(word)){
                wordSet.add(word);
            }
        }return wordSet;
    }

    private Set<String> getGenreSet(String genres){
        Set<String> genreSet = new HashSet<>();
        if(genres == null){
            return genreSet;
        }
        String[] splitGenres = genres.split(",");
        for(String genre:splitGenres){
            String cleanGenre = genre.trim().toLowerCase();
            if(!cleanGenre.isEmpty()){
                genreSet.add(cleanGenre);
            }
        }return genreSet;
    }

    private int genreScore(Content a, Content b){
        Set<String> aGenres = getGenreSet(a.getGenres());
        Set<String> bGenres = getGenreSet(b.getGenres());

        aGenres.retainAll(bGenres);
        return aGenres.size() *3;
    }
    private int titleScore(Content a, Content b){
        Set<String> aWords = getWordSet(a.getTitle());
        Set<String> bWords = getWordSet(b.getTitle());

        aWords.retainAll(bWords);
        return aWords.size() *2;
    }
    private int descriptionScore(Content a, Content b){
        Set<String> aWords = getWordSet(a.getDescription());
        Set<String> bWords = getWordSet(b.getDescription());

        aWords.retainAll(bWords);
        return Math.min(aWords.size(), 5);
    }

    public List<Content> getRecommendedContent(int userId, int limit, String medium){
        List<Content> savedContent = scs.getSavedContent(userId);
        List<Content> candidates = cs.getRecommendationCandidates(userId,500,medium);
        List<ScoredContent> scoredList = new ArrayList<>();

        for(Content candidate : candidates){
            int score = 0;
            for(Content saved: savedContent){
                score += genreScore(candidate,saved);
                score += titleScore(candidate,saved);
                score += descriptionScore(candidate,saved);
            }
            scoredList.add(new ScoredContent(candidate,score));

        }
        scoredList.sort((a,b) -> Integer.compare(b.getScore(),a.getScore()));
        List<Content> recommendations = new ArrayList<>();
        for(int i = 0; i<Math.min(limit,scoredList.size());i++){
            recommendations.add(scoredList.get(i).getContent());
        }
        return recommendations;

    }

    public List<Content> getRecommendedContent(int userId, int limit, String medium, String genre){
        List<Content> savedContent = scs.getSavedContent(userId);
        List<Content> candidates = cs.getRecommendationCandidates(userId, 500, medium, genre);
        List<ScoredContent> scoredList = new ArrayList<>();

        for(Content candidate : candidates){
            int score = 0;

            for(Content saved : savedContent){
                score += genreScore(candidate, saved);
                score += titleScore(candidate, saved);
                score += descriptionScore(candidate, saved);
            }
            scoredList.add(new ScoredContent(candidate, score));

        }

        scoredList.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        List<Content> recommendations = new ArrayList<>();
        for(int i = 0; i < Math.min(limit, scoredList.size()); i++){
            recommendations.add(scoredList.get(i).getContent());
        }

        return recommendations;
    }
    public List<Content> getSimilarContent(Content selected, int limit, String content_type){
        List<Content> candidates = cs.getSimilarCandidates(content_type,60000);
        List<ScoredContent>scoredList = new ArrayList<>();

        for(Content candidate: candidates){
            if(candidate.getId() == selected.getId()){
                continue;
            }
            int score = 0;
            score += genreScore(candidate,selected);
            score+= titleScore(candidate,selected);
            score+= descriptionScore(candidate,selected);
            scoredList.add(new ScoredContent(candidate,score));

        }
        scoredList.sort((a,b) -> Integer.compare(b.getScore(),a.getScore()));
        List<Content> similar = new ArrayList<>();
        for(int i =0; i< Math.min(limit,scoredList.size()); i++){
            similar.add(scoredList.get(i).getContent());
        }return similar;
    }
}

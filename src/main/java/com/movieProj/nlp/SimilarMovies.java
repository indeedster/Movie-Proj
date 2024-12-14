package com.movieProj.nlp;

import java.util.HashMap;
import java.util.ArrayList;
import com.movieProj.movie.Movie;
import com.movieProj.database.Database;
import org.bson.BsonValue;

public class SimilarMovies {

    private TFIDF tfidf;
    private Database movieDatabase;
    private Processor processor;

    //takes in processor info, and tfidf info with database and collection
    public SimilarMovies(Processor processor, TFIDF tfidf) {
        this.processor = processor;
        this.tfidf = tfidf;
        movieDatabase = new Database("movieProj", "movieInfo");
    }

    //creates an arraylist of movies with the overview and recommendation number (user inputted)
    public ArrayList<Movie> recommendMovies(String movieOverview, int numRecommendations) {
        String[] words = processor.processText(movieOverview);
        HashMap<BsonValue, Float> movieScores = new HashMap<>();
        ArrayList<Movie> recommendedMovies = new ArrayList<>();

        //calculates a score for each movie based on the level of similarity
        for (BsonValue id : tfidf.getIds()) {
            float score = tfidf.calculateTFIDF(id, words);
            movieScores.put(id, score);
        }
//sorts the list by relevence of recomendation
        movieScores.entrySet().stream()
                .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
                .limit(numRecommendations)
                .forEach(e -> recommendedMovies.add(new Movie(movieDatabase.getDocumentByID(e.getKey()))));

        return recommendedMovies;
    }

    //similar to above but recomends based on genre
    public ArrayList<Movie> recommendMoviesByGenre(ArrayList<Movie> filteredMovies, int numRecommendations) {
        ArrayList<Movie> recommendedMovies = new ArrayList<>();
        
        for (Movie movie : filteredMovies) {
            String[] words = processor.processText(movie.getPlot());  
            HashMap<BsonValue, Float> movieScores = new HashMap<>();
            
            for (BsonValue id : tfidf.getIds()) {
                float score = tfidf.calculateTFIDF(id, words);
                System.out.println("Movie ID: " + id + " Score: " + score);  
                movieScores.put(id, score);
            }
    
            movieScores.entrySet().stream()
                .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
                .limit(numRecommendations)
                .forEach(e -> {
                    System.out.println("Movie ID: " + e.getKey() + " Score: " + e.getValue());  
                    recommendedMovies.add(new Movie(movieDatabase.getDocumentByID(e.getKey())));
                });
        }
        return recommendedMovies;
    }
    
    

}
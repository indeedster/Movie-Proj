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

    public SimilarMovies(Processor processor, TFIDF tfidf) {
        this.processor = processor;
        this.tfidf = tfidf;
        movieDatabase = new Database("movieProj", "movieInfo");
    }

    public ArrayList<Movie> recommendMovies(String movieOverview, int numRecommendations) {
        String[] words = processor.processText(movieOverview);
        HashMap<BsonValue, Float> movieScores = new HashMap<>();
        ArrayList<Movie> recommendedMovies = new ArrayList<>();

        for (BsonValue id : tfidf.getIds()) {
            float score = tfidf.calculateTFIDF(id, words);
            movieScores.put(id, score);
        }

        movieScores.entrySet().stream()
                .sorted((e1, e2) -> Float.compare(e2.getValue(), e1.getValue()))
                .limit(numRecommendations)
                .forEach(e -> recommendedMovies.add(new Movie(movieDatabase.getDocumentByID(e.getKey()))));

        return recommendedMovies;
    }

}
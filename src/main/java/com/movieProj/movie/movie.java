package main.java.com.movieProj.database;

import org.bson.Document;

public class Movie {

    private String name;
    private String overview;
    private Float rating;
    private String genre;
    private Integer releaseYear;

    public Movie(String title, String plot, String genre, Integer releaseYear) {
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

   
    public Document getDocument() {
        Document document = new Document();
        document.append("name", name);
        document.append("overview", overview);
        document.append("rating", rating);
        document.append("genre", genre);
        document.append("releaseYear", releaseYear);
        return document;
    }

    
    public static Movie fromDocument(Document document) {
        String name = document.getString("name");
        String overview = document.getString("overview");
        Float rating = document.getDouble("rating").floatValue();
        String genre = document.getString("genre");
        Integer releaseYear = document.getInteger("releaseYear");

        return new Movie(name, overview, rating, genre, releaseYear);
    }

    @Override
    public String toString() {
        return "Movie{" +
               "name='" + name + '\'' +
               ", overview='" + overview + '\'' +
               ", rating=" + rating +
               ", genre='" + genre + '\'' +
               ", releaseYear=" + releaseYear +
               '}';
    }
}

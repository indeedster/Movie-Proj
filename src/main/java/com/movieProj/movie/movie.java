package com.movieProj.movie;

import org.bson.Document;

public class Movie {
    

    private String title;
    private String plot;
    private String genre;
    private Integer releaseYear;

    public Movie(String title, String plot, Float rating, String genre, Integer releaseYear) {
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    // Getters
    public String gettitle() {
        return title;
    }

    public String getplot() {
        return plot;
    }


    public String getGenre() {
        return genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

   
    public Document getDocument() {
        Document document = new Document();
        document.append("title", title);
        document.append("plot", plot);
        document.append("genre", genre);
        document.append("releaseYear", releaseYear);
        return document;
    }

    
    public static Movie fromDocument(Document document) {
        String title = document.getString("title");
        String plot = document.getString("plot");
        String genre = document.getString("genre");
        Integer releaseYear = document.getInteger("releaseYear");

        return new Movie(title, plot, genre, releaseYear);
    }

    @Override
    public String toString() {
        return "Movie{" +
               "title='" + title + '\'' +
               ", plot='" + plot + '\'' +
               ", genre='" + genre + '\'' +
               ", releaseYear=" + releaseYear +
               '}';
    }
}

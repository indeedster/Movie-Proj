package com.movieProj.movie;

import org.bson.Document;

public class Movie {

    private String title;
    private String plot;
    private String genre;
    private Integer releaseYear;

    public Movie(String title, String plot, String genre, Integer releaseYear) {
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    public Movie(Document document) {
        this.title = document.getString("title");
        this.plot = document.getString("plot");
        this.genre = document.getString("genre");
        this.releaseYear = document.getInteger("releaseYear");
    }
    // Getters
    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

   //gets whole movie entry
    public Document getDocument() {
        Document document = new Document();
        document.append("title", title);
        document.append("plot", plot);
        document.append("genre", genre);
        document.append("releaseYear", releaseYear);
        return document;
    }

    //creates new movie
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
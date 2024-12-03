package com.movieProj.movie;

import org.bson.Document;

public class Movie {

    private String name;
    private String overview;
    private String genre;
    private Integer releaseYear;

    public Movie(String name, String overview, String genre, Integer releaseYear) {
        this.name = name;
        this.overview = overview;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }


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
        document.append("genre", genre);
        document.append("releaseYear", releaseYear);
        return document;
    }

    
    public static Movie fromDocument(Document document) {
        String name = document.getString("name");
        String overview = document.getString("overview");
        String genre = document.getString("genre");
        Integer releaseYear = document.getInteger("releaseYear");

        return new Movie(name, overview, genre, releaseYear);
    }

    @Override
    public String toString() {
        return "Movie{" +
               "name='" + name + '\'' +
               ", overview='" + overview + '\'' +
               ", genre='" + genre + '\'' +
               ", releaseYear=" + releaseYear +
               '}';
    }
}

package com.movieProj.database;

import java.util.ArrayList;
import java.util.List;

import org.bson.BsonValue;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.movieProj.movie.Movie;

public class Database {

    private String connectionString, databaseName, collectionName;

    public Database(String dbName, String collectionName) {
        this.connectionString = "mongodb+srv://margaretkastelein:frogEggs.12@cos225.3zv2x.mongodb.net/?retryWrites=true&w=majority&appName=COS225";
        this.databaseName = dbName;
        this.collectionName = collectionName;
    }

    public Database(String connectionString, String dbName, String collectionName) {
        this.connectionString = connectionString;
        this.databaseName = dbName;
        this.collectionName = collectionName;

    }
   

    public List<Movie> findMoviesByGenre(String genre) {
        List<Movie> similarMovies = new ArrayList<>();
        
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase movieDb = mongoClient.getDatabase(databaseName);
            MongoCollection<Document> movieCollection = movieDb.getCollection(collectionName);

            for (Document doc : movieCollection.find()) {
                Movie movie = Movie.fromDocument(doc);
                if (movie.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                    similarMovies.add(movie);
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding movies by genre: " + e.getMessage());
        }

        return similarMovies;
    }


    public void addToDatabase(Document document) {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> movieCollection = movieDatabase.getCollection(this.collectionName);
            movieCollection.insertOne(document);
        } catch (Exception e) {
            System.err.println("Error adding to database: " + e.getMessage());
        }
    }

    public void createCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            movieDatabase.createCollection(this.collectionName);
        } catch (Exception e) {
            System.err.println("Error creating collection: " + e.getMessage());
        }
    }

    public void deleteCollection() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            movieDatabase.getCollection(this.collectionName).drop();
        } catch (Exception e) {
            System.err.println("Error deleting collection: " + e.getMessage());
        }
    }

    public void deleteAllDocuments() {
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> movieCollection = movieDatabase.getCollection(this.collectionName);
            movieCollection.deleteMany(new Document());
        } catch (Exception e) {
            System.err.println("Error deleting documents: " + e.getMessage());
        }
    }

    public Document getDocumentByID(BsonValue id) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase genericDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> genericCollection = genericDatabase.getCollection(this.collectionName);

            return genericCollection.find(new Document("_id", id)).first();

        }
    }
}
package com.movieProj.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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

    public void addToDatabase(Document document) {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> movieCollection = movieDatabase.getCollection(this.collectionName);

            movieCollection.insertOne(document);

        }

    }

    public void createCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            movieDatabase.createCollection(this.collectionName);

        }

    }

    public void deleteCollection() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            movieDatabase.getCollection(this.collectionName).drop();

        }

    }

    public void deleteAllDocuments() {

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase movieDatabase = mongoClient.getDatabase(this.databaseName);
            MongoCollection<Document> movieCollection = movieDatabase.getCollection(this.collectionName);

            movieCollection.deleteMany(new Document());

        }

    }

}
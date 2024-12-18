package com.movieProj.menu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.bson.BsonValue;
import org.bson.Document;

import com.movieProj.database.Database;
import com.movieProj.movie.Movie;
import com.movieProj.nlp.TFIDF;
import com.movieProj.nlp.SimilarMovies;
import com.movieProj.nlp.Processor;


public class Menu {

    
        private Processor processor = new Processor("src/resources/listOfStopWords.txt");
        private TFIDF tfidf = new TFIDF(processor);
    
        private Database movieDatabase;
    
        //uploads entrys to new collection, prints menu
        public void startUp() {
    
            movieDatabase = new Database("movieProj", "movieInfo");
            movieDatabase.createCollection();
    
            String csvFile = "src/resources/easyMoviesFile.csv";
            String line;
            String delimiter = "#";
    
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.readLine(); // Skip header line
    
                while ((line = br.readLine()) != null) {
                    try {
                        String[] movieData = line.split(delimiter);
                        String movieTitle = movieData[1];
                        String moviePlot = movieData[7];
                        String movieGenre = movieData[5];
                        Integer movieReleaseYear = Integer.parseInt(movieData[0]);
    
                        Movie movieObject = new Movie(movieTitle, moviePlot, movieGenre, movieReleaseYear);
                        movieDatabase.addToDatabase(movieObject.getDocument());
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public void shutDown() {
            if (movieDatabase != null) {
                movieDatabase.deleteCollection();
                System.out.println("Database shutdown complete.");
            } else {
                System.out.println("No database to shut down.");
            }
        }
    //adds movie to database
   
        public void addMovieToDatabase(Scanner scanner) {
            try {
                System.out.println("Enter the name of the movie:");
                String movieTitle = scanner.nextLine();
                System.out.println("Enter the movie plot:");
                String moviePlot = scanner.nextLine();
                System.out.println("Enter the movie genre:");
                String movieGenre = scanner.nextLine();
                System.out.println("Enter the release year:");
                Integer movieReleaseYear = Integer.parseInt(scanner.nextLine());
    
                // Create Movie object and add it to the database
                Movie userMovie = new Movie(movieTitle, moviePlot, movieGenre, movieReleaseYear);
                movieDatabase.addToDatabase(userMovie.getDocument());
                System.out.println("Movie added successfully!");
            } catch (Exception e) {
                System.err.println("Error adding movie: " + e.getMessage());
            }
        }
        public static Movie fromDocument(Document doc) {
            String title = doc.getString("title");
            String plot = doc.getString("plot");
            String genre = doc.getString("genre");
            int releaseYear = doc.getInteger("releaseYear");
            return new Movie(title, plot, genre, releaseYear);
        }
   
    
        public void findSimilarMovies(Scanner scanner) {
                System.out.println("Please enter overview of the movie");
                String movieOverview = scanner.nextLine();
        
                System.out.println("How many movies would you like to see?");
                int numRecommendations = Integer.parseInt(scanner.nextLine());

        
                System.out.println("Finding similar movies...");
        
                SimilarMovies recommender = new SimilarMovies(processor, tfidf);
                ArrayList<Document> allMovies = movieDatabase.getAllMovies();  // Assuming you have a method to get all movies
            ArrayList<Movie> filteredMovies = new ArrayList<>();
        
            System.out.println("Enter the genre to filter by (or press Enter to skip):");
            String genreFilter = scanner.nextLine().trim();
            
         
            
            // Convert documents to Movie objects and filter by genre
            for (Document doc : allMovies) {
                Movie movie = Movie.fromDocument(doc); // Assuming this method exists
                if (genreFilter.isEmpty() || movie.getGenre().equalsIgnoreCase(genreFilter)) {
                    filteredMovies.add(movie);
                }
            }

    // If no movies are found for that genre
    if (filteredMovies.isEmpty()) {
        System.out.println("No movies found for that genre.");
        return;
    }

    // Use recommender to find similar movies within the filtered list
    ArrayList<Movie> recommendations = recommender.recommendMoviesByGenre(filteredMovies, numRecommendations);

    // Display the recommended movies
    for (Movie movie : recommendations) {
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Plot: " + movie.getPlot());
        System.out.println("Genre: " + movie.getGenre());
        System.out.println("Release Year: " + movie.getReleaseYear());
    }
}

   

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMovie App Menu:");
            System.out.println("1. Add a movie to the database.");
            System.out.println("2. Get details of a movie from the database.");
            System.out.println("3. Find similar movies");
            System.out.println("4. Exit.");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addMovieToDatabase(scanner);
                        break;
                    case 2:
                    System.out.println("under construction");
                        break;
                    case 3:
                        findSimilarMovies(scanner);
                        break;
                    case 4:
                        System.out.println("Exiting the movie app...");
                        shutDown();
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        System.out.println("Initializing the movie app...");
        menu.startUp();
        menu.runMenu();
    }
}

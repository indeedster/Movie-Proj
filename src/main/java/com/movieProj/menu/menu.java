package com.movieProj.menu;

import com.movieProj.database.Database;
import com.movieProj.movie.Movie;

import java.util.Scanner;
import java.util.List;
import org.bson.Document;


public class Menu {

    private Database movieDatabase;

    public Menu() {
        movieDatabase = new Database("movieProj", "movieInfo");
    }

    public void startUp() {
        movieDatabase.createCollection();
        System.out.println("Initializing movie database...");
    }

    public void shutDown() {
        movieDatabase.deleteCollection();
        System.out.println("Database shutdown complete.");
    }

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

    public void findSimilarMoviesByGenre(Scanner scanner) {
        try {
            System.out.println("Enter the genre to search for similar movies:");
            String searchGenre = scanner.nextLine();

            // Retrieve movies by genre
            List<Movie> genreResults = movieDatabase.findMoviesByGenre(searchGenre);

            if (genreResults.isEmpty()) {
                System.out.println("No movies found for the given genre.");
            } else {
                System.out.println("Movies found:");
                for (Movie movie : genreResults) {
                    System.out.println(movie);
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding similar movies: " + e.getMessage());
        }
    }

    public void getMovieDetails(Scanner scanner) {
        try {
            System.out.println("Enter the name of the movie to search:");
            String movieTitle = scanner.nextLine();

            // Search for the movie in the database
            Document movie = movieDatabase.findDocument("title", movieTitle);

            if (movie != null) {
                System.out.println("Movie found: " + movie.toJson());
            } else {
                System.out.println("Movie not found.");
            }
        } catch (Exception e) {
            System.err.println("Error searching for movie: " + e.getMessage());
        }
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMovie App Menu:");
            System.out.println("1. Add a movie to the database.");
            System.out.println("2. Get details of a movie from the database.");
            System.out.println("3. Find similar movies by genre.");
            System.out.println("4. Exit.");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addMovieToDatabase(scanner);
                        break;
                    case 2:
                        getMovieDetails(scanner);
                        break;
                    case 3:
                        findSimilarMoviesByGenre(scanner);
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

package com.movieProj.menu;

import com.movieProj.database.Database;
import com.movieProj.movie.Movie;

import java.util.List;
import java.util.Scanner;

public class MovieAppMenu {

    private Database movieDatabase;

    public MovieAppMenu() {
        
        this.movieDatabase = new Database("movieProj", "movieInfo");
    }

    
    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMovie App Menu:");
            System.out.println("1. Add a movie to the database.");
            System.out.println("2. Find similar movies by genre.");
            System.out.println("3. Exit.");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addMovieToDatabase(scanner);
                        break;
                    case 2:
                        findSimilarMoviesByGenre(scanner);
                        break;
                    case 3:
                        System.out.println("Exiting the movie app...");
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

    // Method to add a movie to the database
    private void addMovieToDatabase(Scanner scanner) {
        try {
            System.out.println("Enter the name of the movie:");
            String movieTitle = scanner.nextLine();
            System.out.println("Enter the movie plot:");
            String moviePlot = scanner.nextLine();
            System.out.println("Enter the movie genre:");
            String movieGenre = scanner.nextLine();
            System.out.println("Enter the release year:");
            Integer movieReleaseYear = Integer.parseInt(scanner.nextLine());

            // Create Movie object and add to database
            Movie userMovie = new Movie(movieTitle, moviePlot, movieGenre, movieReleaseYear);
            movieDatabase.addToDatabase(userMovie.getDocument());
            System.out.println("Movie added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding movie: " + e.getMessage());
        }
    }

    
    private void findSimilarMoviesByGenre(Scanner scanner) {
        try {
            System.out.println("Enter the genre to search for similar movies:");
            String searchGenre = scanner.nextLine();

            
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

    public static void main(String[] args) {
        MovieAppMenu movieAppMenu = new MovieAppMenu();
        movieAppMenu.startMenu();
    }
}


package main.java.com.movieProj.database;

import org.bson.Document;
import com.movieapp.database.Database;
import com.movieapp.movie.Movie;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Menu {

    public void startUp() {
        Database movieDatabase = new Database("movie_app_database", "movie_data");
        movieDatabase.createCollection();

        String csvFile = "src/main/resources/editedMoviesFile.csv";
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
        Database movieDatabase = new Database("movie_app_database", "movie_data");
        movieDatabase.deleteCollection();
    }

    public void addMovieToDatabase() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the name of the movie:");
            String movieTitle = scanner.nextLine();
            System.out.println("Enter the movie plot:");
            String moviePlot = scanner.nextLine();
            System.out.println("Enter the movie genre:");
            String movieGenre = scanner.nextLine();
            System.out.println("Enter the release year");
            Integer movieReleaseYear = Integer.parseInt(scanner.nextLine());
            Movie userMovie = new Movie(movieTitle, moviePlot, movieGenre, movieReleaseYear);
            Database movieDatabase = new Database("movieProj", "movieInfo");
            movieDatabase.addToDatabase(userMovie.getDocument());
            System.out.println("Movie added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding movie: " + e.getMessage());
        }
    }

    // public void getMovieDetails() {
    //     try (Scanner scanner = new Scanner(System.in)) {
    //         System.out.println("Enter the name of the movie to search:");
    //         String movieTitle = scanner.nextLine();

    //         Database movieDatabase = new Database("movieProj", "movieInfo");
    //         Document movie = movieDatabase.findDocument("title", movieTitle);

    //         if (movie != null) {
    //             System.out.println("Movie found: " + movie.toJson());
    //         } else {
    //             System.out.println("Movie not found.");
    //         }
    //     }
    // }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMovie App Menu:");
            System.out.println("1. Add a movie to the database.");
            System.out.println("2. Get details of a movie from the database.");
            System.out.println("3. Find similar movies.");
            System.out.println("4. Exit.");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addMovieToDatabase();
                        break;
                    case 2:
                        // getMovieDetails();
                        System.out.println("Under Repair");
                        break;
                    case 3:
                        System.out.println("Find similar movies functionality coming soon!");
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

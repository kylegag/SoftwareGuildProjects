/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.ui;

import com.sg.movie.dao.MovieDaoException;
import com.sg.movie.dto.Movie;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kyle
 */
public class MovieView {

    UserIO io;

    public MovieView(UserIO io) {
        this.io = io;
    }

    public void welcomeMessage() {
        io.print("\nWelcome to the Movie Database!\n");
    }

    public int displayOptionsAndGetResponse() {
        io.print("===================");
        io.print("1. Add Movie");
        io.print("2. Remove Movie");
        io.print("3. Edit Movie");
        io.print("4. List All Movies");
        io.print("5. Display Movie Info");
        io.print("6. Search for a Movie");
        io.print("7. Exit");

        return io.readInt("Please select from the above options: ", 1, 7);
    }

    public void displayGoodByeMessage() {
        io.print("Okay, see you next time.");
    }

    public Movie getNewMovie() {
        Movie film = new Movie();
        String title = io.readString("Enter the movie title: ");
        String date = io.readString("Enter the release date: ");
        String rating = io.readString("Enter the movie's MPAA rating: ");
        String director = io.readString("Enter the director's name: ");
        String studio = io.readString("Enter the movie's studio: ");
        String notes = io.readString("Enter notes about the movie: ");

        film.setTitle(title);
        film.setReleaseDate(date);
        film.setRating(rating);
        film.setDirector(director);
        film.setStudio(studio);
        film.setNotes(notes);

        return film;
    }

    public void displayErrorMessage(String ex) {
        io.print("\n=====Error=====");
        io.print(ex);
    }

    public int getMovieId(String operation) {
        return io.readInt("Enter the ID of the movie you would like to " + operation + ": ");
    }

    public void displayMovieNotFound(int ID) {
        this.displayErrorMessage("No movies in database with ID: " + ID + "\n");
    }

    public void displayMovieRemoved() {
        io.print("Movie successfully removed!");
    }

    public void displayMovieAdded() {
        io.print("Movie successfully added!");
    }

    public void displayNoResults() {
        io.print("There are no movies in the database that match this search!");
    }

    // This methods feels like a cheap way to get around the MVC model
    public String promptToEditField(String field) {
        return io.readString(field);
    }

    public void displayMovieUpdated() {
        io.print("Movie successfully updated!");
    }

    public String startMovieSearch() {
        return io.readString("\nSearch: ");
    }

    public void displayMovieInfo(Movie film) {
        String buf;
        if (isValid(buf = film.getTitle())) {
            io.print("\n======" + buf + "======");
        } else {
            io.print("\n======(No title)======");
        }

        if (isValid(buf = film.getReleaseDate())) {
            io.print("Release date: " + buf);
        }
        if (isValid(buf = film.getRating())) {
            io.print("MPAA rating: " + buf);
        }
        if (isValid(buf = film.getDirector())) {
            io.print("Director: " + buf);
        }
        if (isValid(buf = film.getStudio())) {
            io.print("Studio: " + buf);
        }
        if (isValid(buf = film.getNotes())) {
            io.print("Notes: " + buf);
        }
        io.print("Movie ID: " + film.getId() + "\n");
    }

    private boolean isValid(String str) {
        if (str != null && (!"".equals(str))) {
            return true;
        } else {
            return false;
        }
    }

}

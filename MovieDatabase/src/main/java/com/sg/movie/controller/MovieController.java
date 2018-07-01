/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.controller;

import com.sg.movie.dao.MovieDao;
import com.sg.movie.dao.MovieDaoException;
import com.sg.movie.dto.Movie;
import com.sg.movie.ui.MovieView;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class MovieController {

    MovieView mv;
    MovieDao dao;

    public MovieController(MovieView mv, MovieDao dao) {
        this.mv = mv;
        this.dao = dao;
    }

    public void run() {
        boolean isRunning = true;
        int option;
        try {
            startUp();
            while (isRunning) {
                option = mv.displayOptionsAndGetResponse();

                switch (option) {
                    case 1:
                        addMovie();
                        break;
                    case 2:
                        removeMovie();
                        break;
                    case 3:
                        editMovie();
                        break;
                    case 4:
                        listMovies();
                        break;
                    case 5:
                        displayMovieInfo();
                        break;
                    case 6:
                        searchMovie();
                        break;
                    case 7:
                        isRunning = false;
                        break;
                }
            }
            mv.displayGoodByeMessage();
        } catch (MovieDaoException ex) {
            mv.displayErrorMessage(ex.getMessage());
        }
    }

    private void startUp() throws MovieDaoException {
        mv.welcomeMessage();
        dao.loadMovies();
    }

    private void addMovie() throws MovieDaoException {
        Movie film = mv.getNewMovie();
        if(dao.addMovie(film)){
            mv.displayMovieAdded();
        } else {
            mv.displayErrorMessage("Movie unable to be added.");
        }

    }

    private void removeMovie() throws MovieDaoException {
        int id = mv.getMovieId("remove");
        if (dao.removeMovie(id)) {
            mv.displayMovieRemoved();
        } else {
            mv.displayMovieNotFound(id);
        }
    }

    private void editMovie() throws MovieDaoException {
        int id = mv.getMovieId("edit");
        String buf;
        Movie film = dao.getMovieById(id);
        if (film != null) {
            if (!"".equals(buf = mv.promptToEditField("Title" + wrapIfValid(film.getTitle()) + ": "))) {
                film.setTitle(buf);
            }
            if (!"".equals(buf = mv.promptToEditField("Release date" + wrapIfValid(film.getReleaseDate()) + ": "))) {
                film.setReleaseDate(buf);
            }
            if (!"".equals(buf = mv.promptToEditField("MPAA rating" + wrapIfValid(film.getRating()) + ": "))) {
                film.setRating(buf);
            }
            if (!"".equals(buf = mv.promptToEditField("Director" + wrapIfValid(film.getDirector()) + ": "))) {
                film.setDirector(buf);
            }
            if (!"".equals(buf = mv.promptToEditField("Studio" + wrapIfValid(film.getStudio()) + ": "))) {
                film.setStudio(buf);
            }
            if (!"".equals(buf = mv.promptToEditField("Notes" + wrapIfValid(film.getNotes()) + ": "))) {
                film.setNotes(buf);
            }
            if (dao.updateMovie(film)) {
                mv.displayMovieUpdated();
            }
        } else {
            mv.displayMovieNotFound(id);
        }
    }

    public void listMovies() throws MovieDaoException {
        List<Movie> films = dao.getAllMovies();
        for (Movie film : films) {
            mv.displayMovieInfo(film);
        }
        if (films.isEmpty()) {
            mv.displayErrorMessage("No movies to list!");
        }
    }

    public void displayMovieInfo() throws MovieDaoException {
        int id = mv.getMovieId("display");
        Movie film;
        if ((film = dao.getMovieById(id)) != null) {
            mv.displayMovieInfo(film);
        } else {
            mv.displayMovieNotFound(id);
        }
    }
    
    private void searchMovie() throws MovieDaoException {
        String searchBar = mv.startMovieSearch();
        List<Movie> results = dao.searchForTitle(searchBar);
        
        for(Movie film: results) {
            mv.displayMovieInfo(film);
        }
        
        if(results.isEmpty()) {
            mv.displayNoResults();
        }
            
    }

    private String wrapIfValid(String str) {
        if (str != null && (!str.isEmpty())) {
            return "(" + str + ")";
        } else {
            return "";
        }
    }
}
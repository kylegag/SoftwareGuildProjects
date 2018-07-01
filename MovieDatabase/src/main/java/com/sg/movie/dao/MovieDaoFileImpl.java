package com.sg.movie.dao;

import com.sg.movie.dto.Movie;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kyle
 */
public class MovieDaoFileImpl implements MovieDao {

    private List<Movie> movies = new ArrayList<Movie>();
    public static final String MOVIE_FILE = "movies.txt";
    public static final String DELIMITER = "::";

    @Override
    public boolean updateMovie(Movie updatedMovie) throws MovieDaoException {
        if (this.removeMovie(updatedMovie.getId())) {
            return this.addMovie(updatedMovie);
        }
        return false;
    }

    @Override
    public boolean addMovie(Movie film) throws MovieDaoException {
        if (this.getMovieById(film.getId()) == null) {
            movies.add(film);
            writeMovies();
            return true;
        }
        return false;
    }

    @Override
    public List<Movie> getAllMovies() throws MovieDaoException {
        loadMovies();
        return movies;
    }

    @Override
    public Movie getMovieById(int id) throws MovieDaoException {
        loadMovies();
        for (Movie film : movies) {
            if (film.getId() == id) {
                return film;
            }
        }
        return null;
    }

    @Override
    public List<Movie> searchForTitle(String title) throws MovieDaoException {
        int searchLeng = title.length();
        String searchTitle = title.toLowerCase();
        int titleLeng;
        String titleBeginning;

        List<Movie> results = new ArrayList<>();

        for (Movie film : movies) {
            titleLeng = film.getTitle().length();
            if (searchLeng <= titleLeng) {
                titleBeginning = film.getTitle().substring(0, searchLeng).toLowerCase();
                if (titleBeginning.equals(searchTitle)) {
                    results.add(film);
                }
            }
        }

        return results;
    }

    @Override
    public boolean removeMovie(int id) throws MovieDaoException {
        for (Movie film : movies) {
            if (film.getId() == id) {
                movies.remove(film);
                writeMovies();
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadMovies() throws MovieDaoException {

        List<Movie> nextList = new ArrayList<Movie>();

        Scanner sc;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(MOVIE_FILE)));
        } catch (FileNotFoundException ex) {
            throw new MovieDaoException("Could not load movies from file!", ex);
        }

        String currentLine;
        String[] currentTokens;
        Movie nextMovie;

        while (sc.hasNext()) {
            currentLine = sc.nextLine();

            currentTokens = currentLine.split(DELIMITER);

            nextMovie = new Movie(Integer.parseInt(currentTokens[6]));
            nextMovie.setTitle(currentTokens[0]);
            nextMovie.setReleaseDate(currentTokens[1]);
            nextMovie.setRating(currentTokens[2]);
            nextMovie.setDirector(currentTokens[3]);
            nextMovie.setStudio(currentTokens[4]);
            nextMovie.setNotes(currentTokens[5]);

            nextList.add(nextMovie);

        }
        sc.close();

        movies = nextList;

        int nextId = 1;
        for (Movie film : movies) {
            if (nextId <= film.getId()) {
                nextId = film.getId() + 1;
            }
        }

        Movie.resetNextId(nextId);
    }

    @Override
    public void writeMovies() throws MovieDaoException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(MOVIE_FILE));
        } catch (IOException ex) {
            throw new MovieDaoException("Could not save movie data!", ex);
        }

        for (Movie film : movies) {
            out.println(film.getTitle() + DELIMITER
                    + film.getReleaseDate() + DELIMITER
                    + film.getRating() + DELIMITER
                    + film.getDirector() + DELIMITER
                    + film.getStudio() + DELIMITER
                    + film.getNotes() + DELIMITER
                    + film.getId());
            out.flush();
        }

        out.close();
    }
}

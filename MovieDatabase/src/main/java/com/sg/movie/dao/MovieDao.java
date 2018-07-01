/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.dao;

import com.sg.movie.dto.Movie;
import java.util.List;

/**
 *
 * @author Kyle
 */
public interface MovieDao {
    
    List<Movie> searchForTitle(String title) throws MovieDaoException;
    
    boolean updateMovie(Movie film) throws MovieDaoException;
    
    boolean addMovie(Movie film) throws MovieDaoException;
    
    List<Movie> getAllMovies() throws MovieDaoException;
    
    Movie getMovieById(int id) throws MovieDaoException;
    
    boolean removeMovie(int id) throws MovieDaoException;
    
    void loadMovies() throws MovieDaoException;
    
    void writeMovies() throws MovieDaoException;
}

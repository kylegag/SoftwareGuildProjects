/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.dao;

import com.sg.movie.dto.Movie;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class MovieDaoJdbcTemplateTest {
    
    MovieDao dao = new MovieDaoJdbcTemplateImpl();
    
    public MovieDaoJdbcTemplateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of searchForTitle method, of class MovieDaoSQLImpl.
     */
    @Test
    public void testSearchForTitleAndAddMovie() throws Exception {
        
    }

    /**
     * Test of addMovie method, of class MovieDaoSQLImpl.
     */
    @Test
    public void testAddMovie() throws Exception {
        Movie film = new Movie();
        film.setTitle("Toy Story");
        film.setReleaseDate("April 5th");
        film.setRating("Good");
        film.setDirector("Bob");
        film.setStudio("Blue Studio");
        film.setNotes("Quite the film");
        
        dao.addMovie(film);
    }

    /**
     * Test of getAllMovies method, of class MovieDaoSQLImpl.
     */
    @Test
    public void testGetAllMovies() throws Exception {
        
    }

    /**
     * Test of getMovieById method, of class MovieDaoSQLImpl.
     */
    @Test
    public void testGetMovieByIdAndUpdateMovieAndRemoveMovie() throws Exception {
        
        final String updatedString = "as;dlfkjg";
        final int id = 4959;
        
        Movie toAdd = new Movie(id);
        
        toAdd.setNotes("To test");
        
        dao.addMovie(toAdd);
        
        toAdd.setNotes(updatedString);
        
        dao.updateMovie(toAdd);
        
        assertEquals(updatedString,toAdd.getNotes());
       
        dao.removeMovie(id);
        
        assertNull(dao.getMovieById(id));
    }

    /**
     * Test of loadMovies method, of class MovieDaoSQLImpl.
     */
    @Test
    public void testLoadMovies() throws Exception {
    }

    /**
     * Test of writeMovies method, of class MovieDaoSQLImpl.
     */
    @Test
    public void testWriteMovies() throws Exception {
    }
    
}

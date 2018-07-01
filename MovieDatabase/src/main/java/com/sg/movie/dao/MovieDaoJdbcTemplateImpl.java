/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.dao;

import com.sg.movie.dto.Movie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Kyle
 */
public class MovieDaoJdbcTemplateImpl implements MovieDao {

    private JdbcTemplate jt;

    public MovieDaoJdbcTemplateImpl() {
        try {
            jt = (new MySqlDatabase()).getJdbcTemplate();
        } catch (SQLException ex) {
            System.out.println("Error creating JdbcTemplate");
        }
    }

    @Override
    public List<Movie> searchForTitle(String title) throws MovieDaoException {
        String sql = "SELECT * FROM Movie"
                + " WHERE Title LIKE ?;";
        return jt.query(sql, new MovieMapper(), title + "%");
    }

    @Override
    public boolean updateMovie(Movie film) throws MovieDaoException {
        String sql = "UPDATE Movie SET"
                + " Title = ?, "
                + " ReleaseDate = ?,"
                + " Rating = ?,"
                + " Director = ?,"
                + " Studio = ?,"
                + " Notes = ?"
                + " WHERE MovieId = ?;";

        return jt.update(sql,
                film.getTitle(),
                film.getReleaseDate(),
                film.getRating(),
                film.getDirector(),
                film.getStudio(),
                film.getNotes(),
                film.getId()) > 0;
    }

    @Override
    public boolean addMovie(Movie film) throws MovieDaoException {
        return jt.update(
                "INSERT INTO Movie (Title, ReleaseDate, Rating, Director, Studio, Notes, MovieId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);",
                film.getTitle(),
                film.getReleaseDate(),
                film.getRating(),
                film.getDirector(),
                film.getStudio(),
                film.getNotes(),
                film.getId()) > 0;
    }

    @Override
    public List<Movie> getAllMovies() throws MovieDaoException {
        return jt.query("SELECT * FROM Movie;", new MovieMapper());
    }

    @Override
    public Movie getMovieById(int id) throws MovieDaoException {
        try {
            return jt.queryForObject(
                    "SELECT * FROM Movie WHERE MovieId = ?",
                    new MovieMapper(),
                    id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean removeMovie(int id) throws MovieDaoException {
        return jt.update("DELETE FROM Movie WHERE MovieId = ?;", id) > 0;
    }

    @Override
    public void loadMovies() throws MovieDaoException {
    }

    @Override
    public void writeMovies() throws MovieDaoException {
    }

    private static final class MovieMapper implements RowMapper<Movie> {

        @Override
        public Movie mapRow(ResultSet rs, int i) throws SQLException {
            Movie m = new Movie(rs.getInt("MovieId"));
            m.setTitle(rs.getString("Title"));
            m.setReleaseDate(rs.getString("ReleaseDate"));
            m.setRating(rs.getString("Rating"));
            m.setDirector(rs.getString("Director"));
            m.setStudio(rs.getString("Studio"));
            m.setNotes(rs.getString("Notes"));

            return m;
        }

    }

}

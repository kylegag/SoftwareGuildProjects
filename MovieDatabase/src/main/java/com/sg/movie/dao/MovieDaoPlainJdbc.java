/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.dao;

import com.sg.movie.dto.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class MovieDaoPlainJdbc implements MovieDao {

    @Override
    public List<Movie> searchForTitle(String title) throws MovieDaoException {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = MySqlDatabase.getDataSource().getConnection()) {

            String sql = "SELECT * FROM Movie"
                    + " WHERE Title LIKE ?;";
            PreparedStatement findStatement = conn.prepareStatement(sql);
            findStatement.setString(1, title + "%");

            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                movies.add(readMovie(rs));
            }
        } catch (SQLException ex) {
            throw new MovieDaoException("DB Failure.", ex);
        }

        return movies;
    }

    private Movie readMovie(ResultSet rs) throws SQLException {
        Movie m = new Movie(rs.getInt("MovieId"));
        m.setTitle(rs.getString("Title"));
        m.setReleaseDate(rs.getString("ReleaseDate"));
        m.setRating(rs.getString("Rating"));
        m.setDirector(rs.getString("Director"));
        m.setStudio(rs.getString("Studio"));
        m.setNotes(rs.getString("Notes"));
        return m;
    }

    @Override
    public boolean updateMovie(Movie film) throws MovieDaoException {
        try (Connection conn = MySqlDatabase.getDataSource().getConnection()) {

            String sql = "update Movie set"
                    + " Title = ?, ReleaseDate = ?,"
                    + " Rating = ?, Director = ?,"
                    + " Studio = ?, Notes = ?"
                    + " where MovieId = ?;";

            PreparedStatement findStatement = conn.prepareStatement(sql);

            findStatement.setString(1, film.getTitle());
            findStatement.setString(2, film.getReleaseDate());
            findStatement.setString(3, film.getRating());
            findStatement.setString(4, film.getDirector());
            findStatement.setString(5, film.getStudio());
            findStatement.setString(6, film.getNotes());
            findStatement.setInt(7, film.getId());

            return findStatement.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new MovieDaoException("DB Failure.", ex);
        }
    }

    @Override
    public boolean addMovie(Movie film) throws MovieDaoException {
        try (Connection conn = MySqlDatabase.getDataSource().getConnection()) {

            String sql = "insert into Movie "
                    + "(Title, ReleaseDate, Rating, Director, Studio, Notes, MovieId)"
                    + "values ( ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement findStatement = conn.prepareStatement(sql);

            findStatement.setString(1, film.getTitle());
            findStatement.setString(2, film.getReleaseDate());
            findStatement.setString(3, film.getRating());
            findStatement.setString(4, film.getDirector());
            findStatement.setString(5, film.getStudio());
            findStatement.setString(6, film.getNotes());
            findStatement.setInt(7, film.getId());

            return findStatement.executeUpdate() > 0;

        } catch (SQLException ex) {
            throw new MovieDaoException("DB Failure.", ex);
        }
    }

    @Override
    public List<Movie> getAllMovies() throws MovieDaoException {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = MySqlDatabase.getDataSource().getConnection()) {

            String sql = "SELECT * FROM Movie;";
            PreparedStatement findStatement = conn.prepareStatement(sql);

            ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                movies.add(readMovie(rs));
            }
        } catch (SQLException ex) {
            throw new MovieDaoException("DB Failure.", ex);
        }

        return movies;
    }

    @Override
    public Movie getMovieById(int id) throws MovieDaoException {
        try (Connection conn = MySqlDatabase.getDataSource().getConnection()) {

            String sql = "SELECT * FROM Movie"
                    + " WHERE MovieId = ?";
            PreparedStatement findStatement = conn.prepareStatement(sql);
            findStatement.setInt(1, id);

            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                return readMovie(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new MovieDaoException("DB Failure.", ex);
        }
    }

    @Override
    public boolean removeMovie(int id) throws MovieDaoException {
        try (Connection conn = MySqlDatabase.getDataSource().getConnection()) {

            String sql = "delete from Movie"
                    + " where MovieId = ?;";
            PreparedStatement findStatement = conn.prepareStatement(sql);
            findStatement.setInt(1, id);

            return findStatement.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            throw new MovieDaoException("DB Failure.", ex);
        }
    }

    @Override
    public void loadMovies() throws MovieDaoException {
    }

    @Override
    public void writeMovies() throws MovieDaoException {
    }

}

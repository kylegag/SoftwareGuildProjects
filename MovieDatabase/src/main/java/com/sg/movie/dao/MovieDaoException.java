/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.movie.dao;

/**
 *
 * @author Kyle
 */
public class MovieDaoException extends Exception {

    /**
     * Creates a new instance of <code>MovieDaoException</code> without detail
     * message.
     */
    public MovieDaoException() {
    }

    /**
     * Constructs an instance of <code>MovieDaoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MovieDaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

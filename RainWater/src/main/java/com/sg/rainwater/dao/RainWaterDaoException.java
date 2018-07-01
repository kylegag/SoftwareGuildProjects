/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dao;

/**
 *
 * @author Kyle
 */
public class RainWaterDaoException extends Exception {

    /**
     * Creates a new instance of <code>RainWaterDaoException</code> without
     * detail message.
     */
    public RainWaterDaoException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>RainWaterDaoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RainWaterDaoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

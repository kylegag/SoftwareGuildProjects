/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.powerball.service;

/**
 *
 * @author Kyle
 */
public class PickOutOfRangeException extends Exception {

    /**
     * Creates a new instance of <code>PickOutOfRangeException</code> without
     * detail message.
     */
    public PickOutOfRangeException() {
    }

    /**
     * Constructs an instance of <code>PickOutOfRangeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PickOutOfRangeException(String msg) {
        super(msg);
    }
}

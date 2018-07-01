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
public class PickNumbersNotUniqueException extends Exception {

    /**
     * Creates a new instance of <code>PickNumbersNotUniqueException</code>
     * without detail message.
     */
    public PickNumbersNotUniqueException() {
    }

    /**
     * Constructs an instance of <code>PickNumbersNotUniqueException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PickNumbersNotUniqueException(String msg) {
        super(msg);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

/**
 *
 * @author Kyle
 */
public class InsufficientFundsException extends Exception {

    /**
     * Creates a new instance of <code>InsufficientFundsException</code> without
     * detail message.
     */
    public InsufficientFundsException() {
    }

    /**
     * Constructs an instance of <code>InsufficientFundsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InsufficientFundsException(String msg) {
        super(msg);
    }
}

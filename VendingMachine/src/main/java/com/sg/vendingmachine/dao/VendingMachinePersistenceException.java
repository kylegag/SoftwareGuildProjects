/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

/**
 *
 * @author Kyle
 */
public class VendingMachinePersistenceException extends Exception {

    /**
     * Creates a new instance of <code>VendingMachinePersistenceException</code>
     * without detail message.
     */
    public VendingMachinePersistenceException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>VendingMachinePersistenceException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public VendingMachinePersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

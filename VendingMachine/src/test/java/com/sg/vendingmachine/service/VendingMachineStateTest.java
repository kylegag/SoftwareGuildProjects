/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class VendingMachineStateTest {

    /*
    VendingMachineDaoStubImpl stub = new VendingMachineDaoStubImpl();
    //VendingMachineState state = new VendingMachineState(stub);

    public VendingMachineStateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            state.initialize();
        } catch(VendingMachinePersistenceException ex) {
            fail("Failed to initialize.");
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetItems() throws Exception {
        assertEquals(2, state.getItems().size());
    }


    @Test
    public void testGetAvailableItems() {
        assertEquals(1, state.getAvailableItems().size());
    }


    @Test
    public void testInsufficientFundsExceptions() throws Exception {

        boolean pass = false;

        try {
            state.purchase("Twix", (new BigDecimal("1.00").setScale(2, RoundingMode.DOWN)));
        } catch (InsufficientFundsException ex) {
            pass = true;
        } catch (OutOfStockException | VendingMachinePersistenceException ex) {
        }

        assertEquals(true, pass);
    }
    
    
    @Test
    public void testOutOfStockExceptions() throws Exception {

        boolean pass = false;

        try {
            state.purchase("Snickers", (new BigDecimal("2.00").setScale(2, RoundingMode.DOWN)));
        } catch (OutOfStockException ex) {
            pass = true;
        } catch (VendingMachinePersistenceException | InsufficientFundsException ex) {
        }

        assertEquals(true, pass);
    }

    @Test
    public void testPurchase() throws Exception {
        Change ch1;
        Change ch2;
        Change ch3;

        try {
            ch1 = state.purchase("Twix", (new BigDecimal("2.37")).setScale(2, RoundingMode.DOWN));
            ch2 = state.purchase("Twix", (new BigDecimal("2.00")).setScale(2, RoundingMode.DOWN));
            ch3 = state.purchase("Twix", (new BigDecimal("3.01")).setScale(2, RoundingMode.DOWN));
        } catch (Exception ex) {
            return;
        }

        assertEquals(new Change(1, 1, 0, 2), ch1);
        assertEquals(new Change(0, 0, 0, 0), ch2);
        assertEquals(new Change(4, 0, 0, 1), ch3);
    }
    */
}

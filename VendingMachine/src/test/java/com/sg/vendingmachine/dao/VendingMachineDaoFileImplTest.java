/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
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
public class VendingMachineDaoFileImplTest {

    public static final String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";
    public static final int VALID = 0;
    public static final int FORMAT = 1;
    public static final int INCOMPLETE = 2;

    private VendingMachineDao dao = new VendingMachineDaoFileImpl();

    public VendingMachineDaoFileImplTest() {
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
     * Test of loadItems method, of class VendingMachineDaoFileImpl.
     */
    @Test
    public void testLoadItemsExceptions() throws Exception {

        boolean testFailed = false;

        try {
            fillInventoryFileWithTestData(VALID);
        } catch (VendingMachinePersistenceException ex) {
        }
        try {
            dao.loadItems();
        } catch (VendingMachinePersistenceException ex) {
            fail();
        }

        try {
            fillInventoryFileWithTestData(FORMAT);
        } catch (VendingMachinePersistenceException ex) {
        }
        try {
            testFailed = true;
            dao.loadItems();
        } catch (VendingMachinePersistenceException ex) {
            testFailed = false;
        } finally {
            if (testFailed) {
                fail();
            }
        }

        try {
            fillInventoryFileWithTestData(INCOMPLETE);
        } catch (VendingMachinePersistenceException ex) {
        }
        try {
            testFailed = true;
            dao.loadItems();
        } catch (VendingMachinePersistenceException ex) {
            testFailed = false;
        } finally {
            if (testFailed) {
                fail();
            }
        }
    }

    @Test
    public void testLoadGetItems() throws Exception {

        List<VendingItem> items;
        VendingItem twix = new VendingItem("Twix", new BigDecimal("3.00"), 20);

        try {
            fillInventoryFileWithTestData(VALID);
            dao.loadItems();
        } catch (VendingMachinePersistenceException ex) {
        }

        items = dao.getItems();

        assertEquals(1, items.size());
        
        
        //assertEquals(twix, items.get(0));
        if(!twix.equals(items.get(0))) {
            fail();
        }
    }

    /**
     * Test of decrementInventory method, of class VendingMachineDaoFileImpl.
     */
    @Test
    public void testDecrementInventory() throws Exception {
        int firstDecrement;
        int secondDecrement;

        List<VendingItem> items;
        VendingItem twix;

        try {
            fillInventoryFileWithTestData(VALID);
            dao.loadItems();
        } catch (VendingMachinePersistenceException ex) {
        }

        items = dao.getItems();

        assertEquals(1, items.size());

        twix = items.get(0);

        // firstDecrement = 19
        firstDecrement = twix.decrementStock();

        // secondDecrement = 18
        secondDecrement = twix.decrementStock();

        assertEquals(1, firstDecrement - secondDecrement);
    }

    private void fillInventoryFileWithTestData(int corruptionType)
            throws VendingMachinePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException ex) {
            throw new VendingMachinePersistenceException("Inventory could not be saved!", ex);
        }

        switch (corruptionType) {
            case FORMAT:
                out.println("Twix" + DELIMITER + "3.00" + DELIMITER + "NotAnInteger");
                break;
            case INCOMPLETE:
                out.println("Twix" + DELIMITER);
                break;
            case VALID:
                out.println("Twix" + DELIMITER + "3.00" + DELIMITER + "20");
                break;
        }

        out.flush();
        out.close();
    }

}

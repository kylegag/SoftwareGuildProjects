/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Kyle
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private static final String INVENTORY_FILE = "inventory.txt";
    private static final String DELIMITER = "::";
    private List<VendingItem> items;

    @Override
    public void loadItems() throws VendingMachinePersistenceException {

        List<VendingItem> replacement = new ArrayList<VendingItem>();

        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load inventory prices into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentTokens holds each of the parts of the currentLine after it has
        // been split on our DELIMITER
        // NOTE FOR APPRENTICES: In our case we use :: as our delimiter.  If
        // currentLine looks like this:
        // 1234::Joe::Smith::Java-September2013
        // then currentTokens will be a string array that looks like this:
        //
        // ___________________________________
        // |    |   |     |                  |
        // |1234|Joe|Smith|Java-September2013|
        // |    |   |     |                  |
        // -----------------------------------
        //  [0]  [1]  [2]         [3]
        String[] currentTokens;
        // Go through ROSTER_FILE line by line, decoding each line into a 
        // Student object.
        // Process while we have more lines in the file
        VendingItem nextItem;
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // break up the line into tokens
            currentTokens = currentLine.split(DELIMITER);
            // Create a new Student object and put it into the map of students
            // NOTE FOR APPRENTICES: We are going to use the student id
            // We also have to pass the student id into the Student constructor
            try {
                nextItem = new VendingItem(currentTokens[0],
                        new BigDecimal(currentTokens[1]),
                        Integer.parseInt(currentTokens[2]));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new VendingMachinePersistenceException(e.getMessage());
            }
            // Put currentStudent into the map using studentID as the key
            replacement.add(nextItem);
        }
        // close scanner
        scanner.close();

        items = replacement;
    }

    @Override
    public void decrementInventory(String itemString) throws VendingMachinePersistenceException {

        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException ex) {
            throw new VendingMachinePersistenceException("Inventory could not be saved!", ex);
        }

        String buf;
        for (VendingItem item : items) {
            out.println((buf = item.getName()) + DELIMITER
                    + item.getPrice() + DELIMITER
                    + (buf.toLowerCase().equals(itemString.toLowerCase()) 
                            ? item.decrementStock() : item.getStock())
            );
            out.flush();
        }

        out.close();
    }

    public List<VendingItem> getItems() {
        return items;
    }

}

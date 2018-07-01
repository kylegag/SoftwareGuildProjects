/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Kyle
 */
public class VendingMachineView {

    UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayErrorMsg(String msg) {
        io.print("*****Error*****");
        io.print(msg);
    }

    public void displayChange(Change ch) {
        io.print("Here is your change!");
        int buf;
        if ((buf = ch.getQuarters()) > 0) {
            io.print("Quarters: " + buf);
        }
        if ((buf = ch.getDimes()) > 0) {
            io.print("Dimes: " + buf);
        }
        if ((buf = ch.getNickels()) > 0) {
            io.print("Nickles: " + buf);
        }
        if ((buf = ch.getPennies()) > 0) {
            io.print("Pennies: " + buf);
        }
    }
    
    public void displayExitMessage() {
        io.print("See you next time!");
    }

    public boolean displayOptions(List<VendingItem> options) {
        if(options.isEmpty()) {
            io.print("No options are currently available!");
            return false;
        }
        for (VendingItem item : options) {
            io.print(item.getName() + " - $" + item.getPrice());
        }
        io.print("Available options displayed above.");
        return true;
    }
    
    public boolean giveOptionToExit() {
        String input;
        while(true) {
            input = io.readString("Would you like to (c)ontinue or (e)xit?").toLowerCase();
            if(input.equals("e") || input.equals("exit")) {
                return true;
            } else if(input.equals("c") || input.equals("continue")) {
                return false;
            } else {
                io.print(input+" is not a valid option!");
            }
        }
    }

    public BigDecimal askForFunds() {
        return io.readBigDecimalAsString(
                "Please enter the amount of money you would like to insert: ",
                "0", "").setScale(2, RoundingMode.DOWN);
    }

    public String askForChoice(List<VendingItem> items) {
        String choice = "";
        boolean valid = false;
        List<String> names = items.stream().map(i -> i.getName().toLowerCase()).collect(Collectors.toList());
        while (!valid) {
            choice = io.readString("What would you like to buy?");
            if (names.contains(choice.toLowerCase())) {
                valid = true;
            } else {
                io.print(choice + " is not a valid choice.");
            }
        }
        return choice;
    }
}

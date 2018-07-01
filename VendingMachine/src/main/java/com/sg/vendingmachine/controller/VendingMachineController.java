/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.OutOfStockException;
import com.sg.vendingmachine.service.VendingMachineState;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;

/**
 *
 * @author Kyle
 */
public class VendingMachineController {

    VendingMachineState state;
    VendingMachineView view;

    public VendingMachineController(VendingMachineState state, VendingMachineView view) {
        this.state = state;
        this.view = view;
    }

    public void run() {
        initialize();
        BigDecimal funds;
        String item;
        Change ch;
        while (true) {
            if (!view.displayOptions(state.getAvailableItems())) {
                break;
            } else if (view.giveOptionToExit()) {
                break;
            }
            funds = view.askForFunds();
            try {
                item = view.askForChoice(state.getItems());
                item = item.substring(0, 1).toUpperCase() + item.substring(1);
            } catch (VendingMachinePersistenceException ex) {
                view.displayErrorMsg("Error accessing inventory.");
                continue;
            }
            try {

                ch = state.purchase(item, funds);
                view.displayChange(ch);
            } catch (OutOfStockException ex) {
                view.displayErrorMsg(item + " is out of stock!");
            } catch (InsufficientFundsException ex) {
                view.displayErrorMsg("$" + funds + " is not enough for " + item + "!");
            } catch (VendingMachinePersistenceException ex) {
                view.displayErrorMsg("Error updating inventory.");
            }
        }
        view.displayExitMessage();
    }

    public void initialize() {
        try {
            state.initialize();
        } catch (VendingMachinePersistenceException ex) {
            view.displayErrorMsg("Inventory file unable to be read! Exiting.");
            System.exit(0);
        }
    }
}

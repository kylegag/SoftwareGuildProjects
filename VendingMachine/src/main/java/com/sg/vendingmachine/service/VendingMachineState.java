/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class VendingMachineState {

    public static String QUARTER_VALUE = ".25";
    public static String DIME_VALUE = ".10";
    public static String NICKLE_VALUE = ".05";
    public static String PENNY_VALUE = ".01";

    @Autowired
    @Qualifier("JdbcTemplate")
    VendingMachineDao dao;

    
    public void initialize() throws VendingMachinePersistenceException{
        dao.loadItems();
    }

    public List<VendingItem> getItems() throws VendingMachinePersistenceException {
        dao.loadItems();
        return dao.getItems();
    }

    public List<VendingItem> getAvailableItems() {
        return dao.getItems().stream().filter(i -> i.getStock() > 0).collect(Collectors.toList());
    }

    public Change purchase(String itemString, BigDecimal funds)
            throws InsufficientFundsException, OutOfStockException, VendingMachinePersistenceException {

        int quarters;
        int dimes;
        int nickles;
        int pennies;
        BigDecimal price;
        List<VendingItem> availableItems;
        VendingItem item;

        //Search for item;
        availableItems = getAvailableItems();

        item = availableItems.stream()
                .filter(i -> i.getName().toLowerCase().equals(itemString.toLowerCase()))
                .findFirst()
                .orElse(null);

        if (item == null) {
            throw new OutOfStockException();
        }

        price = item.getPrice();

        //Calculate total change
        if ((funds = funds.subtract(price)).compareTo(new BigDecimal("0")) < 0) {
            throw new InsufficientFundsException();
        }

        // Calculate coins to return
        quarters = funds.divide(scale(new BigDecimal(QUARTER_VALUE)), 0, RoundingMode.DOWN).intValue();
        funds = funds.remainder(new BigDecimal(QUARTER_VALUE));

        dimes = funds.divide(scale(new BigDecimal(DIME_VALUE)), 0, RoundingMode.DOWN).intValue();
        funds = funds.remainder(new BigDecimal(DIME_VALUE));

        nickles = funds.divide(scale(new BigDecimal(NICKLE_VALUE)), 0, RoundingMode.DOWN).intValue();
        funds = funds.remainder(new BigDecimal(NICKLE_VALUE));

        pennies = funds.divide(scale(new BigDecimal(PENNY_VALUE)), 0, RoundingMode.DOWN).intValue();
        //funds = funds.remainder(new BigDecimal(pennies));

        dao.decrementInventory(itemString);
        
        return new Change(quarters, dimes, nickles, pennies);
    }

    private static BigDecimal scale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public static final String INVENTORY_FILE = "inventory.txt";

    private List<VendingItem> items;

    @Override
    public void loadItems() throws VendingMachinePersistenceException {
        items = new ArrayList<>();
        items.add(new VendingItem("Twix", new BigDecimal("2.00"), 20));
        items.add(new VendingItem("Snickers", new BigDecimal("2.00"), 0));
    }

    @Override
    public void decrementInventory(String item) throws VendingMachinePersistenceException {
        if (items == null) {
            return;
        }
        for (VendingItem treat : items) {
            if (treat.getName().toLowerCase().equals(item.toLowerCase())) {
                treat.decrementStock();
            }
        }
    }

    @Override
    public List<VendingItem> getItems() {
        return items;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.util.List;

/**
 *
 * @author Kyle
 */
public interface VendingMachineDao {
    public void loadItems() throws VendingMachinePersistenceException;
    public void decrementInventory(String item) throws VendingMachinePersistenceException;
    public List<VendingItem> getItems();
}

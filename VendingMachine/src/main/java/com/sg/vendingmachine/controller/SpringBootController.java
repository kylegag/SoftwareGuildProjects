/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.OutOfStockException;
import com.sg.vendingmachine.service.VendingMachineState;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kyle
 */
@RestController
public class SpringBootController {

    @Autowired
    private VendingMachineState state;


    @CrossOrigin
    @GetMapping("items")
    public ResponseEntity<List<VendingItem>> getAllItems() {
        try {
            return ResponseEntity.ok(state.getItems());
        } catch (VendingMachinePersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CrossOrigin
    @PutMapping("item/{itemName}/{funds}")
    public ResponseEntity<Change> purchaseItem(@PathVariable String itemName, @PathVariable double funds) {
        Change ch = null;

        try {
            ch = state.purchase(itemName, new BigDecimal(funds));
        } catch (InsufficientFundsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (OutOfStockException ex) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        } catch (VendingMachinePersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(ch);
    }
}

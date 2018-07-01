/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
@Qualifier("JdbcTemplate")
public class VendingMachineJdbcTemplate implements VendingMachineDao {

    @Autowired
    private JdbcTemplate jt;

    @Override
    public void loadItems() throws VendingMachinePersistenceException {
    }

    @Override
    public void decrementInventory(String item) throws VendingMachinePersistenceException {

        VendingItem buf;
        if ((buf = jt.queryForObject(
                "SELECT * FROM Inventory WHERE ItemName = ?",
                new ItemMapper(),
                item)) == null) {
            return;
        }
        int curStock = buf.getStock();

        String sql = "UPDATE Inventory SET"
                + " ItemStock = ?"
                + " WHERE ItemName = ?;";

        jt.update(sql, --curStock, item);
    }

    @Override
    public List<VendingItem> getItems() {
        return jt.query("SELECT * FROM Inventory;", new ItemMapper());
    }

    private static final class ItemMapper implements RowMapper<VendingItem> {

        @Override
        public VendingItem mapRow(ResultSet rs, int i) throws SQLException {
            VendingItem item = new VendingItem(rs.getString("ItemName"),
                    rs.getBigDecimal("ItemPrice"),
                    rs.getInt("ItemStock"));
            return item;
        }

    }
}

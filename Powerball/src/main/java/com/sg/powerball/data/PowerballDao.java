/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.powerball.data;

import com.sg.powerball.models.Pick;
import java.util.List;

/**
 *
 * @author Kyle
 */
public interface PowerballDao {
    public boolean addPick(Pick p);
    public List<Pick> getAll();
    public List<Pick> search(String firstName, String lastName, String email
                           , String state, int pickId);
    public void markAllAsInvalid();
}

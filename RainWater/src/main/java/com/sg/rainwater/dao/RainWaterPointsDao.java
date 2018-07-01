/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dao;

import com.sg.rainwater.dto.Point;
import java.util.List;

/**
 *
 * @author Kyle
 */
public interface RainWaterPointsDao {
    
    public void load(String fileName) throws RainWaterDaoException;
    public List<Point> getPoints();
    public int getMultiplier();
    public void write(String fileName, int multiplier, List<Point> points)
            throws RainWaterDaoException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dao;

import com.sg.rainwater.dto.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class RainWaterPointsStub implements RainWaterPointsDao {

    private int multiplier;
    private List<Point> pts;

    @Override
    public void load(String fileName) throws RainWaterDaoException {
        if ("nonExistent.txt".equals(fileName)) {
            throw new RainWaterDaoException("nonExistent.txt not found");
        } else if ("diagnolMiddleMissing.txt".equals(fileName)) {
            pts = new ArrayList<>();
            pts.add(new Point(0, 0));
            pts.add(new Point(2, 2));
            multiplier = 2;
        } else if ("ticTacToe.txt".equals(fileName)) {
            pts = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                pts.add(new Point(2, j));
                pts.add(new Point(5, j));
            }
            for (int i = 0; i < 8; i++) {
                if (i != 2 && i != 5) {
                    pts.add(new Point(i, 2));
                    pts.add(new Point(i, 5));
                }
            }
            multiplier = 1;
        } else if ("trueDiagnol.txt".equals(fileName)) {
            pts = new ArrayList<>();
            pts.add(new Point(0, 0));
            pts.add(new Point(1, 1));
            pts.add(new Point(2, 2));
            multiplier = 1;
        }
    }

    @Override
    public List<Point> getPoints() {
        return pts;
    }

    @Override
    public int getMultiplier() {
        return multiplier;
    }

    @Override
    public void write(String fileName, int multiplier, List<Point> points) throws RainWaterDaoException {

    }

}

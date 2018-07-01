/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.service;

import static com.sg.rainwater.controller.RainWaterController.MODE;
import com.sg.rainwater.dao.RainWaterDaoException;
import com.sg.rainwater.dao.RainWaterPointsDao;
import com.sg.rainwater.dto.Point;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class RainWaterCreateState {

    private String fileName;

    private RainWaterPointsDao dao;

    private List<Point> points;
    private int multiplier;

    @Autowired
    public RainWaterCreateState(@Qualifier(MODE) RainWaterPointsDao dao) {
        this.dao = dao;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void createStructureFile() throws RainWaterDaoException {
        dao.write(fileName, multiplier, points);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}

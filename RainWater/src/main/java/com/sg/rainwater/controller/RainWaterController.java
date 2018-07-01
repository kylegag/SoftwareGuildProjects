/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.controller;

import com.sg.rainwater.dao.RainWaterDaoException;
import com.sg.rainwater.service.RainWaterCreateState;
import com.sg.rainwater.service.RainWaterDisplayState;
import com.sg.rainwater.ui.RainWaterView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class RainWaterController {
    
    // MODE must be either "JSON" or "No JSON"
    // It controls wirings in the displayState and 
    // in the createState that determine whether or not
    // JSON is used to save and open structure files.
    public static final String MODE = "JSON";
    
    @Autowired
    private RainWaterDisplayState displayState;
    @Autowired
    private RainWaterCreateState createState;
    @Autowired
    private RainWaterView view;
    
    public void run() {
        boolean isRunning = true;
        
        while (isRunning) {
            switch (view.displayInitialOptions()) {
                case 1:
                    createNewStructureFile();
                    break;
                case 2:
                    displayExistingStructureFile();
                    break;
                case 3:
                    view.displayGoodByeMessage();
                    isRunning = false;
                    break;
            }
        }
    }
    
    public void createNewStructureFile() {
        createState.setFileName(view.promptForFileNameToCreate());
        
        // multiplier adjusts how many characters wide/tall each point is
        createState.setMultiplier(view.promptForMultiplier());
        
        // each point is just an x,y coordinate
        createState.setPoints(view.promptForPoints());
        try {
            createState.createStructureFile();
        } catch (RainWaterDaoException ex) {
            view.displayError(ex.getMessage());
        }
    }
    
    public void displayExistingStructureFile() {
        displayState.setFileName(view.promptForFileNameToOpen());
        try {
            displayState.loadPoints();
        } catch (RainWaterDaoException ex) {
            view.displayError(ex.getMessage());
            return;
        }
        
        displayState.setRainDirection(view.promptForDirection());
        
        // material is an array that holds the character to be used for the
        // structure and the character to be used for rain water.
        // potentialChar is a character the displayState uses to mark 
        // potentialPoints which are used to find where water will be held
        displayState.setMaterialAndPotentialChar(view.promptForChars());
        
        // Builds the structure without rain water added to it yet
        displayState.buildBareStructure();
        
        // Marks the inititial potentialPoints
        displayState.markInitialPotentialPoints();
        // Goes through the potentialPoints and adds water to those that can
        // hold water
        displayState.markWaterPoints();
        
        do {
            // Save the potentialPoints
            displayState.saveOldPotentialPoints();
            // update the potentialPoints
            displayState.markNextPotentialPoints();
            // update waterPoints
            displayState.markWaterPoints();
            // check to see if there are any new potentialPoints
        } while (displayState.areNewPotentialPoints());
        
        // clean up the potentialPoints
        displayState.cleanUpPotentialPoints();
        
        try {
            // display the structure in the displayDao
            displayState.displayStructure();
        } catch (RainWaterDaoException ex) {
            view.displayError(ex.getMessage());
            return;
        }
    }
}

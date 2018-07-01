/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.service;

import com.sg.rainwater.dto.Direction;
import static com.sg.rainwater.controller.RainWaterController.MODE;
import com.sg.rainwater.dao.RainWaterDaoException;
import com.sg.rainwater.dao.RainWaterDisplayDao;
import com.sg.rainwater.dao.RainWaterPointsDao;
import com.sg.rainwater.dto.Point;
import com.sg.rainwater.dto.StructureMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class RainWaterDisplayState {

    private RainWaterDisplayDao display;
    private RainWaterPointsDao points;

    // rainDirection is the direction the rain is coming from
    private Direction rainDirection;
    private String fileName;

    // material[0] is the character the structure is made out of
    // material[1] is the character the water is made out of 
    private char[] material;
    // A character used to find where water might be
    private char potentialChar;
    private StructureMap structure;

    private List<Point> potentialPoints;
    private List<Point> oldPotentialPoints;

    private List<Point> waterPoints = new ArrayList<>();

    private static final char EMPTY_SPACE = ' ';

    public RainWaterDisplayState(@Qualifier("Not stub") RainWaterDisplayDao display,
            @Qualifier(MODE) RainWaterPointsDao points) {
        this.display = display;
        this.points = points;
    }

    // Returns true if new potential points have been added to the StructureMap
    // Returns false otherwise
    public boolean areNewPotentialPoints() {

        Comparator<Point> comparePoints = (Point p1, Point p2) -> {
            if (p1.getX() == p2.getX()) {
                return p1.getY() - p1.getY();
            } else {
                return p1.getX() - p2.getX();
            }
        };

        Collections.sort(potentialPoints, comparePoints);
        Collections.sort(oldPotentialPoints, comparePoints);

        return !potentialPoints.equals(oldPotentialPoints);
    }

    // Saves the current potentialPoints 
    public void saveOldPotentialPoints() {
        oldPotentialPoints = new ArrayList<>(potentialPoints);
    }

    // Removes old potentialPoints and then marks new potentialPoints
    public void markNextPotentialPoints() {

        this.cleanUpPotentialPoints();

        for (Point water : waterPoints) {
            if (structure.isThisChar(water, material[1])
                    && structure.isInBounds(water.oneInThisDirection(rainDirection))
                    && structure.isThisChar(water.oneInThisDirection(rainDirection), EMPTY_SPACE)) {
                structure.setPoint(water.oneInThisDirection(rainDirection), potentialChar);
                potentialPoints.add(water.oneInThisDirection(rainDirection));
            }
        }
    }

    // Removes all potentialPoints from the StructureMap and creates a new array
    // for potentialPoints
    public void cleanUpPotentialPoints() {
        if (potentialPoints != null) {
            for (Point potential : potentialPoints) {
                if (structure.isThisChar(potential, potentialChar)) {
                    structure.setPoint(potential, EMPTY_SPACE);
                }
            }

            potentialPoints = new ArrayList<>();
        }
    }

    // If a potentialPoint has the appropriate flooring and walls, adds a 
    // row/column of water to the StructureMap at this spot
    public void markWaterPoints() {
        for (Point potential : potentialPoints) {
            if (!structure.isThisChar(potential, potentialChar)) {
                continue;
            }
            switch (rainDirection) {
                case UP:
                case DOWN:
                    if (this.isBarrierInThisDirection(Direction.LEFT, potential)
                            && this.isBarrierInThisDirection(Direction.RIGHT, potential)) {
                        this.addWater(potential);
                    }
                    break;
                case LEFT:
                case RIGHT:
                    if (this.isBarrierInThisDirection(Direction.UP, potential)
                            && this.isBarrierInThisDirection(Direction.DOWN, potential)) {
                        this.addWater(potential);
                    }
                    break;
            }
        }
    }

    // Marks very first potentialPoints of the StructureMap. One for each
    // column or row, depending on the rainDirection
    public void markInitialPotentialPoints() {
        potentialPoints = new ArrayList<>();
        switch (rainDirection) {
            case UP:
                for (int i = 0; i < structure.getWidth(); i++) {
                    for (int j = 0; j < structure.getHeight(); j++) {
                        if (structure.get(i, j) == material[0]) {
                            break;
                        }
                        if (j + 1 != structure.getHeight()
                                && structure.get(i, j + 1) == material[0]) {
                            structure.set(i, j, potentialChar);
                            potentialPoints.add(new Point(i, j));
                            break;
                        }
                    }
                }
                break;
            case DOWN:
                for (int i = 0; i < structure.getWidth(); i++) {
                    for (int j = structure.getHeight() - 1; 0 <= j; j--) {
                        if (structure.get(i, j) == material[0]) {
                            break;
                        }
                        if (j != 0
                                && structure.get(i, j - 1) == material[0]) {
                            structure.set(i, j, potentialChar);
                            potentialPoints.add(new Point(i, j));
                            break;
                        }
                    }
                }
                break;
            case LEFT:
                for (int j = 0; j < structure.getHeight(); j++) {
                    for (int i = 0; i < structure.getWidth(); i++) {
                        if (structure.get(i, j) == material[0]) {
                            break;
                        }
                        if (i + 1 != structure.getWidth()
                                && structure.get(i + 1, j) == material[0]) {
                            structure.set(i, j, potentialChar);
                            potentialPoints.add(new Point(i, j));
                            break;
                        }
                    }
                }
                break;
            case RIGHT:
                for (int j = 0; j < structure.getHeight(); j++) {
                    for (int i = structure.getWidth() - 1; 0 <= i; i--) {
                        if (structure.get(i, j) == material[0]) {
                            break;
                        }
                        if (i != 0
                                && structure.get(i - 1, j) == material[0]) {
                            structure.set(i, j, potentialChar);
                            potentialPoints.add(new Point(i, j));
                            break;
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    public void displayStructure() throws RainWaterDaoException {
        display.write(structure);
    }

    public void loadPoints() throws RainWaterDaoException {
        points.load(fileName);
    }

    public List<Point> getPoints() {
        return points.getPoints();
    }

    // set characters to be used for the structure, water and potentialPoints
    public void setMaterialAndPotentialChar(char[] mat) {
        this.material = mat;
        this.pickPotentialPointChar();
    }

    // Looks at the RainWaterPointsDao and creates the appropriate MapStructure
    // based of the points held in RainWaterPointsDao
    public void buildBareStructure() {

        List<Point> pts = points.getPoints();
        List<Point> multPts = new ArrayList<>();
        int multiplier = points.getMultiplier();
        int maxX = 0;
        int maxY = 0;

        for (Point p : pts) {
            if (p.getX() > maxX) {
                maxX = p.getX();
            }
            if (p.getY() > maxY) {
                maxY = p.getY();
            }

            for (int i = p.getX() * multiplier; i < (p.getX() + 1) * multiplier; i++) {
                for (int j = p.getY() * multiplier; j < (p.getY() + 1) * multiplier; j++) {
                    multPts.add(new Point(i, j));
                }
            }
        }

        structure = new StructureMap(multiplier * (maxX + 1),
                multiplier * (maxY + 1));

        for (Point p : multPts) {
            structure.setPoint(p, material[0]);
        }
    }

    public StructureMap getStructureMap() {
        return structure;
    }

    public Direction getRainDirection() {
        return rainDirection;
    }

    public void setRainDirection(Direction dir) {
        rainDirection = dir;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    // Adds water to the row or column that Point p is in depending on the
    // rainDirection
    private void addWater(Point p) {

        if (structure.isThisChar(p, material[1])) {
            return;
        }

        Point nextPoint = p.copy();
        boolean structureFound = false;

        while (!structureFound) {
            if (!structure.isInBounds(nextPoint)) {
                System.out.println("Programming error at Point: " + nextPoint.getX() + ", " + nextPoint.getY());
                return;
            } else if (structure.isThisChar(nextPoint, material[0])) {
                structureFound = true;
            } else {
                structure.setPoint(nextPoint, material[1]);
                waterPoints.add(nextPoint.copy());

                switch (rainDirection) {
                    case UP:
                    case DOWN:
                        nextPoint = nextPoint.oneInThisDirection(Direction.LEFT);
                        break;
                    case LEFT:
                    case RIGHT:
                        nextPoint = nextPoint.oneInThisDirection(Direction.UP);
                        break;
                }
            }
        }

        structureFound = false;
        nextPoint = p.copy();

        while (!structureFound) {
            if (!structure.isInBounds(nextPoint)) {
                System.out.println("Programming error at Point: " + nextPoint.getX() + ", " + nextPoint.getY());
                return;
            } else if (structure.isThisChar(nextPoint, material[0])) {
                structureFound = true;
            } else {
                structure.setPoint(nextPoint, material[1]);
                waterPoints.add(nextPoint.copy());

                switch (rainDirection) {
                    case UP:
                    case DOWN:
                        nextPoint = nextPoint.oneInThisDirection(Direction.RIGHT);
                        break;
                    case LEFT:
                    case RIGHT:
                        nextPoint = nextPoint.oneInThisDirection(Direction.DOWN);
                        break;
                }
            }
        }
    }

    // Looks for floors and walls in Direction toCheck. Returns whether or not
    // it found the absence of a floor first or a wall. True for wall, false 
    // for absence of floor
    private boolean isBarrierInThisDirection(Direction toCheck, Point p) {
        boolean stillLooking = true;
        Point nextCheck = p.copy();
        Point flooringPoint;

        while (stillLooking) {
            if (!structure.isInBounds(nextCheck)) {
                return false;
            } else if (structure.isThisChar(nextCheck, material[0])) {
                return true;
            } else if (structure.isThisChar(nextCheck, potentialChar)) {
                //potentialPoints.remove(nextCheck);
                //structure.setPoint(nextCheck, ' ');
            }

            flooringPoint = nextCheck.oneInThisDirection(oppositeDirection(rainDirection));
            if (!structure.isInBounds(flooringPoint)) {
                return false;
            } else if ((!structure.isThisChar(flooringPoint, material[0]))
                    && (!structure.isThisChar(flooringPoint, material[1]))) {
                stillLooking = false;
            } else {
                nextCheck = nextCheck.oneInThisDirection(toCheck);
            }
        }

        return false;
    }

    // Gives opposite direction of Direction direct
    private static Direction oppositeDirection(Direction direct) {
        switch (direct) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
        }

        return null;
    }

    // Chooses potentialChar based off of the water character and structure 
    // character to ensure they are not the same.
    private void pickPotentialPointChar() {
        if (material.length != 2) {
            return;
        }
        if (material[0] != 'p' && material[1] != 'p') {
            potentialChar = 'p';
        } else if (material[0] != 'v' && material[1] != 'v') {
            potentialChar = 'v';
        } else if (material[0] != 'q' && material[1] != 'q') {
            potentialChar = 'q';
        }
    }
}

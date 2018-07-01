/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.ui;

import com.sg.rainwater.dto.Point;
import com.sg.rainwater.dto.Direction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class RainWaterView {

    @Autowired
    private UserIO io;

    public List<Point> promptForPoints() {
        String input = "";
        List<Point> points = new ArrayList<>();
        String[] onePoint;
        int x;
        int y;

        io.print("Please start entering data points in the form 'x, y' and hit enter"
                + " at the end of each point.\nTo stop entering points just hit enter.");
        while (true) {
            input = io.readStringNoReturn("");
            if(input.isEmpty()) {
                break;
            }
            onePoint = input.replaceAll(" ", "").split(",");
            try {
                x = Integer.parseInt(onePoint[0]);
                y = Integer.parseInt(onePoint[1]);
                points.add(new Point(x,y));
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                io.print("Point is invalid.");
            }
        }

        return points;
    }

    public int promptForMultiplier() {
        return io.readInt("Please enter the multiplier you would like to use. (1-20)", 1, 20);
    }

    public int displayInitialOptions() {
        return io.readInt("\nCreate a new structure file(1)\n"
                + "Display an existing structure file(2)\n"
                + "Exit(3)\n"
                + "Please choose from one of the above options.",
                 1, 3);
    }

    public void displayGoodByeMessage() {
        io.print("Okay, see you next time.");
    }

    public boolean promptToContinue() {
        boolean valid = false;
        boolean isContinuing = false;
        String yesOrNo;
        while (!valid) {
            yesOrNo = io.readString("Would you like to continue?").toLowerCase();
            if (yesOrNo.equals("yes") || yesOrNo.equals("y")) {
                isContinuing = true;
                valid = true;
            } else if (yesOrNo.equals("no") || yesOrNo.equals("n")) {
                isContinuing = false;
                valid = true;
            } else {
                io.print(yesOrNo + " is not a valid response!");
            }
        }
        return isContinuing;
    }

    public void displayError(String msg) {
        io.print("**********Error**********");
        io.print(msg);
    }

    public String promptForFileNameToOpen() {
        return io.readString("What file would you like to open?");
    }
    
    public String promptForFileNameToCreate() {
        return io.readString("What would you like to name the file you are creating?");
    }

    public Direction promptForDirection() {
        int choice = io.readInt("Up(0)\n"
                + "Down(1)\n"
                + "Left(2)\n"
                + "Right(3)\n"
                + "Which of the above direction should the rain fall from?", 0, 3);

        switch (choice) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return null;
        }
    }

    public char[] promptForChars() {
        boolean valid = false;
        char[] format;

        format = new char[2];

        format[0] = io.readChar("What character would you like the structure to be made out of?");

        while (!valid) {
            format[1] = io.readChar("What character would you like the rain water to be made out of?");
            if (format[0] != format[1]) {
                valid = true;
            } else {
                io.print("You cannot reuse your structure character!");
            }
        }

        return format;
    }

    // Returns number between 0 and columnUpperLimit to delete a column
    // Returns number btween -rowUpperLimit and -1 to delete a column
    public int promptToDeleteRowOrColumn(int upperLimit) {
        String rowOrColumn;
        int toDelete;
        boolean valid = false;
        boolean isRow = false;

        while (!valid) {
            rowOrColumn = io.readString("Would you like to delete a (r)ow or a (c)olumn?").toLowerCase();
            if (rowOrColumn.equals("row") || rowOrColumn.equals("r")) {
                isRow = true;
                valid = true;
            } else if (rowOrColumn.equals("column") || rowOrColumn.equals("c")) {
                isRow = false;
                valid = true;
            } else {
                io.print("That was not a valid response.");
            }
        }

        toDelete = io.readInt("Which " + (isRow ? "row" : "column") + " would you like to delete?", 0, upperLimit);

        if (isRow) {
            return toDelete;
        } else {
            toDelete = -toDelete;
            toDelete--;
            return toDelete;
        }
    }
}

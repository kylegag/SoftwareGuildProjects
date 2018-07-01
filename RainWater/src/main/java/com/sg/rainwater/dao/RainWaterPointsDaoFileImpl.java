/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dao;

import com.sg.rainwater.dto.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kyle
 */
@Service("No JSON")
@Qualifier("No JSON")
public class RainWaterPointsDaoFileImpl implements RainWaterPointsDao {

    private List<Point> data;
    private int multiplier;

    @Override
    public void load(String fileName) throws RainWaterDaoException {
        if (fileName == null) {
            throw new RainWaterDaoException("File name is null!");
        }

        List<Point> replacement = new ArrayList<>();

        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new RainWaterDaoException(
                    "-_- Could not load this file!", e);
        }
        String currentLine;

        String[] currentTokens;

        try {
            multiplier = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException | NoSuchElementException ex) {
            throw new RainWaterDaoException("File format of " + fileName + " is incorrect.");
        }

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();

            currentTokens = currentLine.split(",");

            try {
                replacement.add(new Point(Integer.parseInt(currentTokens[0]),
                        Integer.parseInt(currentTokens[1])));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new RainWaterDaoException("File format of " + fileName + " is incorrect.");
            }
        }

        scanner.close();

        data = replacement;
    }

    @Override
    public void write(String fileName, int multiplier, List<Point> points)
            throws RainWaterDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException ex) {
            throw new RainWaterDaoException("Coule not create file " + fileName + "!", ex);
        }

        out.println(multiplier);

        for (Point point : points) {
            out.println(point.getX() + "," + point.getY());
        }

        out.flush();
        out.close();
    }

    @Override
    public List<Point> getPoints() {
        return data;
    }
    
    @Override
    public int getMultiplier() {
        return multiplier;
    }

}

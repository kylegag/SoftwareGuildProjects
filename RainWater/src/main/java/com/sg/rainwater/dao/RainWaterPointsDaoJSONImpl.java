/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dao;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.sg.rainwater.dto.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kyle
 */
@Service("JSON")
@Qualifier("JSON")
public class RainWaterPointsDaoJSONImpl implements RainWaterPointsDao {

    private List<Point> data;
    private int multiplier;

    @Override
    public void load(String fileName) throws RainWaterDaoException {

        BufferedReader fileReader;
        JsonReader jsonReader;

        try {
            fileReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            throw new RainWaterDaoException("File " + fileName + " not found!");
        }

        jsonReader = new JsonReader(fileReader);
        jsonReader.setLenient(true);
        Gson gson = new Gson();

        try {
            multiplier = gson.fromJson(jsonReader, int.class);

            Type typeOfT = new TypeToken<List<Point>>() {
            }.getType();
            data = gson.fromJson(jsonReader, typeOfT);
        } catch (IllegalStateException | JsonSyntaxException ex) {
            throw new RainWaterDaoException("File format of " + fileName + " is incorrect.");
        }

        try {
            fileReader.close();
            jsonReader.close();
        } catch (IOException ex) {
            throw new RainWaterDaoException("Problem closing " + fileName);
        }
    }

    @Override
    public void write(String fileName, int multiplier, List<Point> points)
            throws RainWaterDaoException {
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(fileName);
            gson.toJson(multiplier, writer);
            gson.toJson(points, writer);
            writer.close();
        } catch (IOException ex) {
            throw new RainWaterDaoException("Error writing to " + fileName);
        }

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

/**
 *
 * @author Kyle
 */
@Entity
public class Fuel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fuelId;

    private String name;
    private double fuelDuration;
    private double density;
    
    public Fuel() {
        this.fuelId = 1;
        this.name = "Jupiter Juice";
        this.fuelDuration = 0.0;
        this.density = 0.0;
    }

    public int getFuelId() {
        return fuelId;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFuelDuration() {
        return fuelDuration;
    }

    public void setFuelDuration(double fuelDuration) {
        this.fuelDuration = fuelDuration;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public String toString() {
        return "name: " + name + " fuelId: " + fuelId + " fuelDuration: " + fuelDuration + " density: " + density;
    }
}

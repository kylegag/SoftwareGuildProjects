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
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int engineId;

    private String name;
    private double thrust;
    private double mass;

    public Engine() {
        this.engineId = 1;
        this.name = "Ion";
        this.thrust = 0.0;
        this.mass = 0.0;
    }

    public Engine(int engineId, String name, double thrust, double mass) {
        this.engineId = engineId;
        this.name = name;
        this.thrust = thrust;
        this.mass = mass;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getThrust() {
        return thrust;
    }

    public void setThrust(double thrust) {
        this.thrust = thrust;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public String toString() {
        return "name: " + name + " engineId: " + engineId + " thrust: " + thrust + " mass: " + mass;
    }
}

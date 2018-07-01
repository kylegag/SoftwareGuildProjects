/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Kyle
 */
@Entity
public class Body {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bodyId;
  
    private String name;
    private double windResistance;
    private double mass;
    
    public Body() {
        this.bodyId = 1;
        this.name = "Atlas";
        this.windResistance = 0.0;
        this.mass = 0.0;
    }

    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getWindResistance() {
        return windResistance;
    }

    public void setWindResistance(double windResistance) {
        this.windResistance = windResistance;
    }
    
    public String toString() {
        return "name: " + name + " bodyId: " + bodyId + " windResistance: " + windResistance + " mass: " + mass;
    }
}

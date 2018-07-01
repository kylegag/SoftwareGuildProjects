/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.models;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.springframework.format.annotation.DateTimeFormat;
/**
 *
 * @author Kyle
 */
@Entity
public class Launch implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int launchId;
    
    @DecimalMax("90.0")
    @DecimalMin("0.0")
    private double angle;
    
    @NotNull(message = "Date is required.")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate date;
    
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "location")
    private Location location;
    
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "rocket")
    private Rocket rocket;
    
    //@DecimalMin("0.0")
    private double distance;
    
    public Launch() {
        this.rocket = new Rocket();
        this.angle = 0.0;
        this.date = null;
        this.location = new Location();
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getLaunchId() {
        return launchId;
    }

    public void setLaunchId(int launchId) {
        this.launchId = launchId;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }
    
    public String toString() {
        return "engineId: " + rocket.getEngine().getEngineId() + "\n"
             + "bodyId: " + rocket.getBody().getBodyId() + "\n"
             + "fuelId: " + rocket.getFuel().getFuelId() + "\n"
             + "rocket Name: " + rocket.getName() + "\n"
             + "locationId: " + location.getLocationId() + "\n"
             + "date: " + date + "\n"
             + "angle: " + angle;
    }
}

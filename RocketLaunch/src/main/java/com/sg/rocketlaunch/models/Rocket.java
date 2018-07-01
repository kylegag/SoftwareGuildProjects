/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.models;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kyle
 */
@Entity
public class Rocket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rocketId;
    
    @NotNull(message = "Name is required.")
    @Size(min = 1, message = "Name is required.")
    //@NotEmpty(message = "Name is required.")
    private String name;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "engine")
    private Engine engine;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "body")
    private Body body;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "fuel")
    private Fuel fuel;
    
    public Rocket() {
        this.name = "";
        this.engine = new Engine();
        this.body = new Body();
        this.fuel = new Fuel();
    }

    public int getRocketId() {
        return rocketId;
    }

    public void setRocketId(int rocketId) {
        this.rocketId = rocketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public String toString() {
        return "name: " + name + "\nrocketId:" + rocketId + "\n" + body + "\n" + engine + "\n" + fuel;
    }
}

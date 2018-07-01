/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.models;

import java.lang.Math;

/**
 *
 * @author Kyle
 */
public class Vector {
    
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public static Vector PolarCoordinates(double absoluteValue, double angle) {
        return (new Vector(Math.cos(angle * (Math.PI/180.0)), Math.sin(angle * (Math.PI/180.0)))).multiply(absoluteValue);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    } 
    
    public Vector multiply(double m) {
        return new Vector(this.getX() * m, this.getY() * m);
    }
    
    public Vector divide(double m) {
        return new Vector(this.getX() / m, this.getY() / m);
    }
    
    public static Vector add(Vector v1, Vector v2) {
        return new Vector(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }
    
    // subtract second from first
    public static Vector subtract(Vector v1, Vector v2) {
        return new Vector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }
    
    public Vector getUnitVector() {
        double absoluteValue = Math.sqrt((this.x * this.x) + (this.y * this.y));
        
        return new Vector(x/absoluteValue, y/absoluteValue);
    }
    
    public double getAbsoluteValue() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }
    
    public static Point getEndPoint(Point start, Vector travel) {
        return new Point(start.getX() + travel.getX(), start.getY() + travel.getY());
    }
    
    public Vector copy() {
        return new Vector(this.x, this.y);
    }
    
    public double getSlope() {
        return this.y/this.x;
    }
    
    public String toString() {
        return "VECTOR- X: " + x + " Y: " + y + " absolute value: " + getAbsoluteValue();
    }
}

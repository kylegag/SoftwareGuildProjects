/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class StructureMap {

    private List<ArrayList<Character>> map;
    private int width;
    private int height;

    public StructureMap(int width, int height) {

        this.width = width;
        this.height = height;

        map = new ArrayList<>();

        ArrayList<Character> buf;

        for (int i = 0; i < width; i++) {
            buf = new ArrayList<>();

            for (int j = 0; j < height; j++) {
                buf.add(' ');
            }
            map.add(buf);
        }
    }
    
    public boolean isThisChar(Point p, char c) {
        return this.isThisChar(p.getX(), p.getY(), c);
    }
    

    public boolean isThisChar(int x, int y, char c) {
        if (this.get(x, y) == c) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInBounds(Point p) {
        return this.isInBounds(p.getX(), p.getY());
    }

    public boolean isInBounds(int x, int y) {
        if (0 <= x && x < width
                && 0 <= y && y < height) {
            return true;
        } else {
            return false;
        }
    }
    
    public char get(Point p) {
        return this.get(p.getX(), p.getY());
    }

    public char get(int x, int y) {
        return map.get(x).get(y);
    }

    public void set(int x, int y, char val) {
        map.get(x).set(y, val);
    }

    public void setPoint(Point p, char val) {
        this.set(p.getX(), p.getY(), val);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void forDebugging(int x, int y) {
        if (width <= x) {
            System.out.println("Error caught. width: " + width + " x: " + x);
        }
        if (height <= y) {
            System.out.println("Error caught. height: " + height + " y: " + y);
        }
    }
}

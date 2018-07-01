/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dto;

import static com.sg.rainwater.dto.Direction.DOWN;
import static com.sg.rainwater.dto.Direction.LEFT;
import static com.sg.rainwater.dto.Direction.RIGHT;
import static com.sg.rainwater.dto.Direction.UP;

/**
 *
 * @author Kyle
 */
public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Point copy() {
        return new Point(this.getX(), this.getY());
    }
    
    public Point oneInThisDirection(Direction direct) {
        switch(direct) {
            case UP:
                return this.oneUp();
            case DOWN:
                return this.oneDown();
            case LEFT:
                return this.oneToTheLeft();
            case RIGHT:
                return this.oneToTheRight();
        }
        
        return null;
    }

    public Point oneUp() {
        return new Point(this.getX(), this.getY() - 1);
    }

    public Point oneDown() {
        return new Point(this.getX(), this.getY() + 1);
    }

    public Point oneToTheRight() {
        return new Point(this.getX() + 1, this.getY());
    }

    public Point oneToTheLeft() {
        return new Point(this.getX() - 1, this.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    /*
    public boolean argIsImmediatelyAboveThis(Point p, Direction direct) {
        int theirX = p.getX();
        int theirY = p.getY();

        switch (direct) {
            case UP:
                if (this.x == theirX && this.y + 1 == theirY) {
                    return true;
                } else {
                    return false;
                }
            case DOWN:
                if (this.x == theirX && this.y - 1 == theirY) {
                    return true;
                } else {
                    return false;
                }
            case LEFT:
                if (this.x - 1 == theirX && this.y == theirY) {
                    return true;
                } else {
                    return false;
                }
            case RIGHT:
                if (this.x + 1 == theirX && this.y == theirY) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "x: "+this.x+" y: "+this.y;
    }
}

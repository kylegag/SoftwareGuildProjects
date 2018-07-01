/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.powerball.models;

import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Kyle
 */
public class Pick {

    private int pickId;

    @NotNull(message = "First name is required.")
    @NotEmpty(message = "First name is required.")
    private String firstName;

    @NotNull(message = "Last name is required.")
    @NotEmpty(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Email is required.")
    @NotEmpty(message = "Email is required.")
    private String email;

    @NotNull(message = "State is required.")
    @NotEmpty(message = "State is required.")
    private String state;

    @Max(69)
    @Min(1)
    private int firstNum;
    
    private int secondNum;
    
    private int thirdNum;
    
    private int fourthNum;
    
    private int fifthNum;
    private int powerBall;
    private boolean isQuickPick;
    private boolean isValid;
    private Date pickDate;

    public Pick() {
        pickId = -1;
        firstName = "";
        lastName = "";
        email = "";
        state = "";
        firstNum = -1;
        secondNum = -1;
        thirdNum = -1;
        fourthNum = -1;
        fifthNum = -1;
        powerBall = -1;
        isQuickPick = false;
        isValid = true;
        pickDate = new Date();
    }

    public int getPickId() {
        return pickId;
    }

    public void setPickId(int pickId) {
        this.pickId = pickId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }

    public int getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(int secondNum) {
        this.secondNum = secondNum;
    }

    public int getThirdNum() {
        return thirdNum;
    }

    public void setThirdNum(int thirdNum) {
        this.thirdNum = thirdNum;
    }

    public int getFourthNum() {
        return fourthNum;
    }

    public void setFourthNum(int fourthNum) {
        this.fourthNum = fourthNum;
    }

    public int getFifthNum() {
        return fifthNum;
    }

    public void setFifthNum(int fifthNum) {
        this.fifthNum = fifthNum;
    }

    public int getPowerBall() {
        return powerBall;
    }

    public void setPowerBall(int powerBall) {
        this.powerBall = powerBall;
    }

    public boolean isIsQuickPick() {
        return isQuickPick;
    }

    public void setIsQuickPick(boolean isQuickPick) {
        this.isQuickPick = isQuickPick;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public Date getPickDate() {
        return pickDate;
    }

    public void setPickDate(Date pickDate) {
        this.pickDate = pickDate;
    }
}

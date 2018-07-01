/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.ui;

import java.math.BigDecimal;

/**
 *
 * @author Kyle
 */
public interface UserIO {
    
    public char readChar(String prompt);

    public BigDecimal readBigDecimalAsString(String prompt);

    public BigDecimal readBigDecimalAsString(String prompt, String min, String max);

    void print(String msg);
    
    void printNoReturn(String msg);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    //float readFloat(String prompt);
    //float readFloat(String prompt, float min, float max);
    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    //long readLong(String prompt);
    //long readLong(String prompt, long min, long max);
    String readString(String prompt);
    
    String readStringNoReturn(String prompt);
}

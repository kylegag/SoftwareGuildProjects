/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.ui;

import java.math.BigDecimal;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class UserIOConsoleImpl implements UserIO {

    private Scanner input = new Scanner(System.in);

    public char readChar(String prompt) {
        boolean valid = false;
        String str = null;

        while (!valid) {
            str = readString(prompt);
            if (str.length() == 1) {
                valid = true;
            } else {
                System.out.println("That is not a single character!");
            }
        }

        return str.charAt(0);
    }

    public BigDecimal readBigDecimalAsString(String prompt, String min, String max) {
        boolean valid = true;
        BigDecimal result;
        BigDecimal minBD = null;
        BigDecimal maxBD = null;
        if (stringIsValid(min)) {
            minBD = new BigDecimal(min);
        }
        if (stringIsValid(max)) {
            maxBD = new BigDecimal(max);
        }
        do {
            valid = true;

            result = readBigDecimalAsString(prompt);

            if (minBD != null && result.compareTo(minBD) < 0) {
                System.out.println("Value must be above " + minBD + ".");
                valid = false;
                continue;
            }
            if (maxBD != null && result.compareTo(maxBD) > 0) {
                System.out.println("Value must be below " + maxBD + ".");
                valid = false;
                continue;
            }

        } while (!valid);
        return result;
    }

    public BigDecimal readBigDecimalAsString(String prompt) {
        boolean valid = false;
        BigDecimal result = new BigDecimal("0");
        do {
            String value = null;
            try {
                value = readString(prompt);
                result = new BigDecimal(value);
                valid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("The value '%s' is not a number.\n", value);
            }
        } while (!valid);
        return result;
    }

    public String readString(String prompt) {
        System.out.println(prompt);
        return input.nextLine();
    }

    public String readStringNoReturn(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    public void print(String str) {
        System.out.println(str);
    }

    public void printNoReturn(String str) {
        System.out.print(str);
    }

    public double readDouble(String prompt) {
        boolean valid = false;
        double result = 0;
        do {
            String value = null;
            try {
                value = readString(prompt);
                result = Double.parseDouble(value);
                valid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("The value '%s' is not a number.\n", value);
            }
        } while (!valid);
        return result;
    }

    public double readDouble(String prompt, double min, double max) {
        boolean valid = false;
        double result = 0;
        do {
            result = readDouble(prompt);
            if (result >= min && result <= max) {
                valid = true;
            } else {
                System.out.printf("The value must be between %d and %d.", min, max);
            }
        } while (!valid);
        return result;
    }

    public int readInt(String prompt) {
        boolean valid = false;
        int result = 0;
        do {
            String value = null;
            try {
                value = readString(prompt);
                result = Integer.parseInt(value);
                valid = true;
            } catch (NumberFormatException ex) {
                System.out.printf("The value '%s' is not a number.\n", value);
            }
        } while (!valid);
        return result;
    }

    public int readInt(String prompt, int min, int max) {
        boolean valid = false;
        int result = 0;
        do {
            result = readInt(prompt);
            if (result >= min && result <= max) {
                valid = true;
            } else {
                System.out.printf("The value must be between %d and %d.", min, max);
            }
        } while (!valid);
        return result;
    }

    public boolean stringIsValid(String str) {
        return str != null && (!str.equals(""));
    }
}

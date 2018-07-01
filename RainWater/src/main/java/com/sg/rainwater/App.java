/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater;

import com.sg.rainwater.controller.RainWaterController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Kyle
 */
@Configuration
@ComponentScan
public class App {

    public static void main(String[] args) {
        ApplicationContext cxt = new AnnotationConfigApplicationContext(App.class);
        RainWaterController app;
        app = cxt.getBean(RainWaterController.class);
        app.run();
    }

    /*
    Test files:
    
Six buckets:
    
1,1
1,2
1,3
1,4
1,6
1,7
1,8
1,9
2,4
3,4
5,4
6,4
7,4
8,4
9,4
10,4
11,4
12,4
13,4
14,4
15,4
16,4
2,9
3,9
4,9
5,9
6,9
7,9
8,9
9,9
10,9
11,9
12,9
13,9
14,9
15,9
16,9    
6,1
6,2
6,3
6,5
6,6
6,7
6,8   
11,1
11,2
11,3
11,5
11,6
11,7
11,8
16,1
16,2
16,3
16,4
16,6
16,7
16,8
16,9 
    


    Short comings:

    0,5
    1,5
    2,5
    3,5
    4,5
    5,5
    6,5
    7,5
    8,5
    9,5
    10,5
    0,4
    6,0
    6,1
    6,2
    6,3
    6,4
    3,0
    3,1
    3,2
    3,3
    4,0
    5,0
    7,0
    8,0
    9,0
    10,4
    11,4
    11,3
    12,3
    12,2
    13,2
    13,1
    
    TicTacToe:
    
    1,0
    1,1
    1,2
    1,3
    1,4
    3,0
    3,1
    3,2
    3,3
    3,4
    0,1
    2,1
    4,1
    0,3
    2,3
    4,3

    
    
    
    
    */
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import java.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Kyle
 */
@SpringBootApplication
//@EnableTransactionManagement
public class App {

    public static final DateTimeFormatter DateFormatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        /*
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        
        VendingMachineDao dao = new VendingMachineJdbcTemplate();
        //VendingMachineDao dao = new VendingMachineDaoFileImpl();
        VendingMachineState state = new VendingMachineState(dao);
        
        VendingMachineController app = new VendingMachineController(state, view);
        app.run();
         */

        /*
        ApplicationContext cxt = new AnnotationConfigApplicationContext(App.class);
        VendingMachineState state;
        state = cxt.getBean(VendingMachineState.class);
        
        
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineController app = new VendingMachineController(state, view);
        app.run();
         */
        
        SpringApplication.run(App.class, args);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.controllers;

import com.sg.rocketlaunch.models.Body;
import com.sg.rocketlaunch.models.Engine;
import com.sg.rocketlaunch.models.Fuel;
import com.sg.rocketlaunch.models.Launch;
import com.sg.rocketlaunch.models.Location;
import com.sg.rocketlaunch.models.Point;
import com.sg.rocketlaunch.service.RocketLaunchService;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Kyle
 */
@Controller
public class RocketLaunchController {

    @Autowired
    RocketLaunchService state;

    @GetMapping("/")
    public String getHome(Model model) {
        return "Home";
    }
    
    @CrossOrigin
    @ResponseBody
    @GetMapping("/points")
    public List<Point> getPoints() {
        return state.getPoints();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/background")
    public String getBackground() {
        Launch currLaunch = state.getCurrentLaunch();
        String name = currLaunch.getLocation().getUrl();
        return "url(\"../css/images/" + name + "\")";
    }

    @PostMapping("/Home")
    public String getDifferentPage(Model model, String action) {
        if (action.equals("AddNewLaunch")) {

            List<Engine> engines = state.getAllEngines();
            model.addAttribute("engines", engines);

            List<Body> bodies = state.getAllBodies();
            model.addAttribute("bodies", bodies);

            List<Fuel> fuels = state.getAllFuels();
            model.addAttribute("fuels", fuels);

            List<Location> locations = state.getAllLocations();
            model.addAttribute("locations", locations);

            Launch launch = new Launch();
            launch.getRocket().setBody(state.getBody(1));
            launch.getRocket().setEngine(state.getEngine(1));
            launch.getRocket().setFuel(state.getFuel(1));
            launch.setLocation(state.getLocation(1));

            model.addAttribute("launch", launch);

        } else if (action.equals("SearchPastLaunches")) {

            List<Engine> engines = state.getAllEngines();
            model.addAttribute("engines", engines);

            List<Body> bodies = state.getAllBodies();
            model.addAttribute("bodies", bodies);

            List<Fuel> fuels = state.getAllFuels();
            model.addAttribute("fuels", fuels);

            List<Location> locations = state.getAllLocations();
            model.addAttribute("locations", locations);

            Launch launch = new Launch();
            launch.getRocket().setBody(state.getBody(1));
            launch.getRocket().setEngine(state.getEngine(1));
            launch.getRocket().setFuel(state.getFuel(1));
            launch.setLocation(state.getLocation(1));

            model.addAttribute("launch", launch);

        } else if (action.equals("HighScores")) {

            List<Launch> results = state.getHighScores();

            model.addAttribute("results", results);

        }

        return action;
    }

    @PostMapping("/SearchLaunch")
    public String getSearchLaunch(@Valid Launch launch, BindingResult result, Model model, String action) {

        if (action.equals("submit")) {
            List<Engine> engines = state.getAllEngines();
            model.addAttribute("engines", engines);

            List<Body> bodies = state.getAllBodies();
            model.addAttribute("bodies", bodies);

            List<Fuel> fuels = state.getAllFuels();
            model.addAttribute("fuels", fuels);

            List<Location> locations = state.getAllLocations();
            model.addAttribute("locations", locations);

            model.addAttribute("launch", launch);

            if (launch.getDate() == null) {
                launch.setDate(LocalDate.parse("0001-01-01"));
            }

            List<Launch> results = state.searchLaunch(launch);

            model.addAttribute("results", results);

            return "SearchPastLaunchesTwo";
        } else {
            Launch toPlay = state.getLaunch(Integer.parseInt(action));
            state.setCurrentLaunch(toPlay);
            model.addAttribute("launch", toPlay);
            return "PlayRocketLaunch";
        }
    }

    @PostMapping("/PlayLaunch")
    public String playLaunch(Model model, String action) {
        Launch launch = state.getLaunch(Integer.parseInt(action));
        state.setCurrentLaunch(launch);
        model.addAttribute("launch", launch);
        return "PlayRocketLaunch";
    }

    @PostMapping("/SubmitNewLaunch")
    public String getSubmitNewLaunch(@Valid Launch launch, BindingResult result, Model model, String action) {

        // Presentation
        // The rocket object was not validated so I did it myself
        if (launch.getRocket().getName().isEmpty()) {
            result.addError(new FieldError("launch", "rocket.name", "Rocket name is required."));
        }

        if (!result.hasErrors()) {
            launch.getRocket().setEngine(state.getEngine(launch.getRocket().getEngine().getEngineId()));
            launch.getRocket().setBody(state.getBody(launch.getRocket().getBody().getBodyId()));
            launch.getRocket().setFuel(state.getFuel(launch.getRocket().getFuel().getFuelId()));
            launch.setLocation(state.getLocation(launch.getLocation().getLocationId()));

            state.setCurrentLaunch(launch);
            state.getPoints(); // This calculates the distance for currentLaunch
            
            state.saveLaunch(launch);
            model.addAttribute("launch", launch);


            return "PlayRocketLaunch";
        } else {
            model.addAttribute("launch");

            List<Engine> engines = state.getAllEngines();
            model.addAttribute("engines", engines);

            List<Body> bodies = state.getAllBodies();
            model.addAttribute("bodies", bodies);

            List<Fuel> fuels = state.getAllFuels();
            model.addAttribute("fuels", fuels);

            List<Location> locations = state.getAllLocations();
            model.addAttribute("locations", locations);

            return "AddNewLaunch";
        }
    }
}

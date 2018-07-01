/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.service;

import com.sg.rocketlaunch.data.BodyRepository;
import com.sg.rocketlaunch.data.EngineRepository;
import com.sg.rocketlaunch.data.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sg.rocketlaunch.data.LaunchRepository;
import com.sg.rocketlaunch.data.LocationRepository;
import com.sg.rocketlaunch.models.Body;
import com.sg.rocketlaunch.models.Engine;
import com.sg.rocketlaunch.models.Fuel;
import com.sg.rocketlaunch.models.Launch;
import com.sg.rocketlaunch.models.Location;
import com.sg.rocketlaunch.models.Point;
import com.sg.rocketlaunch.models.Rocket;
import com.sg.rocketlaunch.models.Vector;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author Kyle
 */
@Service
public class RocketLaunchService {

    private Launch currentLaunch;

    @Autowired
    private LaunchRepository launchRepo;

    @Autowired
    private FuelRepository fuelRepo;

    @Autowired
    private EngineRepository engineRepo;

    @Autowired
    private BodyRepository bodyRepo;

    @Autowired
    private LocationRepository locationRepo;

    public List<Point> getPoints() {

        Rocket r = currentLaunch.getRocket();

        boolean hasLanded = false;

        final double gravityAcceleration = 9.8;
        final double fuelDensity = r.getFuel().getDensity();
        final double fuelDuration = r.getFuel().getFuelDuration();
        final double startingAngle = currentLaunch.getAngle();
        final double bodyMass = r.getBody().getMass();
        final double windResistanceCoefficient = r.getBody().getWindResistance();
        final double thrustForce = r.getEngine().getThrust();
        final double engineMass = r.getEngine().getMass();

        double underGroundX;
        double underGroundY;

        // Total vectors
        Vector prevTotalVelocity;
        Vector totalVelocityWithoutWindResistance;
        Vector totalVelocity;
        Vector totalAccelerationWithoutWindResistance;
        Vector totalAcceleration;
        Vector totalForceWithoutWindResistance;

        // Gravity vectors
        Vector gravityForceVector;

        // Thrust vectors
        Vector thrustForceVector;

        final double deltaTime = .1;
        final double initialFuelVolume = 20.0;                                 
        final double initialFuelMass = initialFuelVolume * fuelDensity;  
        double fuelVolumeLeft = initialFuelVolume;                       
        double fuelMassLeft = fuelVolumeLeft * fuelDensity;               
        final double massBurnRate = initialFuelMass / fuelDuration;      
        final double fuelMassLostPerDeltaTime = deltaTime * massBurnRate;
        double totalMass;
        double prevAngle;

        Point prevPoint;
        Point nextPoint;

        ArrayList<Point> points = new ArrayList<>();

        points.add(new Point(0, 0));
        prevTotalVelocity = new Vector(0, 0);
        prevAngle = startingAngle;
        prevPoint = points.get(0);

        while (!hasLanded) {
            System.out.println("\n");

            totalMass = bodyMass + engineMass + fuelMassLeft;
            gravityForceVector = new Vector(0, -totalMass * gravityAcceleration);

            if (fuelMassLeft > 0) {
                thrustForceVector = Vector.PolarCoordinates(thrustForce, prevAngle);
            } else {
                thrustForceVector = new Vector(0, 0);
            }

            totalForceWithoutWindResistance = Vector.add(gravityForceVector, thrustForceVector);
            totalAccelerationWithoutWindResistance = totalForceWithoutWindResistance.divide(totalMass);

            totalAcceleration = totalAccelerationWithoutWindResistance.multiply(deltaTime);
            totalVelocityWithoutWindResistance = Vector.add(prevTotalVelocity, totalAcceleration);
            totalVelocity = totalVelocityWithoutWindResistance.multiply(windResistanceCoefficient);

            nextPoint = Vector.getEndPoint(prevPoint, totalVelocity);

            if (0 > nextPoint.getY()) {
                underGroundX = nextPoint.getX();
                underGroundY = nextPoint.getY();
                nextPoint.setY(0);
                nextPoint.setX(underGroundX - (underGroundY / totalVelocity.getSlope()));
                hasLanded = true;
            }

            points.add(nextPoint.copy());
            System.out.println(nextPoint);
            prevAngle = Math.atan((nextPoint.getY() - prevPoint.getY()) / (nextPoint.getX() - prevPoint.getX())) * (180 / Math.PI);
            prevPoint = nextPoint.copy();
            prevTotalVelocity = totalVelocity.copy();
            fuelMassLeft -= fuelMassLostPerDeltaTime;
        }

        currentLaunch.setDistance(points.get(points.size() - 1).getX());

        return points;
    }

    public void setCurrentLaunch(Launch launch) {
        this.currentLaunch = launch;
    }

    public Launch getCurrentLaunch() {
        return currentLaunch;
    }

    public List<Engine> getAllEngines() {
        return engineRepo.findAll();
    }

    public List<Body> getAllBodies() {
        return bodyRepo.findAll();
    }

    public List<Fuel> getAllFuels() {
        return fuelRepo.findAll();
    }

    public List<Location> getAllLocations() {
        return locationRepo.findAll();
    }

    public Launch getLaunch(int id) {
        List<Launch> launches = launchRepo.getByLaunchId(id);
        if (launches != null && launches.size() == 1) {
            return launchRepo.getByLaunchId(id).get(0);
        } else {
            return null;
        }
    }

    public Engine getEngine(int id) {
        List<Engine> engines = engineRepo.getByEngineId(id);
        if (engines != null && engines.size() == 1) {
            return engineRepo.getByEngineId(id).get(0);
        } else {
            return null;
        }
    }

    public Body getBody(int id) {
        List<Body> bodies = bodyRepo.getByBodyId(id);
        if (bodies != null && bodies.size() == 1) {
            return bodyRepo.getByBodyId(id).get(0);
        } else {
            return null;
        }
    }

    public Fuel getFuel(int id) {
        List<Fuel> fuels = fuelRepo.getByFuelId(id);
        if (fuels != null && fuels.size() == 1) {
            return fuelRepo.getByFuelId(id).get(0);
        } else {
            return null;
        }
    }

    public Location getLocation(int id) {
        List<Location> locations = locationRepo.getByLocationId(id);
        if (locations != null && locations.size() == 1) {
            return locationRepo.getByLocationId(id).get(0);
        } else {
            return null;
        }
    }

    @Transactional
    public void saveLaunch(Launch launch) {

        launch.getRocket().setEngine(getEngine(launch.getRocket().getEngine().getEngineId()));
        launch.getRocket().setBody(getBody(launch.getRocket().getBody().getBodyId()));
        launch.getRocket().setFuel(getFuel(launch.getRocket().getFuel().getFuelId()));
        launch.setLocation(getLocation(launch.getLocation().getLocationId()));
        launchRepo.save(launch);
    }

    @Transactional
    public void deleteLaunch(Launch launch) {
        int id = launch.getLaunchId();
        Launch toRemove = getLaunch(id);
        if (toRemove == null) {
            return;
        } else {
            launchRepo.delete(toRemove);
        }
    }

    public List<Launch> searchLaunch(Launch launch) {
        return launchRepo.find(launch.getRocket().getName(),
                launch.getAngle(),
                launch.getDate(),
                launch.getLocation().getLocationId(),
                launch.getRocket().getBody().getBodyId(),
                launch.getRocket().getEngine().getEngineId(),
                launch.getRocket().getFuel().getFuelId(),
                launch.getDistance());
    }

    public List<Launch> getAllLaunches() {
        return launchRepo.findAll();
    }

    public List<Launch> getHighScores() {
        return launchRepo.findTop10AllByOrderByDistanceDesc();
    }
}

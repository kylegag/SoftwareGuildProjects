/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.service;

import com.sg.rocketlaunch.models.Launch;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;

/**
 *
 * @author Kyle
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketLaunchServiceTest {

    @Autowired
    RocketLaunchService state;

    public RocketLaunchServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllEngines method, of class RocketLaunchService.
     */
    @Test
    public void testGetAllEngines() {
        assertEquals(new Integer(3), new Integer(state.getAllEngines().size()));
    }

    /**
     * Test of getAllBodies method, of class RocketLaunchService.
     */
    @Test
    public void testGetAllBodies() {
        assertEquals(new Integer(3), new Integer(state.getAllBodies().size()));
    }

    /**
     * Test of getAllFuels method, of class RocketLaunchService.
     */
    @Test
    public void testGetAllFuels() {
        assertEquals(new Integer(3), new Integer(state.getAllFuels().size()));
    }

    /**
     * Test of getAllLocations method, of class RocketLaunchService.
     */
    @Test
    public void testGetAllLocations() {
        assertEquals(new Integer(3), new Integer(state.getAllLocations().size()));
    }

    /**
     * Test of getEngine method, of class RocketLaunchService.
     */
    @Test
    public void testGetEngine() {
        assertEquals(new Integer(1), new Integer(state.getEngine(1).getEngineId()));
    }

    /**
     * Test of getBody method, of class RocketLaunchService.
     */
    @Test
    public void testGetBody() {
        assertEquals(new Integer(1), new Integer(state.getBody(1).getBodyId()));
    }

    /**
     * Test of getFuel method, of class RocketLaunchService.
     */
    @Test
    public void testGetFuel() {
        assertEquals(new Integer(1), new Integer(state.getFuel(1).getFuelId()));
    }

    /**
     * Test of getLocation method, of class RocketLaunchService.
     */
    @Test
    public void testGetLocation() {
        assertEquals(new Integer(1), new Integer(state.getLocation(1).getLocationId()));
    }

    /**
     * Test of saveLaunch method, of class RocketLaunchService.
     */
    @Test
    public void testSaveLaunch() {
        Launch toSave = new Launch();
        toSave.getRocket().setName("name");
        toSave.setDate(LocalDate.parse("1111-11-11"));

        int beforeSize = state.getAllLaunches().size();
        state.saveLaunch(toSave);
        int afterSize = state.getAllLaunches().size();

        state.deleteLaunch(toSave);

        assertEquals(new Integer(beforeSize + 1), new Integer(afterSize));
    }

    // Only use if database was previously empty
    @Test
    public void testSearchAndAddLaunch() {

        Launch otherLaunch = new Launch();
        otherLaunch.getRocket().setName("##########");
        otherLaunch.setDistance(50.0);
        otherLaunch.setAngle(90.0);
        otherLaunch.setDate(LocalDate.parse("1111-11-11"));
        state.saveLaunch(otherLaunch);

        List<Launch> results;
        Launch launch;

        // Test search name
        launch = getLaunchWithSameFields(otherLaunch);
        launch.getRocket().setName("!?qwerty");
        state.saveLaunch(launch);
        launch.getRocket().setName("!?qwe");
        results = state.searchLaunch(launch);
        assertEquals(1, results.size());
        state.deleteLaunch(launch);

        // Test search angle
        launch = getLaunchWithSameFields(otherLaunch);
        launch.setAngle(7.8);
        state.saveLaunch(launch);
        results = state.searchLaunch(launch);
        assertEquals(1, results.size());
        state.deleteLaunch(launch);

        // Test search angle when Angle = 0.00
        launch = getLaunchWithSameFields(otherLaunch);
        launch.setAngle(10.0);
        state.saveLaunch(launch);
        launch.setAngle(0.0);
        results = state.searchLaunch(launch);
        assertEquals(2, results.size());
        state.deleteLaunch(launch);

        // Test search date when date = '0001-01-01'
        launch = getLaunchWithSameFields(otherLaunch);
        launch.setDate(LocalDate.parse("2222-12-12"));
        state.saveLaunch(launch);
        launch.setDate(LocalDate.parse("0001-01-01"));
        results = state.searchLaunch(launch);
        assertEquals(2, results.size());
        state.deleteLaunch(launch);

        // Test search distance when distance = 0.0
        launch = getLaunchWithSameFields(otherLaunch);
        launch.setDistance(otherLaunch.getDistance() + 10.0);
        state.saveLaunch(launch);
        launch.setDistance(0.0);
        results = state.searchLaunch(launch);
        assertEquals(2, results.size());
        state.deleteLaunch(launch);

        // Test search location when location = -1
        launch = getLaunchWithSameFields(otherLaunch);
        launch.getLocation().setLocationId(2);
        state.saveLaunch(launch);
        launch.getLocation().setLocationId(-1);
        results = state.searchLaunch(launch);
        assertEquals(2, results.size());
        state.deleteLaunch(launch);

        // Test search engine when engine = -1
        launch = getLaunchWithSameFields(otherLaunch);
        launch.getRocket().getEngine().setEngineId(2);
        state.saveLaunch(launch);
        launch.getRocket().getEngine().setEngineId(-1);
        results = state.searchLaunch(launch);
        assertEquals(2, results.size());
        state.deleteLaunch(launch);
        
        // Test search body when body = -1
        launch = getLaunchWithSameFields(otherLaunch);
        launch.getRocket().getBody().setBodyId(2);
        state.saveLaunch(launch);
        launch.getRocket().getBody().setBodyId(-1);
        results = state.searchLaunch(launch);
        assertEquals(2, results.size());
        state.deleteLaunch(launch);
        
        // Test search fuel when fuel = -1
        launch = getLaunchWithSameFields(otherLaunch);
        launch.getRocket().getFuel().setFuelId(2);
        state.saveLaunch(launch);
        launch.getRocket().getFuel().setFuelId(-1);
        results = state.searchLaunch(launch);
        assertEquals(2, results.size());
        state.deleteLaunch(launch);

        state.deleteLaunch(otherLaunch);
    }

    private Launch getLaunchWithSameFields(Launch otherLaunch) {
        Launch launch = new Launch();
        launch.setDate(otherLaunch.getDate());
        launch.setDistance(otherLaunch.getDistance());
        launch.getRocket().setName(otherLaunch.getRocket().getName());
        launch.setAngle(otherLaunch.getAngle());
        return launch;
    }
}

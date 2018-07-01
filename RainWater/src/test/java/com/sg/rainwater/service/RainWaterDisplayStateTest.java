/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.service;

import com.sg.rainwater.dao.RainWaterDaoException;
import com.sg.rainwater.dao.RainWaterDisplayDao;
import com.sg.rainwater.dao.RainWaterDisplayDaoStub;
import com.sg.rainwater.dao.RainWaterPointsDao;
import com.sg.rainwater.dao.RainWaterPointsStub;
import com.sg.rainwater.dto.Direction;
import com.sg.rainwater.dto.Point;
import com.sg.rainwater.dto.StructureMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * Didn't design the RainWaterDisplayState with unit tests in mind. It wasn't
 * possible to test most of the methods without using other methods. I could
 * have added methods to the DisplayState to make the tests less coupled but
 * ran out of time.
 *
 * @author Kyle
 */
public class RainWaterDisplayStateTest {

    private RainWaterDisplayDao display;
    private RainWaterPointsDao points;
    private RainWaterDisplayState state;

    private final char BLANK_SPACE = ' ';

    // Ensures that the private method pickPotentialPointChar() in 
    // RainWaterDisplayState will choose 'p' to be the potential char.
    private final char[] material = {'a', 'b'};

    private final char potentialChar = 'p';

    public RainWaterDisplayStateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        display = new RainWaterDisplayDaoStub();
        points = new RainWaterPointsStub();
        state = new RainWaterDisplayState(display, points);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getRainDirection and setRainDirection
     */
    @Test
    public void testSetGetRainDirection() {
        assertEquals(null, state.getRainDirection());

        state.setRainDirection(Direction.UP);
        assertEquals(Direction.UP, state.getRainDirection());

        state.setRainDirection(Direction.RIGHT);
        assertEquals(Direction.RIGHT, state.getRainDirection());

        state.setRainDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, state.getRainDirection());

        state.setRainDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, state.getRainDirection());
    }

    /**
     * Test of setFileName and getFileName
     *
     */
    @Test
    public void testSetGetFileName() {
        assertEquals(null, state.getFileName());

        state.setFileName("test");

        assertEquals("test", state.getFileName());
    }

    /**
     * Test of loadPoints and getPoints
     *
     * Depends on setFileName
     */
    @Test
    public void testLoadPointsGetPoints() {
        List<Point> pts;
        int total = 0;
        boolean passed = false;
        try {
            state.setFileName("nonExistent.txt");
            state.loadPoints();
        } catch (RainWaterDaoException ex) {
            passed = true;
        }
        assertTrue(passed);

        try {
            state.setFileName("trueDiagnol.txt");
            state.loadPoints();
        } catch (RainWaterDaoException ex) {
            fail();
        }

        pts = state.getPoints();

        for (Point toCheck : pts) {
            if (toCheck.getX() == toCheck.getY()) {
                total++;
            }
        }

        assertEquals(state.getPoints().size(), total);
    }

    /**
     * Test of buildBareStructure, getStructureMap
     *
     * Depends on setMaterialAndPotentialChar
     *
     */
    @Test
    public void testBuildBareStructureGetStructureMap() {

        try {
            points.load("diagnolMiddleMissing.txt");
        } catch (RainWaterDaoException ex) {
            return;
        }
        List<Point> expectedData = points.getPoints();
        int expectedMultiplier = points.getMultiplier();

        state.setMaterialAndPotentialChar(material);
        state.buildBareStructure();
        StructureMap actualStructure = state.getStructureMap();

        int expectedMaxX = expectedData
                .stream()
                .max((p1, p2) -> Integer.compare(p1.getX(), p2.getX()))
                .get().getX();
        int expectedMaxY = expectedData
                .stream()
                .max((p1, p2) -> Integer.compare(p1.getY(), p2.getY()))
                .get().getY();

        assertEquals((expectedMaxX + 1) * expectedMultiplier, actualStructure.getWidth());
        assertEquals((expectedMaxY + 1) * expectedMultiplier, actualStructure.getHeight());

        int x;
        int y;
        for (Point expectedPoint : expectedData) {
            x = expectedPoint.getX();
            y = expectedPoint.getY();
            if (x + y < 2
                    || x == 1 && y == 1
                    || x == 4 && y == 4
                    || 8 < x + y) {
                assertTrue(actualStructure.isThisChar(expectedPoint, material[0]));
            } else if (actualStructure.isInBounds(expectedPoint)) {
                assertTrue(actualStructure.isThisChar(expectedPoint, BLANK_SPACE));
            }
        }
    }

    /**
     * Test of markInitialPotentialPoints method.
     *
     * Dependent on setRainDirection, buildBareStructure,
     * setMaterialAndPotentialChar, getStructureMap
     *
     */
    @Test
    public void testMarkInitialPotentialPoints() {

        StructureMap structure;
        Point toTest;
        state.setMaterialAndPotentialChar(material);
        try {
            points.load("diagnolMiddleMissing.txt");
        } catch (RainWaterDaoException ex) {

        }

        for (Direction direct : Direction.values()) {
            state.setRainDirection(direct);
            state.buildBareStructure();
            state.markInitialPotentialPoints();
            structure = state.getStructureMap();

            for (int i = 0; i < structure.getWidth(); i++) {
                for (int j = 0; j < structure.getHeight(); j++) {
                    if (i + j < 2
                            || i == 1 && j == 1
                            || i == 4 && j == 4
                            || 8 < i + j) {
                        toTest = (new Point(i, j)).oneInThisDirection(direct);
                        if (structure.isInBounds(toTest)
                                && (!structure.isThisChar(toTest, material[0]))
                                && (!structure.isThisChar(toTest, potentialChar))) {
                            fail();
                        }
                    }
                }
            }
        }

    }

    /**
     * Test of markWaterPoints
     *
     * Depends on markInitialPotentialPoints, setRainDirection,
     * buildBareStructure, setMaterialAndPotentialChar, getStructureMap
     *
     */
    @Test
    public void testMarkWaterPoints() {
        StructureMap structure;
        state.setMaterialAndPotentialChar(material);
        try {
            points.load("ticTacToe.txt");
        } catch (RainWaterDaoException ex) {

        }

        for (Direction direct : Direction.values()) {
            state.setRainDirection(direct);
            state.buildBareStructure();
            state.markInitialPotentialPoints();
            state.markWaterPoints();
            structure = state.getStructureMap();

            for (int i = 0; i < structure.getWidth(); i++) {
                for (int j = 0; j < structure.getHeight(); j++) {
                    switch (direct) {
                        case UP:
                            if ((i == 3 || i == 4) && j == 1) {
                                if (!structure.isThisChar(i, j, material[1])) {
                                    fail();
                                }
                            } else if (structure.isThisChar(i, j, material[1])) {
                                fail("i: " + i + " j: " + j);
                            }
                            break;
                        case DOWN:
                            if ((i == 3 || i == 4) && j == 6) {
                                if (!structure.isThisChar(i, j, material[1])) {
                                    fail();
                                }
                            } else if (structure.isThisChar(i, j, material[1])) {
                                fail();
                            }
                            break;

                        case LEFT:
                            if (i == 1 && (j == 3 || j == 4)) {
                                if (!structure.isThisChar(i, j, material[1])) {
                                    fail();
                                }

                            } else if (structure.isThisChar(i, j, material[1])) {
                                fail();
                            }
                            break;

                        case RIGHT:
                            if (i == 6 && (j == 3 || j == 4)) {
                                if (!structure.isThisChar(i, j, material[1])) {
                                    fail();
                                }
                            } else if (structure.isThisChar(i, j, material[1])) {
                                fail();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * Test of setMaterialAndPotentialChar
     *
     * Depends on markInitialPotentialPoints, setRainDirection,
     * buildBareStructure, markWaterPoints, getStructureMap
     *
     */
    @Test
    public void testSetMaterialAndPotentialChar() {
        StructureMap structure;
        state.setMaterialAndPotentialChar(material);
        try {
            points.load("ticTacToe.txt");
        } catch (RainWaterDaoException ex) {

        }

        state.setRainDirection(Direction.UP);
        state.buildBareStructure();
        state.markInitialPotentialPoints();
        state.markWaterPoints();
        structure = state.getStructureMap();

        assertTrue(structure.isThisChar(2, 0, material[0]));
        assertTrue(structure.isThisChar(3, 1, material[1]));
    }


/**
 * Test of markNextPotentialPoints
 *
 * Depends on markInitialPotentialPoints, setRainDirection, buildBareStructure,
 * markWaterPoints, getStructureMap, setMaterialAndPotentialChar
 *
 */
@Test
        public void testMarkNextPotentialPoints() {
        StructureMap structure;
        state.setMaterialAndPotentialChar(material);
        try {
            points.load("ticTacToe.txt");
        } catch (RainWaterDaoException ex) {

        }

        for (Direction direct : Direction.values()) {
            state.setRainDirection(direct);
            state.buildBareStructure();
            state.markInitialPotentialPoints();
            state.markWaterPoints();
            state.markNextPotentialPoints();
            structure = state.getStructureMap();

            for (int i = 0; i < structure.getWidth(); i++) {
                for (int j = 0; j < structure.getHeight(); j++) {
                    switch (direct) {
                        case UP:
                            if ((i == 3 || i == 4) && j == 0) {
                                if (!structure.isThisChar(i, j, potentialChar)) {
                                    fail();
                                }
                            } else if (structure.isThisChar(i, j, potentialChar)) {
                                fail("i: " + i + " j: " + j);
                            }
                            break;
                        case DOWN:
                            if ((i == 3 || i == 4) && j == 7) {
                                if (!structure.isThisChar(i, j, potentialChar)) {
                                    fail();
                                }
                            } else if (structure.isThisChar(i, j, potentialChar)) {
                                fail();
                            }
                            break;

                        case LEFT:
                            if (i == 0 && (j == 3 || j == 4)) {
                                if (!structure.isThisChar(i, j, potentialChar)) {
                                    fail();
                                }

                            } else if (structure.isThisChar(i, j, potentialChar)) {
                                fail();
                            }
                            break;

                        case RIGHT:
                            if (i == 7 && (j == 3 || j == 4)) {
                                if (!structure.isThisChar(i, j, potentialChar)) {
                                    fail();
                                }
                            } else if (structure.isThisChar(i, j, potentialChar)) {
                                fail();
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * Test of saveOldPotentialPoints and areNewPotentialPoints
     *
     * Depends on setRainDirection, buildBareStructure, markInitialPotentialPoints
     * markNextPotentialPoints, markWaterPoints, setMaterialAndPotentialChar
     */
    @Test
        public void testSaveOldPotentialPointsAndAreNewPotentialPoints() {
        state.setMaterialAndPotentialChar(material);
        try {
            points.load("ticTacToe.txt");
        } catch (RainWaterDaoException ex) {
        }

        for (Direction direct : Direction.values()) {
            state.setRainDirection(direct);
            state.buildBareStructure();
            state.markInitialPotentialPoints();
            state.saveOldPotentialPoints();
            state.markWaterPoints();
            state.markNextPotentialPoints();
            assertTrue(state.areNewPotentialPoints());

            state.saveOldPotentialPoints();
            assertFalse(state.areNewPotentialPoints());
        }
    }

    /**
     * Test of cleanUpPotentialPoints
     *
     * Depends on setMaterialAndPotentialChar, setRainDirection, 
     * markInitialPotentialPoints, buildBareStructure, getStructureMap
     *
     */
    @Test
        public void testCleanUpPotentialPoints() {
        StructureMap structure;
        Point toTest;
        state.setMaterialAndPotentialChar(material);
        try {
            points.load("diagnolMiddleMissing.txt");
        } catch (RainWaterDaoException ex) {

        }

        for (Direction direct : Direction.values()) {
            state.setRainDirection(direct);
            state.buildBareStructure();
            state.markInitialPotentialPoints();
            structure = state.getStructureMap();

            for (int i = 0; i < structure.getWidth(); i++) {
                for (int j = 0; j < structure.getHeight(); j++) {
                    if (i + j < 2
                            || i == 1 && j == 1
                            || i == 4 && j == 4
                            || 8 < i + j) {
                        toTest = (new Point(i, j)).oneInThisDirection(direct);
                        if (structure.isInBounds(toTest)
                                && (!structure.isThisChar(toTest, material[0]))
                                && (!structure.isThisChar(toTest, potentialChar))) {
                            fail();
                        }
                    }
                }
            }

            state.cleanUpPotentialPoints();

            for (int i = 0; i < structure.getWidth(); i++) {
                for (int j = 0; j < structure.getHeight(); j++) {
                    if (structure.isThisChar(i, j, potentialChar)) {
                        fail();
                    }
                }
            }
        }
    }
}

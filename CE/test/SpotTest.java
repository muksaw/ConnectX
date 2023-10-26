import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests Spot class
 * @author Mukul Sauhta
 */
public class SpotTest {

    /** This spot represents an empty spot */
    private Spot emptySpot;

    /** This spot represents a yellow spot */
    private Spot yellowSpot;

    /** This spot represents a red spot */
    private Spot redSpot;

    /**
    * Initialize yellow and red spot.
    */
    @BeforeEach
    public void setUp() {
        yellowSpot = new Spot();
        yellowSpot.setSpotColor(1);
        redSpot = new Spot();
        redSpot.setSpotColor(2);
        emptySpot = new Spot();

    }

    /**
     * Tests getSpotValue with yellowSpot
     */
    @Test
    public void testGetSpotColorYellow() {
        assertEquals(1, yellowSpot.getSpotColor(), "yellowSpot value");
    }

    /**
     * Tests setSpotValue with yellowSpot
     */
    @Test
    public void testSetSpotColorYellow() {
        Spot newSpot = new Spot();
        int yellow = 1;
        newSpot.setSpotColor(yellow);
        assertEquals(newSpot.getSpotColor(), yellowSpot.getSpotColor(), "yellowSpot value");
    }

    /**
     * Tests getSpotValue with redSpot
     */
    @Test
    public void testGetSpotColorRed() {
        assertEquals(2, redSpot.getSpotColor(), "redSpot value");
    }

    /**
     * Tests setSpotValue with redSpot
     */
    @Test
    public void testSetSpotColorRed() {
        Spot newSpot = new Spot();
        int red = 2;
        newSpot.setSpotColor(red);
        assertEquals(newSpot.getSpotColor(), redSpot.getSpotColor(), "redSpot value");
    }

    /**
     * Tests getSpotValue with emptySpot
     */
    @Test
    public void testGetSpotColorEmpty() {
        assertEquals(0, emptySpot.getSpotColor(), "emptySpot value");
    }

    /**
     * Tests setSpotValue with emptySpot
     */
    @Test
    public void testSetSpotColorEmpty() {
        Spot newSpot = new Spot();
        int empty = 0;
        newSpot.setSpotColor(empty);
        assertEquals(newSpot.getSpotColor(), emptySpot.getSpotColor(), "emptySpot value");
    }

}

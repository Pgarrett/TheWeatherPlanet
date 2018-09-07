package theweatherplanet.model;

import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class PlanetTest {

    
    @Test public void newPlanetTest() {
	final Planet p = new Planet("test", true, 5, 2000);
	Assert.assertEquals("test", p.getName());
    }

    /**
     * Test of getName method, of class Planet.
     */
    @Test
    public void testGetName() {
	System.out.println("getName");
	Planet instance = new Planet("test", true, 5, 2000);
	String expResult = "test";
	String result = instance.getName();
	assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceFromSun method, of class Planet.
     */
    @Test
    public void testGetDistanceFromSun() {
	System.out.println("getDistanceFromSun");
	Planet instance = new Planet("test", true, 5, 2000);
	int expResult = 2000;
	int result = instance.getDistanceFromSun();
	assertEquals(expResult, result);
    }

    /**
     * Test of getCurrentPosition method, of class Planet.
     */
    @Test
    public void testGetCurrentPosition() {
	System.out.println("getCurrentPosition");
	Planet instance = new Planet("test", true, 5, 2000);
	int expResult = 0;
	int result = instance.getCurrentPosition();
	assertEquals(expResult, result);
    }

    /**
     * Test of newDay method, of class Planet.
     */
    @Test
    public void testNewDay() {
	System.out.println("newDay");
	Planet instance = new Planet("test", true, 5, 2000);
	int expResult = 5;
	int result = instance.newDay();
	assertEquals(expResult, result);
    }
    

}
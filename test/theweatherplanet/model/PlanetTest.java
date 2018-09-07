package theweatherplanet.model;

import junit.framework.Assert;
import org.junit.Test;

public class PlanetTest {
    
    @Test public void newPlanetTest() {
	final Planet p = new Planet("test", true, 5, 2000);
	Assert.assertEquals("test", p.getName());
    }
    

}
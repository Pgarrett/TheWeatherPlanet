package theweatherplanet;

import theweatherplanet.model.Planet;

public class Forecast {
    
    

    private void newDay(final Planet ferengi, final Planet betasoid, final Planet vulcan) {
	ferengi.newDay();
	betasoid.newDay();
	vulcan.newDay();
    }

    public void predict() {
	final Planet ferengi = new Planet("Ferengis", true, 1, 500);
	final Planet betasoid = new Planet("Betasoides", true, 3, 2000);
	final Planet vulcan = new Planet("Vulcanos", false, 5, 1000);
	for (int i = 0; i < 3650; i++) {
	    newDay(ferengi, betasoid, vulcan);
	}
    }

}

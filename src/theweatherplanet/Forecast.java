package theweatherplanet;

import theweatherplanet.model.Planet;

public class Forecast {
    

    private void newDay(final Planet ferengi, final Planet betasoid, final Planet vulcan) {
	ferengi.newDay();
	betasoid.newDay();
	vulcan.newDay();
    }
    
    private void printDay(final Planet p) {
	System.out.println("Planet: " + p.getName() + ". Current Position: " + p.getCurrentPosition());
    }

    public void predict() {
	final Planet ferengi = new Planet("Ferengis", true, 1, 500);
	final Planet betasoid = new Planet("Betasoides", true, 3, 2000);
	final Planet vulcan = new Planet("Vulcanos", false, 5, 1000);
	for (int i = 0; i < 180; i++) {
	    newDay(ferengi, betasoid, vulcan);
	    printDay(ferengi);
	    printDay(betasoid);
	    printDay(vulcan);
	    System.out.println("");
	    System.out.println("");
	}
    }

}

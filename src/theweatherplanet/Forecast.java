
package theweatherplanet;

import com.google.gson.JsonArray;
import theweatherplanet.model.Planet;
import theweatherplanet.utils.Constants;

public class Forecast {

    private final Integer _yearsToForecast;
    private final Integer _daysToForecast;
    
    private final JsonArray _forecast;
    
    private int _maxRainDay;
    private int _maxRainDayTriangle;

    public Forecast(final Integer yearsToForecast) {
	_yearsToForecast = yearsToForecast;
	_daysToForecast = _yearsToForecast * Constants.DAYS_OF_YEAR;
	_forecast = new JsonArray();
    }

    private void newDay(final Planet ferengi, final Planet betasoid, final Planet vulcan) {
	ferengi.newDay();
	betasoid.newDay();
	vulcan.newDay();
    }

    private void printDay(final Planet p) {
	System.out.println("Planet: " + p.getName() + ". Current Position: " + p.getCurrentPosition());
    }
    
    private void getForecast(final Planet ferengi, final Planet betasoid, final Planet vulcan) {
	final int ferengiCP = ferengi.getCurrentPosition();
	final int betasoidCP = betasoid.getCurrentPosition();
	final int vulcanCP = vulcan.getCurrentPosition();
    }

    public void predict() {
	final Planet ferengi = new Planet("Ferengis", true, 1, 500);
	final Planet betasoid = new Planet("Betasoides", true, 3, 2000);
	final Planet vulcan = new Planet("Vulcanos", false, 5, 1000);
	for (int i = 0; i < _daysToForecast; i++) {
	    newDay(ferengi, betasoid, vulcan);
	    printDay(ferengi);
	    printDay(betasoid);
	    printDay(vulcan);
	    System.out.println("");
	    System.out.println("");
	}
    }

}

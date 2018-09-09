
package theweatherplanet;

import com.google.gson.JsonArray;
import theweatherplanet.model.Planet;
import theweatherplanet.utils.Constants;
import theweatherplanet.utils.Utilities;

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
	System.out.println("Planet: " + p.getName() + ". Current Position: " + p.getCurrentPosition() + ". Coordinates: (" + p.getXPosition() + ", " + p.getYPosition() + ")");
    }

    private boolean isDroughtForecast(final int ferengiP, final int betasoidP, final int vulcanP) {
	boolean result = true;
	result &= (ferengiP % Constants.HALF_ORBIT == betasoidP % Constants.HALF_ORBIT);
	result &= (ferengiP % Constants.HALF_ORBIT == vulcanP % Constants.HALF_ORBIT);
	return result;
    }

    private boolean isOptimumConditionForecast(final Planet ferengi, final Planet vulcan, final Planet betasoid) {
	final double ferengiX = ferengi.getXPosition();
	final double ferengiY = ferengi.getYPosition();
	final double vulcanX = vulcan.getXPosition();
	final double vulcanY = vulcan.getYPosition();
	final double betasoidX = betasoid.getXPosition();
	final double betasoidY = betasoid.getYPosition();
	final double m1 = Utilities.round((betasoidY - vulcanY) / (betasoidX - vulcanX));
	final double m2 = Utilities.round((betasoidY - ferengiY) / (betasoidX - ferengiX));
	final double m3 = Utilities.round((vulcanY - ferengiY) / (vulcanX - ferengiX));
	return m1 == m2 && m2 == m3;
    }

    private boolean isRainyDayForecast(final int ferengiP, final int betasoidP, final int vulcanP) {
	if (betasoidP > ferengiP && betasoidP < (ferengiP + Constants.HALF_ORBIT) % Constants.ORBIT_SIZE) {
	    if (vulcanP < ferengiP && vulcanP > (ferengiP + Constants.HALF_ORBIT) % Constants.ORBIT_SIZE) {

	    }
	}
	return true;
    }

    private String getForecast(final Planet ferengi, final Planet vulcan, final Planet betasoid) {
	final int ferengiP = ferengi.getCurrentPosition();
	final int betasoidP = betasoid.getCurrentPosition();
	final int vulcanP = vulcan.getCurrentPosition();
	if (isDroughtForecast(ferengiP, betasoidP, vulcanP)) {
	    return Constants.SEQUIA;
	} else if (isOptimumConditionForecast(ferengi, betasoid, vulcan)) {
	    return Constants.OPTIMA;
	} else if (isRainyDayForecast(ferengiP, betasoidP, vulcanP)) {
	    return Constants.LLUVIA;
	}
	return Constants.SIN_INFORMACION;
    }

    public void predict() {
	final Planet ferengi = new Planet("Ferengis", true, 1, 500);
	final Planet vulcan = new Planet("Vulcanos", false, 5, 1000);
	final Planet betasoid = new Planet("Betasoides", true, 3, 2000);
	int countOpt = 0;
	for (int i = 0; i < _daysToForecast; i++) {
	    final String forecast = getForecast(ferengi, vulcan, betasoid);
	    if (forecast.equals(Constants.OPTIMA)) {
		countOpt++;
		System.out.println("Day: " + i);
		printDay(ferengi);
		printDay(vulcan);
		printDay(betasoid);
		System.out.println("");
		System.out.println("");
	    }
	    newDay(ferengi, betasoid, vulcan);
	}
	System.out.println("Opt: " + countOpt);
    }

}

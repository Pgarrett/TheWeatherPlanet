
package theweatherplanet;

import ace.gson.builders.JsonObjectBuilder;
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

    private int _totalDry;
    private int _totalOptimum;
    private int _totalRain;
    private int _totalNoInfo;

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

    private boolean isDroughtForecast(final int ferengiP, final int vulcanP, final int betasoidP) {
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
    
    private int getSide(final int planetP, final int midPointA, final int midPointB) {
	if (midPointA >= 0 && midPointA < 180) {
	    //A: quadrant 1-2
	    //B: quadrant 3-4
	    if (planetP > midPointA && planetP < midPointB) {
		return 1;
	    } else {
		return 0;
	    }
	} else {
	    //A: quadrant 4-3
	    //B: quadrant 2-1
	    if (planetP < midPointA && planetP > midPointB) {
		return 1;
	    } else {
		return 0;
	    }
	}
    }
    
    private boolean areInSameQuadrant(final int ferengiP, final int vulcanP, final int betasoidP) {
	if (ferengiP >= 0 && ferengiP < 90 && vulcanP >= 0 && vulcanP < 90 && betasoidP >= 0 && betasoidP < 90) {
	    return true;
	} else if (ferengiP >= 90 && ferengiP < 180 && vulcanP >= 90 && vulcanP < 180 && betasoidP >= 90 && betasoidP < 180) {
	    return true;
	} else if (ferengiP >= 180 && ferengiP < 270 && vulcanP >= 180 && vulcanP < 270 && betasoidP >= 180 && betasoidP < 270) {
	    return true;
	} else if (ferengiP >= 270 && ferengiP < 360 && vulcanP >= 270 && vulcanP < 360 && betasoidP >= 270 && betasoidP < 360) {
	    return true;
	}
	return false;
    }
    
    private boolean isSunInMiddle(final int maxP, final int minP, final int betasoidP) {
	final int midPointB = (maxP + 180) % 360;
	if (maxP >= 0 && maxP < 90) {
	    //A: quadrant 1
	    //B: quadrant 3
	    if (betasoidP >= 180 && betasoidP < 270) {
		if (minP > maxP && minP < midPointB) {
		    return false;
		} else {
		    return true;
		}
	    } else {
		return false;
	    }
	} else if (maxP >= 90 && maxP < 180) {
	    //A: quadrant 2
	    //B: quadrant 4
	    if (betasoidP > 180 && betasoidP < midPointB) {
		if (minP > 0 && minP < (betasoidP + 180) % 360) {
		    return true;
		} else {
		    return false;
		}
	    } else {
		return false;
	    }
	} else if (maxP >= 180 && maxP < 270) {
	    //A: quadrant 3
	    //B: quadrant 1
	    if (betasoidP < maxP && betasoidP > minP) {
		if (minP < maxP && minP > midPointB) {
		    return false;
		} else {
		    return true;
		}
	    } else {
		if (minP < maxP && minP > midPointB) {
		    return true;
		} else {
		    return false;
		}
	    }
	} else {
	    //A: quadrant 4
	    //B: quadrant 2
	    if (minP < maxP && minP > midPointB) {
		return false;
	    } else {
		return true;
	    }
	}
    }

    private boolean isRainyDayForecast(final int ferengiP, final int vulcanP, final int betasoidP) {
	final int betasoidPB = (betasoidP + 180) % 360;
	final int ferengiSide = getSide(ferengiP, betasoidP, betasoidPB);
	final int vulcanSide = getSide(vulcanP, betasoidP, betasoidPB);
	if (areInSameQuadrant(ferengiP, vulcanP, betasoidP)) {
	    return false;
	} else {
	    if (ferengiSide == vulcanSide) {
		return false;
	    } else {
		if (ferengiP > vulcanP) {
		    return isSunInMiddle(ferengiP, vulcanP, betasoidP);
		} else {
		    return isSunInMiddle(vulcanP, ferengiP, betasoidP);
		}
	    }
	}
    }

    private String getForecast(final Planet ferengi, final Planet vulcan, final Planet betasoid) {
	final int ferengiP = ferengi.getCurrentPosition();
	final int vulcanP = vulcan.getCurrentPosition();
	final int betasoidP = betasoid.getCurrentPosition();
	if (isDroughtForecast(ferengiP, vulcanP, betasoidP)) {
	    _totalDry++;
	    return Constants.SEQUIA;
	} else if (isOptimumConditionForecast(ferengi, vulcan, betasoid)) {
	    _totalOptimum++;
	    return Constants.OPTIMA;
	} else if (isRainyDayForecast(ferengiP, vulcanP, betasoidP)) {
	    _totalRain++;
	    return Constants.LLUVIA;
	}
	_totalNoInfo++;
	return Constants.SIN_INFORMACION;
    }

    public void predict() {
	final Planet ferengi = new Planet("Ferengis", true, 1, 500);
	final Planet vulcan = new Planet("Vulcanos", false, 5, 1000);
	final Planet betasoid = new Planet("Betasoides", true, 3, 2000);
	for (int i = 0; i < _daysToForecast; i++) {
	    final String forecast = getForecast(ferengi, vulcan, betasoid);
	    _forecast.add(
		new JsonObjectBuilder()
		    .add(Constants.DIA, i)
		    .add(Constants.CONDICION, forecast)
		    .getAsJsonObject()
	    );
//	    if (forecast.equals(Constants.OPTIMA)) {
//		printDay(ferengi);
//		printDay(vulcan);
//		printDay(betasoid);
//		System.out.println("");
//		System.out.println("");
//	    }
	    newDay(ferengi, betasoid, vulcan);
	}
	System.out.println("Total Dry: " + _totalDry);
	System.out.println("Total Optimum: " + _totalOptimum);
	System.out.println("Total Rain: " + _totalRain);
	System.out.println("Total No Info: " + _totalNoInfo);
    }

}

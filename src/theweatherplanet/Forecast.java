package theweatherplanet;

import ace.Ace;
import ace.constants.STRINGS;
import ace.files.TextFiles;
import ace.gson.Json;
import ace.gson.builders.JsonObjectBuilder;
import ace.text.Strings;
import com.google.gson.JsonArray;
import java.io.File;
import org.apache.log4j.Logger;
import theweatherplanet.model.Planet;
import theweatherplanet.utils.Constants;
import theweatherplanet.utils.Utilities;

public class Forecast {

    private final Integer _yearsToForecast;
    private final Integer _daysToForecast;

    private final File _jsonDataFile;

    private final JsonArray _forecast;

    private int _maxRainDay;
    private double _maxRainDayTriangle;

    private int _totalDry;
    private int _totalOptimum;
    private int _totalRain;
    
    private static Logger _logger = Logger.getLogger(TheWeatherPlanet.class);

    public Forecast(final Integer yearsToForecast, final File jsonDataFile) {
	_yearsToForecast = yearsToForecast;
	_daysToForecast = _yearsToForecast * Constants.DAYS_OF_YEAR;
	_forecast = new JsonArray();
	_jsonDataFile = jsonDataFile;
    }

    private void newDay(final Planet ferengi, final Planet betasoid, final Planet vulcan) {
	ferengi.newDay();
	betasoid.newDay();
	vulcan.newDay();
    }

    private boolean isDroughtForecast(final int ferengiP, final int vulcanP, final int betasoidP) {
	boolean result = true;
	result &= (ferengiP % Constants.HALF_ORBIT == betasoidP % Constants.HALF_ORBIT);
	result &= (ferengiP % Constants.HALF_ORBIT == vulcanP % Constants.HALF_ORBIT);
	return result;
    }

    private boolean isOptimumConditionForecast(final Planet ferengi, final Planet vulcan, final Planet betasoid, final int day) {
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
	    if (planetP > midPointA && planetP < midPointB) {
		return 1;
	    } else {
		return 0;
	    }
	} else {
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
	    if (betasoidP >= 180 && betasoidP < 270) {
		return !(minP > maxP && minP < midPointB);
	    } else {
		return false;
	    }
	} else if (maxP >= 90 && maxP < 180) {
	    if (betasoidP > 180 && betasoidP < midPointB) {
		return minP > 0 && minP < (betasoidP + 180) % 360;
	    } else {
		return false;
	    }
	} else if (maxP >= 180 && maxP < 270) {
	    if (minP > midPointB) {
		return !(betasoidP > midPointB && betasoidP < (minP + 180) % 360);
	    } else {
		return betasoidP > midPointB && betasoidP < (minP + 180) % 360;
	    }
	} else {
	    if (minP < midPointB) {
		return betasoidP > midPointB && betasoidP < (minP + 180) % 360;
	    } else {
		return !(betasoidP > midPointB && betasoidP < (minP + 180) % 360);
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

    private double getDistanceBetweenPlanets(final Planet planetA, final Planet planetB) {
	return Math.sqrt(Math.pow(planetA.getXPosition() - planetB.getXPosition(), 2) + Math.pow(planetA.getYPosition() - planetB.getYPosition(), 2));
    }

    private void updateMaxTriangleSize(final Planet ferengi, final Planet vulcan, final Planet betasoid, final int day) {
	final double ferengiVulcanD = getDistanceBetweenPlanets(ferengi, vulcan);
	final double vulcanBetasoidD = getDistanceBetweenPlanets(vulcan, betasoid);
	final double betasoidFerengiD = getDistanceBetweenPlanets(betasoid, ferengi);
	final double totalDistance = ferengiVulcanD + vulcanBetasoidD + betasoidFerengiD;
	if (totalDistance >= _maxRainDayTriangle) {
	    _maxRainDayTriangle = totalDistance;
	    _maxRainDay = day;
	}
    }

    private String getForecast(final Planet ferengi, final Planet vulcan, final Planet betasoid, final int day) {
	final int ferengiP = ferengi.getCurrentPosition();
	final int vulcanP = vulcan.getCurrentPosition();
	final int betasoidP = betasoid.getCurrentPosition();
	if (isDroughtForecast(ferengiP, vulcanP, betasoidP)) {
	    _totalDry++;
	    return Constants.SEQUIA;
	} else if (isOptimumConditionForecast(ferengi, vulcan, betasoid, day)) {
	    _totalOptimum++;
	    return Constants.OPTIMA;
	} else if (isRainyDayForecast(ferengiP, vulcanP, betasoidP)) {
	    _totalRain++;
	    updateMaxTriangleSize(ferengi, vulcan, betasoid, day);
	    return Constants.LLUVIA;
	}
	return Constants.SIN_INFORMACION;
    }
    
    private void cleanup(final File startFile, final File errorFile) {
	if (Ace.assigned(startFile) && startFile.exists()) {
	    if (!startFile.delete()) {
		_logger.error(Strings.concat("El archivo '", startFile.getAbsolutePath(), "' no pudo ser eliminado."));
	    }
	}
	if (Ace.assigned(errorFile) && errorFile.exists()) {
	    if (!errorFile.delete()) {
		_logger.error(Strings.concat("El archivo '", errorFile.getAbsolutePath(), "' no pudo ser eliminado."));
	    }
	}
    }

    public void predict() {
	final File startFile = new File(Constants.START_FILE);
	final File errorFile = new File(Constants.ERROR_FILE);
	cleanup(startFile, errorFile);
	TextFiles.write(startFile, STRINGS.EMPTY);
	final Planet ferengi = new Planet("Ferengis", true, 1, 500);
	final Planet vulcan = new Planet("Vulcanos", false, 5, 1000);
	final Planet betasoid = new Planet("Betasoides", true, 3, 2000);
	for (int i = 0; i < _daysToForecast; i++) {
	    final String forecast = getForecast(ferengi, vulcan, betasoid, i);
	    _forecast.add(
		new JsonObjectBuilder()
		    .add(Constants.DIA, i)
		    .add(Constants.CONDICION, forecast)
		    .getAsJsonObject()
	    );
	    newDay(ferengi, betasoid, vulcan);
	}
	if (TextFiles.write(_jsonDataFile, Json.JsonElementToPrettyString(
	    new JsonObjectBuilder()
		.add(Constants.DIA_MAXIMA_LLUVIA, _maxRainDay)
		.add(Constants.TOTAL_SEQUIA, _totalDry)
		.add(Constants.TOTAL_LLUVIA, _totalRain)
		.add(Constants.TOTAL_CONDICIONES_OPTIMAS, _totalOptimum)
		.add(Constants.PRONOSTICO, _forecast)
		.getAsJsonObject()))) {
	    cleanup(startFile, errorFile);
	} else {
	    cleanup(startFile, null);
	    TextFiles.write(errorFile, STRINGS.EMPTY);
	}
    }

}

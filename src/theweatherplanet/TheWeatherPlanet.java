
package theweatherplanet;

import ace.Ace;
import ace.gson.Json;
import ace.text.Strings;
import com.google.gson.JsonObject;
import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import theweatherplanet.utils.Constants;

public class TheWeatherPlanet {

    private static Logger _logger = Logger.getLogger(TheWeatherPlanet.class);

    private static int _yearsToForecast;
    private static File _dataDirectory;
    private static File _dataFile;

    private static JsonObject readConfig() {
	final JsonObject config = Json.readFileAsJsonObject(Constants.CONFIG_FILE);
	if (Ace.assigned(config)) {
	    if (config.has(Constants.YEARS) && config.has(Constants.DATA_FILE) && config.has(Constants.DATA_DIRECTORY)) {
		return config;
	    }
	}
	return null;
    }

    private static boolean init() {
	if (!new File(Constants.LOG4J_FILE).exists()) {
	    System.err.println(Strings.concat("No se encontró el archivo de configuración: '", Constants.LOG4J_FILE, "'."));
	    System.err.println(Constants.APPLICATION_EXIT);
	    return false;
	}
	PropertyConfigurator.configureAndWatch(Constants.LOG4J_FILE);
	if (Constants.CONFIG_FILE.exists()) {
	    final JsonObject config = readConfig();
	    if (Ace.assigned(config)) {
		_yearsToForecast = Json.obtainInteger(config, Constants.YEARS);
		_dataDirectory = new File(Json.obtainString(config, Constants.DATA_DIRECTORY));
		if (_dataDirectory.exists()) {
		    _dataFile = new File(_dataDirectory, Json.obtainString(config, Constants.DATA_FILE));
		    return true;
		}
		_logger.error(Strings.concat("El directorio '", Json.obtainString(config, Constants.DATA_DIRECTORY), "' no existe."));
		return false;
	    } else {
		_logger.error("La configuración no es correcta, por favor verificar.");
		return false;
	    }
	} else {
	    _logger.error(Strings.concat("No se encontró el archivo de configuración: ", Constants.CONFIG_FILE.getAbsolutePath(), "."));
	    return false;
	}
    }

    public static void main(final String[] args) {
	if (args.length > 0 && (args[0].equals(Constants.V_ARG) || args[0].equals(Constants.VERSION_ARG))) {
	    System.out.println(Constants.INIT_MESSAGE);
	    System.exit(0);
	}
	if (init()) {
	    _logger.info(Constants.INIT_MESSAGE);
	    _logger.info("Application start.");
	    final Forecast f = new Forecast(_yearsToForecast, _dataDirectory, _dataFile);
	    f.predict();
	    _logger.info(Constants.APPLICATION_EXIT);
	    System.exit(0);
	}
	System.exit(-1);

    }

}

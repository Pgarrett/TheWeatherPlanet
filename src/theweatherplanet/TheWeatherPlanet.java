
package theweatherplanet;

import ace.Ace;
import ace.Sandboxed;
import java.io.File;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import theweatherplanet.utils.Constants;

public class TheWeatherPlanet {

    private static final String VERSION = "1.0.0";

    private static Logger _logger = Logger.getLogger(TheWeatherPlanet.class);

    private static void printVersionNumber() {
//	System.out.println("########################################\n");
//	System.out.println("###  The Weather Planet Application  ###\n");
//	System.out.println("###           Version  " + VERSION + "         ###\n");
//	System.out.println("########################################");
	_logger.info("Start");
	_logger.info("\n########################################\n###  The Weather Planet Application  ###\n###           Version  " + VERSION + "         ###\n########################################");
    }

    private static void printCorrectUsage() {
	System.out.println("########################################");
	System.out.println("###  The Weather Planet Application  ###");
	System.out.println("###           Version  " + VERSION + "         ###");
	System.out.println("###                                  ###");
	System.out.println("###  Correct usage:                  ###");
	System.out.println("###          1 argument expected:    ###");
	System.out.println("###            *  -v or --version    ###");
	System.out.println("###            *  number of years    ###");
	System.out.println("###                   to forecast    ###");
	System.out.println("########################################");
    }

    private static Integer getYearsToForecast(final String s) {
	final Sandboxed sandbox = new Sandboxed() {
	    @Override public Integer run() throws Exception {
		return Integer.parseInt(s);
	    }
	};
	return (Integer) sandbox.go();
    }

    public static void main(final String[] args) {
	PropertyConfigurator.configureAndWatch("config/log4j.properties");
	if (args.length > 0) {
	    if (!new File("config/log4j.properties").exists()) {
		System.err.println("Logging configuration file not found \"config/log4j.properties\"");
		System.err.println("Exit application.");
		System.exit(-1);
	    }
	    printVersionNumber();
	    if (args[0].equals(Constants.V_ARG) || args[0].equals(Constants.VERSION_ARG)) {
		System.exit(0);
	    }
	    final Integer yearsToForecast = getYearsToForecast(args[0]);
	    if (Ace.assigned(yearsToForecast)) {
		final Forecast f = new Forecast(yearsToForecast, new File(args[1]));
		f.predict();
		_logger.info("End");
		System.exit(0);
	    }
	}
	printCorrectUsage();
	System.exit(-1);
    }

}

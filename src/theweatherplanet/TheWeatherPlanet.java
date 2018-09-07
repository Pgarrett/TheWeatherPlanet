
package theweatherplanet;

import ace.Ace;
import ace.Sandboxed;
import theweatherplanet.utils.Constants;

public class TheWeatherPlanet {

    private static final String VERSION = "1.0.0";

    private static void printVersionNumber() {
	System.out.println("########################################");
	System.out.println("###  The Weather Planet Application  ###");
	System.out.println("###           Version  " + VERSION + "          ###");
	System.out.println("########################################");
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
	if (args.length > 0) {
	    printVersionNumber();
	    if (args[0].equals(Constants.V_ARG) || args[0].equals(Constants.VERSION_ARG)) {
		System.exit(0);
	    }
	    final Integer yearsToForecast = getYearsToForecast(args[0]);
	    if (Ace.assigned(yearsToForecast)) {
		final Forecast f = new Forecast(yearsToForecast);
		f.predict();
	    }
	}
	printCorrectUsage();
	System.exit(-1);
    }

}

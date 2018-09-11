
package theweatherplanet.utils;

import java.io.File;

public class Constants {

    public static final int ORBIT_SIZE = 360;
    public static final int HALF_ORBIT = 180;
    public static final int DAYS_OF_YEAR = 365;

    public static final String VERSION = "1.0.0";
    public static final File CONFIG_FILE = new File("config/theweatherplanet.cfg");
    public static final String LOG4J_FILE = "config/log4j.properties";
    public static final String INIT_MESSAGE = "\n########################################\n###  The Weather Planet Application  ###\n###           Version  " + VERSION + "         ###\n########################################";

    public static final String V_ARG = "-v";
    public static final String VERSION_ARG = "--version";

    public static final String APPLICATION_EXIT = "Application exit.";

    public static final String START_FILE = "data.start";
    public static final String ERROR_FILE = "data.error";

    public static final String DIA_MAXIMA_LLUVIA = "dia-maxima-lluvia";
    public static final String TOTAL_SEQUIA = "total-sequia";
    public static final String TOTAL_LLUVIA = "total-lluvia";
    public static final String TOTAL_CONDICIONES_OPTIMAS = "total-condiciones-optimas";
    public static final String PRONOSTICO = "pronostico";
    public static final String DIA = "dia";
    public static final String CONDICION = "condicion";
    public static final String SEQUIA = "sequia";
    public static final String LLUVIA = "lluvia";
    public static final String OPTIMA = "optima";
    public static final String SIN_INFORMACION = "sin-informacion";

}

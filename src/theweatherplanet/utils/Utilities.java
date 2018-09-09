package theweatherplanet.utils;

public class Utilities {

    public static double round(final double value) {
	final int scale = (int) Math.pow(10, 1);
	return (double) Math.round(value * scale) / scale;
    }

}
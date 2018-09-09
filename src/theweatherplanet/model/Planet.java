
package theweatherplanet.model;

import theweatherplanet.utils.Constants;
import theweatherplanet.utils.Utilities;

public class Planet {

    private final String _name;
    private final boolean _clockwise;
    private final int _speed;
    private final double _distanceFromSun;
    private int _currentPosition;

    public Planet(final String name, final boolean clockwise, final int speed, final double distanceFromSun) {
	_name = name;
	_clockwise = clockwise;
	_speed = _clockwise ? speed * -1 : speed;
	_distanceFromSun = distanceFromSun;
	_currentPosition = 0;
    }

    public String getName() {
	return _name;
    }

    public double getDistanceFromSun() {
	return _distanceFromSun;
    }

    public int getCurrentPosition() {
	return _currentPosition;
    }

    public int newDay() {
	_currentPosition = ((_currentPosition + _speed) % Constants.ORBIT_SIZE);
	if (_currentPosition < 0) {
	    _currentPosition += Constants.ORBIT_SIZE;
	}
	return getCurrentPosition();
    }

    public double getXPosition() {
	return Utilities.round(_distanceFromSun * (Math.cos(Math.toRadians(_currentPosition))));
    }

    public double getYPosition() {
	return Utilities.round(_distanceFromSun * (Math.sin(Math.toRadians(_currentPosition))));
    }

}

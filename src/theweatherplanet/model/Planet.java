package theweatherplanet.model;

import theweatherplanet.utils.Constants;

public class Planet {

    private final String _name;
    private final boolean _clockwise;
    private final int _speed;
    private final int _distanceFromSun;
    private int _currentPosition;

    public Planet(final String name, final boolean clockwise, final int speed, final int distanceFromSun) {
	_name = name;
	_clockwise = clockwise;
	_speed = _clockwise ? speed : speed * -1;
	_distanceFromSun = distanceFromSun;
	_currentPosition = 0;
    }

    public String getName() {
	return _name;
    }

    public int getDistanceFromSun() {
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

}

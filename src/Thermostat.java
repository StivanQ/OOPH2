import java.util.ArrayList;

/**
 * @author Fabian
 *
 */
public class Thermostat {
	private int initialTimestamp;
	private Double globalTemperature;
	private Double globalHumidity;
	private boolean humidity;
	private final int HOUR = 3600;

	private ArrayList<Sensor> sensors;

	/**
	 * Construct a new thermostat
	 * 
	 * @param globalTemperature the global temperature of the house the
	 *                          thermostat is in
	 * @param humidity          true if there is humidity to be considered
	 * @param globalHumidity    the global humidity value of the house the
	 *                          thermostat is in
	 * @param initialTimestamp  the initial time stamp a.k.a. the present
	 */
	Thermostat(Double globalTemperature, boolean humidity,
			Double globalHumidity, int initialTimestamp) {
		sensors = new ArrayList<Sensor>();
		this.initialTimestamp = initialTimestamp;
		this.globalTemperature = globalTemperature;
		this.globalHumidity = globalHumidity;
		this.humidity = humidity;
	}

	/**
	 * @return the globalTemperature
	 */
	public Double getGlobalTemperature() {
		return globalTemperature;
	}

	/**
	 * @return the initialTimestamp
	 */
	public int getInitialTimestamp() {
		return initialTimestamp;
	}

	/**
	 * @param globalTemperature the globalTemperature to set
	 */
	public void setGlobalTemperature(Double globalTemperature) {
		this.globalTemperature = globalTemperature;
	}

	/**
	 * Adds the new sensor to the thermostat
	 * 
	 * @param sensor the sensor to be added
	 */
	public void addSensor(Sensor sensor) {
		sensors.add(sensor);
	}

	/**
	 * Adds the temperature value to the temperature database
	 * 
	 * @param sensorName   the sensor name that recorded the temperature
	 * @param crtTimestamp the time stamp at which the recording took place
	 * @param temperature  the temperature value to be added
	 */
	public void addDataTemp(String sensorName, int crtTimestamp,
			Double temperature) {
		for(Sensor s : sensors) {
			if(s.getName().contentEquals(sensorName)) {
				s.addDataTemp(initialTimestamp, crtTimestamp, temperature);
			}
		}
	}
	
	/**
	 * Adds the humidity value to the humidity database
	 * 
	 * @param sensorName   the sensor name that recorded the humidity
	 * @param crtTimestamp the time stamp at which the recording took place
	 * @param humidity     the humidity value to be added
	 */
	public void addDataHumidity(String sensorName, int crtTimestamp,
			Double humidity) {
		for (Sensor s : sensors) {
			if (s.getName().contentEquals(sensorName)) {
				s.addDataHumidity(initialTimestamp, crtTimestamp, humidity);
			}
		}
	}

	/**
	 * Calculates whether the the heating should be triggered or not
	 * 
	 * @param house the house the thermostat is in
	 * @return true if the heating should be triggered
	 */
	public boolean triggerHeat(House house) {
		Integer totalSurface = 0;
		Integer auxSurface = 0;
		
		if (humidity == false) {
			Double weightedTemperature = 0.00;
			Double auxWeightedTemperature = 0.00;

			for (Room r : house.getRooms()) {
				try {
					auxWeightedTemperature = r.getLastMinPonderateTemperature();
					auxSurface = r.getSurface();
				} catch (NoDataFoundException ex) {
					auxWeightedTemperature = 0.00;
					auxSurface = 0;
				} finally {
					weightedTemperature += auxWeightedTemperature;
					totalSurface += auxSurface;
				}
			}
			weightedTemperature = weightedTemperature / totalSurface;

			if (weightedTemperature < globalTemperature) {
				return true;
			}

			return false;
		} else {
			Double weightedHumidity = 0.00;
			Double auxWeightedHumidity = 0.00;

			for (Room r : house.getRooms()) {
				try {
					auxWeightedHumidity = r.getLastMaxPonderateHumidity();
					auxSurface = r.getSurface();
				} catch (NoDataFoundException ex) {
					auxWeightedHumidity = 0.00;
					auxSurface = 0;
				} finally {
					weightedHumidity += auxWeightedHumidity;
					totalSurface += auxSurface;
				}
			}
			weightedHumidity = weightedHumidity / totalSurface;

			if (weightedHumidity < globalHumidity) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Lists the temperature values of the whole time buckets that are contained
	 * between the two time bounds
	 * 
	 * @param roomName the room whose temperature values are to be listed
	 * @param time1    the lower bound of the interval
	 * @param time2    the upper bound of the interval
	 * @return a string representation of all the temperature values listed in
	 *         ascending order on buckets
	 */
	public String list(String roomName, int time1, int time2) {
		StringBuilder stringBuilder = new StringBuilder();
		String string;
		stringBuilder.append(roomName);
		stringBuilder.append(" ");

		int index1 = (initialTimestamp - time1) / HOUR;
		int index2 = (initialTimestamp - time2) / HOUR;

		for (Sensor s : sensors) {
			if (s.getRoomName().contentEquals(roomName)) {
				for (int i = index2; i < index1; i++) {
					stringBuilder.append(s.listInterval(i));
				}
			}
		}
		string = stringBuilder.toString();
		return string;

	}

}


/**
 * @author Fabian
 *
 */
public class Sensor {
	private String name;
	private String roomName;
	private Data temperature;
	private Data humidity;

	/**
	 * Constructs a new sensor and initializes the data fields
	 * 
	 * @param name     the name of the sensor
	 * @param roomName the name of the room it belongs to
	 */
	public Sensor(String name, String roomName) {
		this.name = name;
		this.roomName = roomName;
		temperature = new Data();
		humidity = new Data();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @return the temperature
	 */
	public Data getTemperature() {
		return temperature;
	}
	
	/**
	 * @return the humidity
	 */
	public Data getHumidity() {
		return humidity;
	}

	/**
	 * Adds the temperature value to the temperature database
	 * 
	 * @param initialTimestamp the initial time stamp a.k.a. the present
	 * @param crtTimestamp     the current time stamp
	 * @param temperature      temperature to be added to the data
	 */
	public void addDataTemp(int initialTimestamp, int crtTimestamp,
			Double temperature) {
		this.temperature.addData(initialTimestamp, crtTimestamp, temperature);
	}
	
	/**
	 * Adds the humidity value to the humidity database
	 * 
	 * @param initialTimestamp the initial time stamp a.k.a. the present
	 * @param crtTimestamp     the current time stamp
	 * @param humidity         humidity to be added to the data
	 */
	public void addDataHumidity(int initialTimestamp, int crtTimestamp,
			Double humidity) {
		this.humidity.addData(initialTimestamp, crtTimestamp, humidity);
	}

	/**
	 * Returns the smallest value of the temperature found in the most recent
	 * time bucket
	 * 
	 * @return the minimum of the last bucket
	 * @throws NoDataFoundException if there is no data recorded in the
	 *                              temperature database
	 */
	public Double getLastMinTemp() throws NoDataFoundException {
		return temperature.getLastMinimum();
	}

	/**
	 * Returns the biggest value of the humidity found in the most recent time
	 * bucket
	 * 
	 * @return the maximum of the last bucket
	 * @throws NoDataFoundException if there is no data recorded in the humidity
	 *                              database
	 */
	public Double getLastMaxHumidity() throws NoDataFoundException {
		return humidity.getLastMaximum();
	}

	/**
	 * Returns the string representation of the index specified temperature time
	 * bucket
	 * 
	 * @param index the bucket to be listed
	 * @return the string representation of the
	 */
	public String listInterval(int index) {
		return temperature.listInterval(index);
	}

}

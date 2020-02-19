
/**
 * @author Fabian
 *
 */
public class Room {
	private String name;
	private Sensor sensor;
	private int surface;

	/**
	 * Constructs a new room
	 * 
	 * @param name    the name of the room
	 * @param sensor  the name of the sensor that is in the room
	 * @param surface the surface of the room
	 */
	public Room(String name, Sensor sensor, int surface) {
		this.name = name;
		this.sensor = sensor;
		this.surface = surface;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the sensor
	 */
	public Sensor getSensor() {
		return sensor;
	}

	/**
	 * @return the surface
	 */
	public int getSurface() {
		return surface;
	}

	/**
	 * Returns the product of the last minimum temperature and the surface
	 * 
	 * @return the product of the surface and the last minimum recorded
	 *         temperature
	 * @throws NoDataFoundException in case there is no temperature data
	 *                              recorded
	 */
	public Double getLastMinPonderateTemperature()
			throws NoDataFoundException {
		return surface * sensor.getLastMinTemp();
	}

	/**
	 * Returns the product of the last maximum humidity value and the surface
	 * 
	 * @return the product of the surface and the last minimum recorded humidity
	 *         value
	 * @throws NoDataFoundException in case there is no humidity data recorded
	 */
	public Double getLastMaxPonderateHumidity() throws NoDataFoundException {
		return surface * sensor.getLastMaxHumidity();
	}


}

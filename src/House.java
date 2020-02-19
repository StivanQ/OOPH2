import java.util.ArrayList;

/**
 * @author Fabian
 *
 */
public class House {
	private ArrayList<Room> rooms;
	private int noRooms;
	private Thermostat thermostat;

	/**
	 * Constructs a new house
	 * 
	 * @param noRooms           the number of rooms the house has
	 * @param globalTemperature the initial global temperature the house has
	 * @param humidity          true if there is humidity to be considered
	 * @param globalHumidity    the global humidity value the house has
	 * @param initialTimestamp  the initial time stamp a.k.a. the present
	 */
	public House(int noRooms, Double globalTemperature, boolean humidity,
			Double globalHumidity, int initialTimestamp) {
		thermostat = new Thermostat(globalTemperature, humidity, globalHumidity,
				initialTimestamp);
		rooms = new ArrayList<Room>();
		this.noRooms = noRooms;
	}

	/**
	 * @return the rooms
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/**
	 * @return the thermostat
	 */
	public Thermostat getThermostat() {
		return thermostat;
	}

	/**
	 * @return the noRooms
	 */
	public int getNoRooms() {
		return noRooms;
	}

	/**
	 * Adds the room to the house
	 * 
	 * @param room the room to be added to the house
	 */
	public void addRoom(Room room) {
		rooms.add(room);
		thermostat.addSensor(room.getSensor());
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * The most important class
 * 
 * @author Fabian
 *
 */
public class Parser {

	private File input;
	private FileWriter output;
	private StringBuilder stringBuilder;

	private Scanner scanner;

	/**
	 * Constructs a new parser
	 * 
	 * @param input  the path of the input file
	 * @param output the path of the output file
	 */
	public Parser(String input, String output) {
		this.input = new File(input);
		try {
			this.output = new FileWriter(output);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			scanner = new Scanner(this.input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stringBuilder = new StringBuilder();
	}

	/**
	 * The main method that ncorporates everything
	 */
	public void solve() {
		if(!scanner.hasNext()) {
			return;
		}

		String token = null;
		String[] things = null;

		token = scanner.nextLine();
		things = token.split(" ");

		House house = null;

		if (things.length == 3) {
			house = new House(Integer.parseInt(things[0]),
					Double.parseDouble(things[1]), false, 0.0,
					Integer.parseInt(things[2]));
		} else if (things.length == 4) {
			house = new House(Integer.parseInt(things[0]),
					Double.parseDouble(things[1]), true,
					Double.parseDouble(things[2]), Integer.parseInt(things[3]));
		} else {
			System.out.println("not good");
			return;
		}

		for (int i = 0; i < house.getNoRooms(); i++) {
			token = scanner.nextLine();
			things = token.split(" ");
			house.addRoom(new Room(things[0], new Sensor(things[1], things[0]),
					Integer.parseInt(things[2])));
		}

		while (scanner.hasNext()) {

			token = scanner.nextLine();
			things = token.split(" ");

			if (things[0].contentEquals("OBSERVE")) {
				house.getThermostat().addDataTemp(things[1],
						Integer.parseInt(things[2]),
						Double.parseDouble(things[3]));
			} else if (things[0].contentEquals("TRIGGER")) {

				if (house.getThermostat().triggerHeat(house)) {
					stringBuilder.append("YES");
				} else {
					stringBuilder.append("NO");
				}

				try {
					stringBuilder.append("\n");
					output.write(stringBuilder.toString());
					output.flush();
					stringBuilder.delete(0, stringBuilder.length());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (things[0].contains("TEMPERATURE")) {

				house.getThermostat()
						.setGlobalTemperature(Double.parseDouble(things[1]));
			} else if (things[0].contains("LIST")) {

				stringBuilder.append(house.getThermostat().list(things[1],
						Integer.parseInt(things[2]),
						Integer.parseInt(things[3])));
				try {
					stringBuilder.append("\n");
					output.write(stringBuilder.toString());
					stringBuilder.delete(0, stringBuilder.length());
					output.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (things[0].contentEquals("OBSERVEH")) {
				house.getThermostat().addDataHumidity(things[1],
						Integer.parseInt(things[2]),
						Double.parseDouble(things[3]));
			}
		}



		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

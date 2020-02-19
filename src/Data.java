import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeSet;

/**
 * @author Fabian
 *
 */
public class Data {
	private ArrayList<TreeSet<Double>> data;
	private final int HOUR = 3600;


	/**
	 * Constructs a new database
	 */
	Data() {
		data = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			TreeSet<Double> orderedSet = new TreeSet<>();
			data.add(orderedSet);
		}
	}

	/**
	 * @return the data
	 */
	public ArrayList<TreeSet<Double>> getData() {
		return data;
	}

	/**
	 * Adds the data to the specific time bucket
	 * 
	 * @param initialTimestamp the initial time stamp a.k.a the present
	 * @param crtTimestamp     the current time stamp
	 * @param data             data to be added
	 */
	public void addData(int initialTimestamp, int crtTimestamp,
			Double data) {
		if (crtTimestamp > initialTimestamp) {
			return;
		}

		int index = (initialTimestamp - crtTimestamp) / HOUR;
		if (index > 23) {
			return;
		}

		this.data.get(index).add(data);
	}

	/**
	 * Returns the minimum for the most recent bucket or throws an exception if
	 * there is no data to be returned
	 * 
	 * @return the minimum for the most recent bucket
	 * @throws NoDataFoundException in case there is no data found
	 */
	public Double getLastMinimum() throws NoDataFoundException {
		for (TreeSet<Double> set : data) {
			if (!set.isEmpty()) {
				return set.first();
			}
		}
		throw new NoDataFoundException();
	}

	/**
	 * Returns the maximum for the most recent bucket or throws an exception if
	 * there is no data to be returned
	 * 
	 * @return the maximum for the most recent bucket
	 * @throws NoDataFoundException in case there is no data found
	 */
	public Double getLastMaximum() throws NoDataFoundException {
		for (TreeSet<Double> set : data) {
			if (!set.isEmpty()) {
				return set.last();
			}
		}
		throw new NoDataFoundException();
	}

	/**
	 * Lists the elements of the time bucket specified by the index
	 * 
	 * @param index the bucket to be listed
	 * @return a String representation of the time bucket's elements
	 */
	public String listInterval(int index) {
		StringBuilder stringBuilder = new StringBuilder();
		String string;
		TreeSet<Double> auxSet = new TreeSet<>(data.get(index));
		Double auxDouble = 0.00;
		DecimalFormat df = new DecimalFormat("#.00",
				new DecimalFormatSymbols(Locale.US));

		while (!auxSet.isEmpty()) {
			auxDouble = auxSet.pollFirst();
			stringBuilder.append(df.format(auxDouble));
			stringBuilder.append(" ");
		}

		string = stringBuilder.toString();
		return string;
	}

}

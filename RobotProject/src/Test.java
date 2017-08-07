

import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.Sound;

/**
 * This is the main Class. Requires connection, initiates the threads and
 * controls the robot class for one brick at a time.
 * 
 * @author Christos Liontos
 * 
 */
public class Test {

	private static int[][] distances;

	/**
	 * Set distances is used by the translateCoordinates Class to pass the
	 * distances to the Main class
	 * 
	 * @param array
	 */
	public static void setDistances(int[][] array) {
		distances = array;
	}

	public static void main(String[] args) throws IOException {

		ColourSensor col = new ColourSensor();
		BackSonicSensor fs = new BackSonicSensor();
		RearSonicSensor rs = new RearSonicSensor();
		// Start the threads
		col.start();
		fs.start();
		rs.start();
		ReceiveData pc = new ReceiveData();
		Robot robot = new Robot();

		// Create an array with zeros
		distances = new int[10][10];
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances.length; j++) {
				distances[i][j] = 0;
			}
		}

		pc.connect();

		// For each pair of coordinates place a brick and return back
		for (int i = 0; i < distances.length; i++) {
			robot.placeBrick(distances[i][0], distances[i][1]);
			robot.returnToDispatchPoint();
			if (i != distances.length - 1){
			Sound.twoBeeps();
			}else{
				//StarWarsThread music = new StarWarsThread();
			}
		}

		Button.waitForPress();
	}

}

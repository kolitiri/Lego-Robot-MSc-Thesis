
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;
import lejos.util.TextMenu;

/**
 * This class is used by the NXT to receive the pointsArray from the PC via USB
 * or BT PC: Initiator NXT: Receiver
 * 
 */
public class ReceiveData {

	private ArrayList<Integer> array;

	public void connect() throws IOException {

		TranslateCoordinates translator = new TranslateCoordinates();
		// Create an arrayList to store the coordinates
		array = new ArrayList<Integer>();
		// Create an array to store the selection options
		String[] connectionStrings = new String[] { "Bluetooth", "USB" };
		// Create a menu with the above options
		TextMenu connectionMenu = new TextMenu(connectionStrings, 1,
				"Tranfer Type");
		// Create the type of connections (BT or USB)
		NXTCommConnector[] connectors = { Bluetooth.getConnector(),
				USB.getConnector() };
		// Get the selected connection option from the menu
		int connectionType = connectionMenu.select();

		// Initiate receiving procedure
		while (true) {

			LCD.clear();
			LCD.drawString("Waiting", 0, 0);

			// Wait for PC to initiate a connection either by BT or by USB
			NXTConnection btc = connectors[connectionType].waitForConnection(0,
					NXTConnection.PACKET);
			LCD.clear();

			// Open the input stream to receive the first part of data (the
			// first element which is the number of selected squares)
			DataInputStream dis = btc.openDataInputStream();

			// Get the first first element
			int numberOfPoints = dis.readInt();
			System.out.println("Total Points: " + numberOfPoints);
			// Close the stream
			dis.close();
			// Open a new input stream to send the remaining data.
			DataInputStream dis2 = btc.openDataInputStream();

			for (int i = 0; i < numberOfPoints * 2; i++) {

				// The xArray elements will be stored to the first
				// pointsArray.size/2 positions
				// The yArray elements will be stored to the last
				// pointsArray.size/2 positions
				array.add(dis.readInt());

			}

			// Close streams
			dis2.close();
			// Close connection
			btc.close();

			// Sent the array to the TranslateCoordinates class to translate
			// into distances
			translator.setArray(array);

			break;

		}

	}

}

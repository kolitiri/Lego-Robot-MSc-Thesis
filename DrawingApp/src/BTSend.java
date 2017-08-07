
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

/**
 * This class is used to send the pointsArray to the NXT via BlueTooth
 * 
 * @author Christos Liontos
 */
public class BTSend {
	// Declares an ArrayList
	private static ArrayList<Integer> pointsArray;

	/**
	 * Constructor. Creates a ArrayList to store: the number of the selected
	 * squares (xArray or yArray size) the xArray elements the yArray elements
	 */
	public BTSend() {
		pointsArray = new ArrayList<Integer>();
	}

	/**
	 * The send method sends the pointsArray to the NXT
	 */
	public void send() {
		NXTConnector conn = new NXTConnector();

		conn.addLogListener(new NXTCommLogListener() {
			public void logEvent(String message) {
				System.out.println("BTSend Log.listener: " + message);
			}

			public void logEvent(Throwable throwable) {
				System.out.println("BTSend Log.listener - stack trace: ");
				throwable.printStackTrace();
			}
		});

		// Connect to any NXT over BlueTooth
		boolean connected = conn.connectTo("btspp://");

		if (!connected) {
			System.err.println("Failed to connect to any NXT by BT");
			System.exit(1);
		}

		// Create the input, output objects to be used to send the data
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

		// Firstly we will dispatch the first element which is the number of the
		// selected squares
		try {
			dos.writeInt(pointsArray.get(0));
			dos.flush();

		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}

		// We close the connection and create a second output object to
		// re-initialize the procedure
		try {
			dos.close();
		} catch (IOException ioe) {
			System.out.println("IOException closing connection:");
			System.out.println(ioe.getMessage());
		}

		System.out.println("Number of points: " + pointsArray.get(0));

		// Create a second output object
		DataOutputStream dos2 = new DataOutputStream(conn.getOutputStream());

		// Now we send the elements starting from the second one
		// xArray elements and then yArray elements
		for (int i = 1; i < pointsArray.size(); i++) {
			try {
				dos2.writeInt(pointsArray.get(i));
				dos2.flush();
			} catch (IOException ioe) {
				System.out.println("IO Exception writing bytes:");
				System.out.println(ioe.getMessage());
				break;
			}
		}
		// Close the connections
		try {
			dos2.close();
			conn.close();
		} catch (IOException ioe) {
			System.out.println("IOException closing connection:");
			System.out.println(ioe.getMessage());
		}
	}

	/**
	 * This method is invoked by the DrawinPanel class when the upload button is
	 * clicked. Passes the xArray and yArray in this class.
	 */
	public void getArray(ArrayList<Integer> xArray, ArrayList<Integer> yArray) {

		// The first element inside the pointsArray (pointsArray(0)) will be the
		// size of the xArray or yArray (it is the same).
		// Thus, this element informs us about how many squares have been
		// selected. It could have been pointsArray.size()/2!!!
		BTSend.pointsArray.add(xArray.size());

		// Starting from the second element (pointsArray(1)) we store the
		// x-coordinates from the xArray.
		for (int i = 0; i < xArray.size(); i++) {
			pointsArray.add(xArray.get(i));
		}

		// Then we add the remaining y-coordinates from yArray.
		for (int i = 0; i < yArray.size(); i++) {
			pointsArray.add(yArray.get(i));
		}
		// Thus pointsArray has: the size of the x or y arrays in the first
		// position
		// the xArray elements from 1 to xArray.size()
		// the yArray elements from pointsArray.size() - xArray.size() to
		// pointsArray.size()

		// Invoke the send method to initialize the sending procedure
		send();

	}
}

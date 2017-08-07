
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

/**
 * This class is used to send the pointsArray to the NXT via USB The methods
 * work the same as in BTSend class
 * 
 * @author Christos Liontos
 * 
 */
public class USBSend {

	private static ArrayList<Integer> pointsArray;

	public USBSend() {
		pointsArray = new ArrayList<Integer>();
	}

	public void send() {
		NXTConnector conn = new NXTConnector();

		conn.addLogListener(new NXTCommLogListener() {

			public void logEvent(String message) {
				System.out.println("USBSend Log.listener: " + message);

			}

			public void logEvent(Throwable throwable) {
				System.out.println("USBSend Log.listener - stack trace: ");
				throwable.printStackTrace();

			}

		});
		boolean connected = conn.connectTo("usb://");

		if (!connected) {
			System.err.println("No NXT found using USB");
			System.exit(1);
		}

		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

		try {
			dos.writeInt(pointsArray.get(0));
			dos.flush();

		} catch (IOException ioe) {
			System.out.println("IO Exception writing bytes:");
			System.out.println(ioe.getMessage());
		}

		try {
			dos.close();
		} catch (IOException ioe) {
			System.out.println("IOException closing connection:");
			System.out.println(ioe.getMessage());
		}

		System.out.println("Number of points: " + pointsArray.get(0));

		DataOutputStream dos2 = new DataOutputStream(conn.getOutputStream());

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

		try {
			dos2.close();
			conn.close();
		} catch (IOException ioe) {
			System.out.println("IOException closing connection:");
			System.out.println(ioe.getMessage());
		}
	}

	public void getArray(ArrayList<Integer> xArray, ArrayList<Integer> yArray) {

		USBSend.pointsArray.add(xArray.size());

		for (int i = 0; i < xArray.size(); i++) {
			pointsArray.add(xArray.get(i));
		}

		for (int i = 0; i < yArray.size(); i++) {
			pointsArray.add(yArray.get(i));
		}

		for (int i = 0; i < pointsArray.size(); i++) {
		}

		send();
		System.out.println("run");

	}
}
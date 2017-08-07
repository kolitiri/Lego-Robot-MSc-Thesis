

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * This is the thread that controls the Back Ultrasonic Sensor
 * @author Christos Liontos
 *
 */
public class BackSonicSensor extends Thread{
	
	private UltrasonicSensor backSensor;
	public static int distance;
	
	public BackSonicSensor(){
		backSensor = new UltrasonicSensor(SensorPort.S2);
	}
	
	public void run()
    {
        do {
        	BackSonicSensor.distance = backSensor.getDistance();
        } while (!isInterrupted());
    }
	
	public static int getDistance()
    {
        return distance;
    }

}

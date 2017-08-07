

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * This is the thread that controls the Rear UltraSonic Sensor
 * @author Christos Liontos
 *
 */
public class RearSonicSensor extends Thread {

	private UltrasonicSensor rearSensor;
	public static int distance;
	
	public RearSonicSensor(){
		rearSensor = new UltrasonicSensor(SensorPort.S1);
		//rearSensor.continuous();
	}
	
	public void run()
    {
        do {
        	RearSonicSensor.distance = rearSensor.getDistance();
        } while (!isInterrupted());
    }
	
	public static int getDistance()
    {
        return distance;
    }
}

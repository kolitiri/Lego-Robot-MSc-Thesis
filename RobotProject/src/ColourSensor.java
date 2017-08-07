

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;

/**
 * This is the thread that controls the Color Sensor
 * @author Christos Liontos
 *
 */
public class ColourSensor extends Thread{
	
	private ColorSensor colorSensor;
	public static int ID;
	
	public ColourSensor(){
		colorSensor = new ColorSensor(SensorPort.S3);
	}
	
	public void run()
    {
        do {
        	ColourSensor.ID = colorSensor.getColorID();
        } while (!isInterrupted());
    }

	
	public static int getID()
    {
        return ID;
    }
	
	
	

}

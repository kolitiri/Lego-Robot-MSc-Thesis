

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * This is the thread class that controls the brick placement
 * 
 * @author Christos Liontos
 * 
 */
public class Robot {

	// In order for a square to be found we should provide its distance from
	// back and rear walls

	// private UltrasonicSensor rearSensor;
	// private UltrasonicSensor frontSensor;
	private DifferentialPilot pilot;
	// private ColorSensor colorSensor;

	private int rotateSpeed = 70;
	private int travelSpeed = 6;
	private int approachingSpeed = 1;
	// Giving a 200 value in the degree of rotation seems to
	// rotate the robot for actual 90 degrees
	private int nintyDegrees = 200;
	// The X-coordinate of the square (modifications should be applied)
	private int desiredDistFromRear_XCoordinate = 0;
	// This distance should be maintained to keep the robot in a straight-like
	// line
	// It will be slightly different from the desiredDistFromRear. It depends on
	// the
	// place the rear UltraSonic sensor is placed. It can be constant like
	// desiredDistFromRear+10
	// where 10 is the difference between the position of the two sensors
	private int distToMaintain = 0;
	// The Y-coordinate of the square (modifications should be applied)
	private int desiredDistFromFront_YCoordinate = 0;
	// Values of each color. They are by default set by the sensor's
	// specifications
	private int red = 0;
	private int blue = 2;
	// private int yellow = 3;
	private int black = 7;
	private int white = 6;

	// Constructor
	public Robot() {
		pilot = new DifferentialPilot(5f, 5f, Motor.A, Motor.C, false);
		pilot.setRotateSpeed(rotateSpeed);
		pilot.setTravelSpeed(travelSpeed);
		Motor.B.setSpeed(150);
	}

	/**
	 * This method is responsible for placing each brick. Starts from the
	 * dispatch point. Moves the robot forward until a desired distance is
	 * reached (X-coordinate of square). Turns 90 degrees. Moves forward until a
	 * desired distance is reached (Y-coordinate)
	 */
	public void placeBrick(int x, int y) {

		this.desiredDistFromRear_XCoordinate = x;
		this.desiredDistFromFront_YCoordinate = y;
		this.distToMaintain = (desiredDistFromRear_XCoordinate + 3);
		System.out.println("X: " + desiredDistFromRear_XCoordinate + "- Y: "
				+ desiredDistFromFront_YCoordinate + "- M: " + distToMaintain);

		int temporaryRearDist = 0;
		int temporaryBackDist = 0;
		int colorID;
		boolean comingFromBlack = true;
		boolean comingFromRed = false;

		pilot.setTravelSpeed(travelSpeed);

		// Lift the fork
		Motor.B.rotate(300);

		// Rotate 180 degrees
		pilot.rotate(2 * nintyDegrees);

		// While on the line, move until desired distance is reached
		temporaryBackDist = BackSonicSensor.distance;

		while (temporaryBackDist < desiredDistFromRear_XCoordinate) {

			colorID = ColourSensor.ID;
			while (colorID == red) {
				if (comingFromBlack) {
					pilot.steer(-40);
					comingFromBlack = false;
				} else if (comingFromRed) {
					pilot.steer(40);
					comingFromRed = false;
				}

				temporaryBackDist = BackSonicSensor.distance;
				if (temporaryBackDist == 255) {
					temporaryBackDist = 200;
				} else if (temporaryBackDist == desiredDistFromRear_XCoordinate) {
					break;
				}
				colorID = ColourSensor.ID;

			}

			colorID = ColourSensor.ID;
			while (colorID != red) {
				if (colorID == black) {
					pilot.steer(40);
					comingFromBlack = true;
				} else if (colorID == white) {
					pilot.steer(-40);
					comingFromRed = true;
				}

				temporaryBackDist = BackSonicSensor.distance;
				if (temporaryBackDist >= desiredDistFromRear_XCoordinate) {
					break;
				} else if (temporaryBackDist == 255) {
					temporaryBackDist = 200;
				}
				colorID = ColourSensor.ID;
				temporaryBackDist = BackSonicSensor.distance;
			}
			temporaryBackDist = BackSonicSensor.distance;
		}
		pilot.stop();

		// Rotate 90 Degrees to face the grid
		pilot.rotate(nintyDegrees);
		
		pilot.setTravelSpeed(travelSpeed + 1);

		// Go forward, keeping a specific distance from rear wall. If the
		// distance
		// changes make modifications until you maintain it again
		temporaryRearDist = RearSonicSensor.distance;
		temporaryBackDist = BackSonicSensor.distance;
		while (temporaryBackDist < desiredDistFromFront_YCoordinate) {
			// If you go straight, means that you maintain the distance
			while (temporaryRearDist == distToMaintain
					&& temporaryBackDist < desiredDistFromFront_YCoordinate) {
				pilot.forward();
				temporaryRearDist = RearSonicSensor.distance;
				temporaryBackDist = BackSonicSensor.distance;
				if (temporaryBackDist == 255) {
					temporaryBackDist = 200;
				}
			}

			// If your direction changes and distance is lost it means
			// you no longer go straight. Thus steer left or right until
			// you find this distance again
			while (temporaryRearDist != distToMaintain
					&& temporaryBackDist < desiredDistFromFront_YCoordinate) {
				if (temporaryRearDist > distToMaintain) {
					pilot.steer(10);
				} else if (temporaryRearDist < distToMaintain) {
					pilot.steer(-10);
				} else if (temporaryBackDist >= desiredDistFromFront_YCoordinate) {
					break;
				}
				temporaryRearDist = RearSonicSensor.distance;
				temporaryBackDist = BackSonicSensor.distance;
				if (temporaryBackDist == 255) {
					temporaryBackDist = 200;
				}
			}
			temporaryRearDist = RearSonicSensor.distance;
			temporaryBackDist = BackSonicSensor.distance;
		}
		pilot.stop();

		// If you overshoot the required distance travel back until you fix it
		temporaryBackDist = BackSonicSensor.distance;
		if (temporaryBackDist > desiredDistFromFront_YCoordinate) {
			pilot.setTravelSpeed(approachingSpeed);
			while (temporaryBackDist > desiredDistFromFront_YCoordinate) {
				pilot.backward();
				temporaryBackDist = BackSonicSensor.distance;
			}
			pilot.stop();
		}
		Motor.B.rotate(-300);
	}

	/**
	 * This method moves the robot back to the dispatch position
	 */
	public void returnToDispatchPoint() {

		int colorID;
		int temporaryRearDist = 0;
		int temporaryBackDist = 0;
		boolean comingFromBlack = true;
		boolean comingFromRed = false;

		pilot.setTravelSpeed(travelSpeed + 1);

		colorID = ColourSensor.ID;
		temporaryRearDist = RearSonicSensor.distance;
		while (colorID != red) {
			// If you go straight, means that you maintain the distance
			while (temporaryRearDist == distToMaintain && colorID != red) {
				pilot.backward();
				colorID = ColourSensor.ID;
				temporaryRearDist = RearSonicSensor.distance;
				temporaryBackDist = BackSonicSensor.distance;
				if (temporaryBackDist < 18) {
					break;
				}
			}

			// If your direction changes and distance is lost it means
			// you no longer go straight. Thus steer left or right until
			// you find this distance again
			colorID = ColourSensor.ID;
			while (temporaryRearDist != distToMaintain && colorID != red) {
				if (temporaryRearDist > distToMaintain) {
					pilot.steerBackward(10);
				} else if (temporaryRearDist < distToMaintain) {
					pilot.steerBackward(-10);
				} else if (colorID == red) {
					break;
				}
				colorID = ColourSensor.ID;
				temporaryRearDist = RearSonicSensor.distance;
				temporaryBackDist = BackSonicSensor.distance;
				if (temporaryBackDist < 18) {
					break;
				}
			}
			colorID = ColourSensor.ID;
			temporaryRearDist = RearSonicSensor.distance;
			temporaryBackDist = BackSonicSensor.distance;
			if (temporaryBackDist < 18) {
				break;
			}
		}

		pilot.stop();
		pilot.rotate(nintyDegrees);
		pilot.setTravelSpeed(travelSpeed);


		colorID = ColourSensor.ID;
		while (colorID != blue) {

			colorID = ColourSensor.ID;
			while (colorID == red) {
				if (comingFromBlack) {
					pilot.steer(40);
					comingFromBlack = false;
				} else if (comingFromRed) {
					pilot.steer(-40);
					comingFromRed = false;
				}

				colorID = ColourSensor.ID;

			}

			colorID = ColourSensor.ID;
			while (colorID != red) {
				if (colorID == black) {
					pilot.steer(-40);
					comingFromBlack = true;
				} else if (colorID == white) {
					pilot.steer(40);
					comingFromRed = true;
				}

				if (colorID == blue) {
					break;
				}
				colorID = ColourSensor.ID;
			}
			colorID = ColourSensor.ID;

		}

		pilot.stop();

	}

}

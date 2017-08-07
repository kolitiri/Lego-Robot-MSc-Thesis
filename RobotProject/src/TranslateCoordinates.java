

import java.awt.Point;
import java.util.ArrayList;

/**
 * This class translates the square coordinates (for example (1,1) into
 * distances (50cm , 50cm))
 * 
 * @author Christos
 * 
 */

public class TranslateCoordinates {

	// The array that comes from the BTReceive
	private ArrayList<Integer> array;
	// This array holds the coordinate indexes of each selected square
	private ArrayList<Point> pointsArray;
	// This 2D array holds the specific distances of each square.
	// Distances from rear wall are saved in the first column (j=0)
	// Distances from front wall are saved in the second column (j=1)
	// Thus each row contains distances from rear and front wall for the
	// (i+1)-square (for i=0 ---> i+1 = first square)
	private int[][] distances;
	private Point point;
	// X and Y distances cannot be smaller than 50 cm or greater than 150 cm
	private int minX = 50;
	private int minY = 41;

	// Constructor
	public TranslateCoordinates() {
		array = new ArrayList<Integer>();
		pointsArray = new ArrayList<Point>();
	}

	// Method sets the array variable
	public void setArray(ArrayList<Integer> array) {
		this.array = array;
		createPoints();
	}

	// Create a points arrayList first to keep the pairs together
	public void createPoints() {

		int n = 0;
		for (int i = 0; i < array.size() / 2; i++) {
			point = new Point(array.get(i), array.get(((array.size() / 2)) + n));
			pointsArray.add(point);
			n = n + 1;
		}

		sortPoints();
	}

	// This method sorts the pointsArray. The robot has to place the bricks from
	// top to bottom and right to left to avoid confusion.
	public void sortPoints() {
		Point bigger = null;
		Point smaller = null;
		boolean sign = true;

		while (sign) {
			sign = false;

			for (int i = 0; i < pointsArray.size() - 1; i++) {
				// Sort by bigger Y (From top to bottom)
				if (pointsArray.get(i).getY() < pointsArray.get(i + 1).getY()) {
					smaller = pointsArray.get(i);
					bigger = pointsArray.get(i + 1);
					pointsArray.set(i, bigger);
					pointsArray.set(i + 1, smaller);
					sign = true;
					// If the points have the same Y, sort by smaller X (from
					// right to left)
				} else if (pointsArray.get(i).getY() == pointsArray.get(i + 1)
						.getY()) {
					if ((pointsArray.get(i).getX() > pointsArray.get(i + 1)
							.getX())) {
						bigger = pointsArray.get(i);
						smaller = pointsArray.get(i + 1);
						pointsArray.set(i, smaller);
						pointsArray.set(i + 1, bigger);
						sign = true;
					}
				}
			}
		}

		translate();

	}

	// Translate the coordinates in distances
	// Save those distances in the distances 2D array
	// (1,1) square has: x=1 , y=1
	// Distance from rear = 50
	// Distance from front 150
	public void translate() {
		distances = new int[pointsArray.size()][2];
		int distanceX = 0;
		int distanceY = 0;
		int x = 0;
		int y = 0;

		for (int i = 0; i < pointsArray.size(); i++) {

			x = (int) pointsArray.get(i).getX();
			y = (int) pointsArray.get(i).getY();

			if (x == 1) {
				distanceX = minX + 0;
			} else if (x == 2) {
				distanceX = minX + 10;
			} else if (x == 3) {
				distanceX = minX + 20;
			} else if (x == 4) {
				distanceX = minX + 30;
			} else if (x == 5) {
				distanceX = minX + 40;
			} else if (x == 6) {
				distanceX = minX + 50;
			} else if (x == 7) {
				distanceX = minX + 60;
			} else if (x == 8) {
				distanceX = minX + 70;
			} else if (x == 9) {
				distanceX = minX + 80;
			} else if (x == 10) {
				distanceX = minX + 90;
			}

			if (y == 1) {
				distanceY = minY + 0;
			} else if (y == 2) {
				distanceY = minY + 10;
			} else if (y == 3) {
				distanceY = minY + 20;
			} else if (y == 4) {
				distanceY = minY + 30;
			} else if (y == 5) {
				distanceY = minY + 40;
			} else if (y == 6) {
				distanceY = minY + 50;
			} else if (y == 7) {
				distanceY = minY + 60;
			} else if (y == 8) {
				distanceY = minY + 70;
			} else if (y == 9) {
				distanceY = minY + 80;
			} else if (y == 10) {
				distanceY = minY + 90;
			}

			distances[i][0] = distanceX;
			distances[i][1] = distanceY;
		}

		// Send distances to the main class.
		Test.setDistances(distances);

	}

}

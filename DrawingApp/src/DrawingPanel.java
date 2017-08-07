import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is used as a canvas to draw on it. It is hooked to the
 * hookingPanel located in the center of the Frame
 * 
 * @author Christos Liontos
 * 
 */
public class DrawingPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// Declare a USBSend and a BTSend instance
	private USBSend USBsender;
	private BTSend BTsender;

	private SquareDrawing squareDrawing;

	// Variables to store the coordinates the mouse was pressed
	private int xPressed, yPressed;
	// True if it is the first time we draw on the canvas
	private boolean firstTime = false;
	// True if the pattern is cleared
	private boolean patternCleared = true;

	private BufferedImage buffer = null;
	private Graphics2D g2Buffer;
	private Dimension d;

	// Variables that set the default height and length of each square
	private int xGrid = 50;
	private int yGrid = 50;

	private Color currentColor;

	// Declare an arrayList that will save the selected Points (pairs of X,Y
	// coordinates)
	private ArrayList<Integer> xArray;
	private ArrayList<Integer> yArray;
	private ArrayList<Point> pointsArray;
	private Point point;
	// Declare a String to store the method selected by the radio buttons
	private String method = "";

	// Constructor
	public DrawingPanel() {

		// Create xArray to store the x-coordinates of the selected squares.
		// xArray(0) is the x-coordinate of the first Square
		xArray = new ArrayList<Integer>();
		// Create yArray to store the y-coordinates of the selected squares.
		// yArray(2) is the y-coordinate of the third Square
		yArray = new ArrayList<Integer>();
		// Create an ArrayList to store the points(coordinates in pairs). We
		// need this list to ckeck if previous pairs of values
		// already exist (squares that have been selected. We could use only the
		// points ArrayList since it is more comfortable.
		// However, since we cannot sent Objects like points via the BT or USB
		// connection, we have to stick to the xArray, yArray,
		// which we will finally merge into a new one before start sending the
		// values
		pointsArray = new ArrayList<Point>();

		// Create a BTSend and a USBSend object
		BTsender = new BTSend();
		USBsender = new USBSend();

		// Mouse listener to control the selection of squares via the mouse
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// When mouse button is pressed the X,Y coordinates are imported
				// to xPressed and yPressed
				xPressed = arg0.getX();
				yPressed = arg0.getY();
				// Since it is the first time drawing firsTime = true
				firstTime = true;
				repaint();
			}
		});
	}

	/**
	 * This method paints/clears the buffer white if it is not null
	 */
	public void clear() {
		if (g2Buffer != null) {
			this.g2Buffer.setColor(Color.white);
			this.g2Buffer.fillRect(0, 0, d.width, d.height);
			repaint();
			// Since it will not be the first time to draw (clear has already
			// happened once) firstTime should turn to false
			firstTime = false;
			patternCleared = true;
			// Every time the grid is being cleared the ArrayList is being
			// cleared too
			xArray.clear();
			yArray.clear();
			pointsArray.clear();
			ProjectFrame.emptyLabelNum.setText("0");
		}
	}

	/**
	 * This method is responsible for drawing the squares according to the
	 * specified grid length and height
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (this.buffer == null) {
			this.d = getSize();
			this.buffer = new BufferedImage(this.d.width, this.d.height,
					BufferedImage.TYPE_INT_ARGB_PRE);
			this.g2Buffer = (Graphics2D) this.buffer.getGraphics();
			clear();
		}

		// Invoke the drawGrid method to draw the grid
		drawGrid(g2Buffer);
		g2Buffer.setColor(currentColor);
		// Creates a LineDrawing object and passes in the constructor the width
		// and height of the squares

		squareDrawing = new SquareDrawing(xGrid, yGrid);

		// If it is the first time to draw on a square
		if (firstTime) {
			// Create random colors to fill the squares
			Random random = new Random();
			float R = random.nextFloat();
			float G = random.nextFloat();
			float B = random.nextFloat();
			currentColor = new Color(R, G, B);

			this.g2Buffer.setColor(currentColor);
			// If the Grid has been cleared
			if (patternCleared == true) {
				// If pattern has not been cleared we cannot draw extra cubes
				// since we have already uploaded the pattern
				// Invoke the draw method
				squareDrawing.draw(xPressed, yPressed, g2Buffer);
				// Invoke setCoordinates method to store the Point
				setCoordinates(xPressed, yPressed);
			}
		}
		g.drawImage(this.buffer, 0, 0, this);
	}

	/**
	 * This method will translate the x,y coordinates of the mouse and scale
	 * them to squares coordinates Each time the mouse is pressed the exact x,y
	 * coordinates are saved in xPressed and yPressed If x is between 0 and 50
	 * then the corresponding square should be in the first column counting from
	 * the left (Grid x=1). If y is between 51 and 100 then the corresponding
	 * square should be in the second line counting from the upper left corner
	 * (Grid y=9). The intersection of these two means that the mouse was
	 * clicked somewhere inside the (1,1) square.
	 * 
	 * @param xCoordinate
	 * @param yCoordinate
	 */
	public void setCoordinates(int xCoordinate, int yCoordinate) {
		int finalX = 0, finalY = 0;

		if (xCoordinate < 50) {
			finalX = 1;
		} else if (xCoordinate < 100) {
			finalX = 2;
		} else if (xCoordinate < 150) {
			finalX = 3;
		} else if (xCoordinate < 200) {
			finalX = 4;
		} else if (xCoordinate < 250) {
			finalX = 5;
		} else if (xCoordinate < 300) {
			finalX = 6;
		} else if (xCoordinate < 350) {
			finalX = 7;
		} else if (xCoordinate < 400) {
			finalX = 8;
		} else if (xCoordinate < 450) {
			finalX = 9;
		} else if (xCoordinate <= 500) {
			finalX = 10;
		}

		if (yCoordinate < 50) {
			finalY = 10;
		} else if (yCoordinate < 100) {
			finalY = 9;
		} else if (yCoordinate < 150) {
			finalY = 8;
		} else if (yCoordinate < 200) {
			finalY = 7;
		} else if (yCoordinate < 250) {
			finalY = 6;
		} else if (yCoordinate < 300) {
			finalY = 5;
		} else if (yCoordinate < 350) {
			finalY = 4;
		} else if (yCoordinate < 400) {
			finalY = 3;
		} else if (yCoordinate < 450) {
			finalY = 2;
		} else if (yCoordinate <= 500) {
			finalY = 1;
		}

		// Each time a square is selected x,y coordinates of the square create a
		// new point (x.y)
		point = new Point(finalX, finalY);

		// If the point does not already exist in the ArrayList it is added. In
		// addition, since we need
		// an xArray and a yArray we also add those values to the lists
		// accordingly. The points arrayList
		// is only used to be sure that we do not add duplicate points-squares.
		if (pointsArray.contains(point) == false) {
			pointsArray.add(point);
			xArray.add(finalX);
			yArray.add(finalY);
		}

		String num = Integer.toString(xArray.size());
		ProjectFrame.emptyLabelNum.setText(num);

		for (int i = 0; i < pointsArray.size(); i++) {
			System.out.println((int) pointsArray.get(i).getX() + ","
					+ (int) pointsArray.get(i).getY());
		}
	}

	/**
	 * Method invoked by the ProjectFrame class. Sets the String method
	 * according to the selected method by the radio buttons
	 */
	public void setMethod(String meth) {
		this.method = meth;
	}

	/**
	 * Method that creates a USBSend or a BTSend instance accordingly and
	 * invokes the getArray method to send the final xArray, yArray to the UBS
	 * or BT classes. The method is invoked when upload button is clicked.
	 */
	public void sendCoordinates() {

		// If method.equals("BT"), BT radio button is selected
		if (method.equals("BT")) {
			BTsender = new BTSend();
			// Send the arrays to the BTSend class
			BTsender.getArray(xArray, yArray);
			patternCleared = false;
			// If method.equals("BT"), BT radio button is selected
		} else if (method.equals("USB")) {
			USBsender = new USBSend();
			// Send the arrays to the USBSend class
			USBsender.getArray(xArray, yArray);
			patternCleared = false;
		}
	}

	/**
	 * This method draws a grid to the panel with w-width and h-height
	 * 
	 * @param g2Buffer
	 */
	public void drawGrid(Graphics2D g2Buffer) {
		// Takes the width and height of the panel to draw the grid in it
		int w = this.getWidth();
		int h = this.getHeight();

		g2Buffer.setColor(Color.black);
		// Two for-loops to draw the horizontal and vertical lines of the grid
		for (int i = 0; i < w; i += xGrid) {
			g2Buffer.drawLine(i, 0, i, h);
		}
		for (int i = 0; i < h; i += yGrid) {
			g2Buffer.drawLine(0, i, w, i);
		}
	}

}

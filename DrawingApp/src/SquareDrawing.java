
import java.awt.Graphics2D;

/**
 * This class is used to paint black the selected squares
 * 
 * @author Christos Liontos
 * 
 */
public class SquareDrawing {

	// Point coordinates
	private float xPressed;
	private float yPressed;

	// Square height , width
	private int xGrid;
	private int yGrid;

	/**
	 * Constructor setting square width and height
	 * 
	 * @param newSquareWidth
	 * @param newSquareHeight
	 */
	public SquareDrawing(int newSquareWidth, int newSquareHeight) {
		this.xGrid = newSquareWidth;
		this.yGrid = newSquareHeight;
	}

	/**
	 * This method scales the X coordinate to fit a grid given a width for each
	 * cell of the grid
	 */
	private float scaletoXgrid(int x) {
		return (float) (((int) x) / xGrid) * xGrid;
	}

	/**
	 * This method scales the Y coordinate to fit a grid given a height for each
	 * cell of the grid
	 */
	private float scaletoYgrid(int y) {
		return (float) (((int) y) / yGrid) * yGrid;
	}

	/**
	 * This method sets the start points
	 * 
	 * @param x
	 * @param y
	 */
	public void setStartPoint(int x, int y) {
		this.xPressed = scaletoXgrid(x);
		this.yPressed = scaletoYgrid(y);
	}

	/**
	 * Draw method invokes the drawSquare and setStartPoint methods to paint the
	 * selected squares
	 * 
	 * @param x
	 * @param y
	 * @param g2
	 */
	public void draw(int x, int y, Graphics2D g2) {
		setStartPoint(x, y);
		drawSquare((int) this.xPressed, (int) this.yPressed, g2);
	}

	/**
	 * This method paints the pixels that belong to the square. It fills the
	 * square that has x-height and y-width
	 * 
	 * @param x
	 * @param y
	 * @param g2Buffer
	 */
	private void drawSquare(int x, int y, Graphics2D g2Buffer) {
		g2Buffer.fillRect(x, y, xGrid, yGrid);

	}

}

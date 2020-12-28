package maze;

// Importing the necessary libraries
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

// Maze class
public class Maze extends JPanel {
	private static final long serialVersionUID = 1L;

	// Setting the size of each cell(passage or wall) to 150 units	
	protected final int CELL_SIZE = 150;
	
	// Declaration of objects needed to initialize a random maze
	private MazeModel temp;
	private char[][] mazeModel;
	protected static Rectangle[][] mazeRect;
	protected static Color[][] mazeColour;
	protected ArrayList<Integer> passagesX;
	protected ArrayList<Integer> passagesY;
	protected ArrayList<Integer> wallsX;
	protected ArrayList<Integer> wallsY;
	private static ArrayList<Rectangle> walls;
	
	// Declaration of variables needed to update the current camera position of the player
	private static int cameraX;
	private static int cameraY;

	// Declaration of images needed for the maze
	BufferedImage wall, finishSquare, startSquare, coveredSquares, mazeBackground, healthBarCanvas;
	
	/**
	 * constructor
	 * pre: none
	 * post: A Maze object created.
	 */
	public Maze() {
		// Initializing the size of the maze and the colour of the maze to 25 by 25 because the maze will contain 25 rows and 25 columns
		mazeRect = new Rectangle[25][25];
		mazeColour = new Color[25][25];
		
		// Initializing the objects needed to store important information to be used in other classes
		passagesX = new ArrayList<Integer>();
		passagesY = new ArrayList<Integer>();
		wallsX = new ArrayList<Integer>();
		wallsY = new ArrayList<Integer>();
		walls = new ArrayList<Rectangle>();

		// This try and catch is used to make sure whenever an input or output operation is failed or interpreted, it will be caught
		try {
			// Initializing the images
			wall = ImageIO.read(this.getClass().getResource("/wall.png"));
			finishSquare = ImageIO.read(this.getClass().getResource("/finish.jpg"));
			startSquare = ImageIO.read(this.getClass().getResource("/start.jpg"));
			coveredSquares = ImageIO.read(this.getClass().getResource("/coveredSquares.jpg"));
			mazeBackground = ImageIO.read(this.getClass().getResource("/mazeBackground.png"));
			healthBarCanvas = ImageIO.read(this.getClass().getResource("/healthBarCanvas.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Setting the current camera x and y values to 0
		cameraX = 0;
		cameraY = 0;
		
		// Initializing a new MazeModel object called temp with 12 * 2 + 1 rows and 12 * 2 + 1 columns and initializing the 'mazeModel' object created in this class to temp
		// The reason it is 12 is because recursive division works by taking half of the maze each time and recursively calling the same function
		temp = new MazeModel(12, 12);
		mazeModel = temp.getMazeModel();

		// Starting the cells of the maze, the colour of the maze, and the size of the maze
		startMazeRect();
		colourMaze();
		setSize(5000, 5000);
		
		// Initializing the walls of the maze as data to be used for other classes and collision detection between the walls
		for (int i = 0; i < mazeRect.length; i++) {
			for (int j = 0; j < mazeRect[0].length; j++) {
				if (mazeColour[i][j] == Color.black) {
					// Adding 5 because of the thin line on the side of the screen. Adding 27
					walls.add(new Rectangle(mazeRect[i][j].x + 7, mazeRect[i][j].y + 27, mazeRect[i][j].width, mazeRect[i][j].height));
				}
			}
		}
	}

	/**
	 * Initializes each cell of the maze to the cell size
	 * pre: none
	 * post: The maze's cells have been initialized
	 */
	private void startMazeRect() {
		// These local variables will help locate where each Rectangle(cell) should be placed
		int curX = 0, curY = 0;
		
		// 'For' loop running from how many rows and columns in the maze
		for (int i = 0; i < mazeModel.length; i++) {
			for (int j = 0; j < mazeModel[0].length; j++) {
				// Creating a new Rectangle and saving it to our mazeRect object while also updating the current xValue for the next Rectangle to be placed
				mazeRect[i][j] = new Rectangle(curX, curY, CELL_SIZE, CELL_SIZE);
				curX += CELL_SIZE;
			}
			
			// Since each column in the current row have already been saved into the mazeRect object, we set the xValue back to 0 and add the cell size to the yValue
			// to allow for a new row of rectangles(cells) to be created and saved
			curX = 0;
			curY += CELL_SIZE;
		}
	}

	/**
	 * Initializes the colours of the maze
	 * pre: none
	 * post: The maze's colours have been initialized
	 */
	private void colourMaze() {
		// 'For' loops running from 0 to how many rows(r) and columns(c) in the maze
		for (int r = 0; r < mazeModel.length; r++) {
			for (int c = 0; c < mazeModel[0].length; c++) {
				
				// If the current index of the mazeModel is equal to '#', then it is a wall and the mazeColour will be set to black
				if (mazeModel[r][c] == '#') {
					mazeColour[r][c] = Color.black;
					
					// This if statement is to save the walls where the enemy peashooters cannot spawn in the Enemy subclass
					// The peashooters cannot be in the first row, last row, or the last column of the maze because they would never be shooting the player
					if (r != 0 && r != mazeModel.length - 1 && c != mazeModel[0].length - 1) {
						wallsX.add(mazeRect[r][c].x);
						wallsY.add(mazeRect[r][c].y);
					}
				} 
				// Else, the cell is not a wall, and the mazeColour will be set to white
				else {
					mazeColour[r][c] = Color.white;
					
					// These passagesX and passagesY objects will be saved for the location of the spike enemies in the Enemy Class
					passagesX.add(mazeRect[r][c].x);
					passagesY.add(mazeRect[r][c].y);
				}
			}
		}
	}

	/**
	 * Setting the change in the camera by subtracting the real x and y coordinates by the user's starting position
	 * pre: none
	 * post: Camera's coordinates have been updated
	 */
	public static void setLocationCanvas(int x, int y) {
		cameraX = x - (mazeRect[1][1].x + mazeRect[1][1].width / 2);
		cameraY = y - (mazeRect[1][1].y + mazeRect[1][1].height / 2);
	}	

	/**
	 * Colouring the walls of the maze with the image that has been set in the constructor
	 * pre: none
	 * post: Walls of the maze have been shown with the cobblestone image
	 */
	private void colourWalls(Graphics g) {
		// 'For' loop running from the amount of rows and columns in the maze
		for (int i = 0; i < mazeRect.length; i++) {
			for (int j = 0; j < mazeRect[i].length; j++) {
				// If the index of mazeColour is equal to black, then it is a wall, and a wall image will be drawn accordingly
				if (mazeColour[i][j] == Color.black) {
					// The "- cameraX" and "- cameraY" comes from the fact that we want the maze to move according to the location of the player
					g.drawImage(wall, mazeRect[i][j].x - cameraX, mazeRect[i][j].y - cameraY, CELL_SIZE, CELL_SIZE, null);
				}
			}
		}	
		
		// Drawing the starting square(top left) and the finish square(bottom right)
		g.drawImage(startSquare, mazeRect[1][1].x - cameraX, mazeRect[1][1].y - cameraY, CELL_SIZE, CELL_SIZE, null);
		g.drawImage(finishSquare, mazeRect[23][23].x - cameraX, mazeRect[23][23].y - cameraY, CELL_SIZE, CELL_SIZE, null);
	}
	
	/**
	 * OverPainting the current canvas to the background image for the maze, the passages squares as white squares, and the walls as minecraft cobblestones
	 * pre: none
	 * post: Current canvas has been updated
	 */
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		// I had help with Background image setting from: https://stackoverflow.com/questions/523767/how-to-set-background-image-in-java
		// The '900' and the '800' comes from the frame's length and width
		g.drawImage(mazeBackground, 0, 0, 900, 800, null);
		
		// 'For' loop running from the rows and columns of the maze
		for (int i = 0; i < mazeRect.length; i++) {
			for (int j = 0; j < mazeRect[i].length; j++) {
				// If the current index for the maze's colour is white, then we fill that current square(cell) as a white square
				if (mazeColour[i][j] == Color.white) {
					g.setColor(mazeColour[i][j]);
					g.fillRect(mazeRect[i][j].x - cameraX, mazeRect[i][j].y - cameraY, CELL_SIZE, CELL_SIZE);
				}
				// The colour blue represents the squares that the user have already covered
				// Else if the current index for the maze's colour is blue, we draw an image indicating that the user has covered this square 
				else if (mazeColour[i][j] == Color.blue) {
					g.drawImage(coveredSquares, mazeRect[i][j].x - cameraX, mazeRect[i][j].y - cameraY, CELL_SIZE, CELL_SIZE, null);
				}
			}
		}		
		
		// This method is used for colouring the walls of the maze
		colourWalls(g);
		
		// Drawing the canvas for the health bar
		g.drawImage(healthBarCanvas, 0, 0, 180, 50, null);
	}
	
	/**
	 * Returns the rectangle maze
	 * pre: none
	 * post: Rectangle maze returned
	 */
	public static Rectangle[][] getMaze() {
		return mazeRect;
	}
	
	/**
	 * Returns the walls of the maze
	 * pre: none
	 * post: Walls of the maze returned
	 */
	public static ArrayList<Rectangle> getWalls() {
		return walls;
	}
	
	/**
	 * Returns the colour of the maze's cells
	 * pre: none
	 * post: Colour of the maze's cells returned
	 */
	public static Color[][] getColor() {
		return mazeColour;
	}
	
	/**
	 * Returns the camera's x-value, or the offset in the x-value
	 * pre: none
	 * post: Camera's x-value returned
	 */
	public static int getCanvasX() {
		return cameraX;
	}
	
	/**
	 * Returns the camera's y-value, or the offset in the y-value
	 * pre: none
	 * post: Camera's y-value returned
	 */
	public static int getCanvasY() {
		return cameraY;
	}
}
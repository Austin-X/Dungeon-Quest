package player;

//Importing the necessary libraries
import javax.imageio.ImageIO;
import javax.swing.*;
import maze.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

// PlayerBehaviours Class, subclass of PlayerFrame which extends JFrame
public class PlayerBehaviours extends PlayerFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	// Declaration of constant variables that control the player's properties
	private final static int PLAYERSIZE = 50;
	private final static int PLAYER_VELOCITY = 1;

	// This image will be used as the player's character throughout the maze
	BufferedImage hero;

	// These variables are for the user's real x and y coordinates in the maze as well as their velocity
	protected int xValue, yValue;
	private int xVelocity, yVelocity;

	// These objects will fetch the mazeRect and mazeColour objects in the 'Maze' class by using their getter methods
	protected Rectangle[][] maze;
	private Color[][] mazeColour;

	// This will act as the user's hitbox to check for collisions
	private static Rectangle hitBox;

	// These variables are for the x and y coordinates for the user on the screen, not on the actual maze
	private int cameraX = 0, cameraY = 0;

	// This ArrayList will store all the data for the walls of the maze to be used later on for collision detection
	private ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	
	/**
	 * constructor
	 * pre: none
	 * post: A PlayerBehaviours Object created
	 */
	public PlayerBehaviours(JPanel canvas) {
		super(canvas);

		// Making sure that the 'hero' BufferedImage is sucessfully initialized
		try {
			hero = ImageIO.read(this.getClass().getResource("/hero.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Calling the class methods in the 'Maze' class to store information about the maze's appearance
		mazeColour = Maze.getColor();
		maze = Maze.getMaze();
		walls = Maze.getWalls();

		// Initializing the user's current real and virtual coordinates, their velocity, and their hitBox
		xValue = maze[1][1].x + maze[1][1].width / 2;
		yValue = maze[1][1].y + maze[1][1].height / 2;
		cameraX = maze[1][1].x + maze[1][1].width / 2;
		cameraY = maze[1][1].y + maze[1][1].height / 2;
		xVelocity = 0;
		yVelocity = 0;
		hitBox = new Rectangle(xValue, yValue, PLAYERSIZE, PLAYERSIZE);

		// Adding a keyListener to allow player movement when keys are pressed
		addKeyListener(this);
	}

	/**
	 * Updates the player's xVelocity and yVelocity based on the key that they press with the general WASD keys
	 * pre: none
	 * post: The player's xVelocity and yVelocity has been updated
	 */
	public void keyTyped(KeyEvent e) {
		// The general WASD movement keys
		if (e.getKeyChar() == 'd') {
			xVelocity = PLAYER_VELOCITY;
		} else if (e.getKeyChar() == 'a') {
			xVelocity = -PLAYER_VELOCITY;;
		} else if (e.getKeyChar() == 's') {
			yVelocity = PLAYER_VELOCITY;
		} else if (e.getKeyChar() == 'w') {
			yVelocity = -PLAYER_VELOCITY;;
		}
	}

	/**
	 * Updates the player's xVelocity and yVelocity based on the key that they press with the general arrow keys
	 * pre: none
	 * post: The player's xVelocity and yVelocity has been updated
	 */
	public void keyPressed(KeyEvent e) {
		// The general arrow movement keys
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            xVelocity = PLAYER_VELOCITY;;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            xVelocity = -PLAYER_VELOCITY;;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            yVelocity = PLAYER_VELOCITY;;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
            yVelocity = -PLAYER_VELOCITY;;
		}
	}

	/**
	 * Updates the player's xVelocity and yVelocity based on which key was released
	 * pre: none
	 * post: xVelocity set to 0 if the key released was d, a, the right arrow key, or the left arrow key.
	 * yVelocity set to 0 if the key released was s, w, the down arrow key, or the up arrow key
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'd' || e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)
			xVelocity = 0;
		else if (e.getKeyChar() == 's' || e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP)
			yVelocity = 0;
	}

	/**
	 * Checks if a player has visited a passage square, and if so, set the index of that square in mazeColour to blue to indicate covered
	 * pre: none
	 * post: mazeColour indexes changed according the amount of squares the player has covered
	 */
	public void visitedSquares() {
		
		// 'For' loops running from 0 to how many rows and columns in the maze
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				
				// If the mazeColour of the cell that the player has intersected is white (to indicate a passage), then set it to blue
				if (mazeColour[i][j] == Color.white && hitBox.intersects(maze[i][j])) {
					mazeColour[i][j] = Color.blue;
				}
			}
		}
	}

	public void movement() {

		// This 'if' statement is for if the user glitches outside the map
		if (xValue <= 0 || yValue <= 0 || xValue >= 3750 || yValue >= 3750) {
			xValue = maze[1][1].x + maze[1][1].width / 2;
			yValue = maze[1][1].y + maze[1][1].height / 2; 
		}
		
		// This counted loop is used for collision detecting between the player and the walls of the maze
		for (int i = 0; i < walls.size(); i++) {
			
			// If the user has intersected a wall
			if (hitBox.getBounds().intersects(walls.get(i).getBounds())) {
				
				// If the user was only moving left or right when they intersected
				if (yVelocity == 0) {
					
					// If the user was just going left when they intersect, they set the xValue of the player to the xValue of the wall + the width of the wall + 10
					if (xVelocity == -PLAYER_VELOCITY)
						xValue = walls.get(i).x + walls.get(i).width + 20;	
					
					// Else, the user has to be going to the right when they intersect, and therefore set the xValue of the player to the walls x-coordinate minus its width + 70
					else
						xValue = walls.get(i).x - walls.get(i).width + 70;
				}
				
				// If the user was only moving up or down when they intersected
				else if (xVelocity == 0) {
					
					// If the user was only going up
					if (yVelocity == -PLAYER_VELOCITY)
						yValue = walls.get(i).y + walls.get(i).height + 30;		
					
					// Else, the user has to be going down
					else 
						yValue = walls.get(i).y - walls.get(i).height + 70;
				}
				
				// This else statement will execute if the user had a xVelocity and yVelocity that was not equal to 0.
				else {
					
					// If the user had hit the bottom side of the wall's rectangle
					if (yValue + 30 >= walls.get(i).y + walls.get(i).height) {
						
						// If the user was going right and up
						if (xVelocity == PLAYER_VELOCITY && yVelocity == -PLAYER_VELOCITY) {
							xValue = hitBox.x + 5;
						} 
						
						// If the user was going left and up
						else if (xVelocity == -PLAYER_VELOCITY && yVelocity == -PLAYER_VELOCITY) {
							xValue = hitBox.x - 5;
						}
						yValue = hitBox.y + 25;
					}
					
					// If the user had hit the top side of the wall's rectangle
					else if (yValue <= walls.get(i).y + 30) {
						
						// If the user was going right and down
						if (xVelocity == PLAYER_VELOCITY && yVelocity == PLAYER_VELOCITY) {
							xValue = hitBox.x + 5;
						} 
						
						// If the user was going left and down
						else if (xVelocity == -PLAYER_VELOCITY && yVelocity == PLAYER_VELOCITY) {
							xValue = hitBox.x - 5;
						}
						yValue = hitBox.y - 25;
					}
					
					// If the user had hit the right side of the wall's rectangle
					else if (xValue + 30 >= walls.get(i).x + walls.get(i).width) {
						
						// If the user was going left and down
						if (xVelocity == -PLAYER_VELOCITY && yVelocity == PLAYER_VELOCITY) {
							yValue = hitBox.y + 5;
						} 
						
						// If the user was going left and up
						else if (xVelocity == -PLAYER_VELOCITY && yVelocity == -PLAYER_VELOCITY) {
							yValue = hitBox.y - 5;
						}
						xValue = hitBox.x + 25;
					}
					
					// If the user had hit the left side of the wall's rectangle
					else if (xValue <= walls.get(i).x + 30) {
						
						// If the user was going right and down
						if (xVelocity == PLAYER_VELOCITY && yVelocity == PLAYER_VELOCITY) {
							yValue = hitBox.y + 5;
						} 
						
						// If the user waz going right and up
						else if (xVelocity == PLAYER_VELOCITY && yVelocity == -PLAYER_VELOCITY) {
							yValue = hitBox.y - 5;
						}
						xValue = hitBox.x - 25;
						
					}
					// These if statements below are for the collision detection wall glitches
					else if (xVelocity == PLAYER_VELOCITY) {
						xValue = hitBox.x - 120;
						yValue = hitBox.y + 120;
					}
					else {
						xValue = hitBox.x + 120;
						yValue = hitBox.y + 120;
					}
				}
			}
		}
		xValue += xVelocity;
		yValue += yVelocity;
	}
	
	/**
	 * Sets the updated canvas of the maze, 
	 * pre: none
	 * post: Current canvas has been updated
	 */
	public void paint(Graphics g) {
		// Setting the real xValue and yValue for the maze to display
		Maze.setLocationCanvas(xValue, yValue);

		// Calling the paintComponents() method from MyFrame.
		// There is really no method called 'paintComponents()' there, but it is a default and invisible method, like toString()
		super.paintComponents(g);

		// Drawing the hero/player to the cameraX and cameraY values
		g.drawImage(hero, cameraX, cameraY, PLAYERSIZE, PLAYERSIZE, null);

		// The hitBox will be a rectangle that will replace the circle's dimensions when checking for collisions
		hitBox = new Rectangle(xValue, yValue, PLAYERSIZE, PLAYERSIZE);

		// This method moderates the movement of the player as well as their collisions
		movement();

		// This method checks all the squares that the player has visited, and if it has been visited, we mark it blue
		visitedSquares();
		
		repaint();
	}

	/**
	 * Returns the hitBox of the player
	 * pre: none
	 * post: Player's hitBox returned
	 */
	public static Rectangle getHitbox() {
		return hitBox;
	}
}
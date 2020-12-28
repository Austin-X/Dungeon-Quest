package maze;

// Importing the necessary libraries
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import dungeonQuest.Game;

// Enemy Class, subclass of Maze
public class Enemy extends Maze {
	private static final long serialVersionUID = 1L;
	
	// Declaration of variables associated to the health of the player
	private static int healthBar;
	private Color healthColour;
	private static int damage;
	
	// Delcaration of variables that moderate the size and amount of spikes
	private static final int SPIKE_SIZE = 30;
	private static int spikeCount;
	
	// Declaration of variables that moderate the size of the peashooter and peas(the bullets from the peashooters), the amount of peashooters,
	// the velocity of the peas, and the dissapear distance for the peas
	private static final int PEASHOOTER_SIZE = 100;
	private static final int PEA_SIZE = 20;
	private static int peashooterCount;
	private static double bulletVelocityX;
	private static final int DISSAPEAR_DISTANCE = 1000;
	
	// Declaration of variables that moderate the size, amount, and velocity of the spiders
	// The spiderCount is technically two times more because there are top and bottom spiders
	private static final int SPIDER_SIZE = 150;
	private static int spiderCount;
	private static int topSpiderVelocityY, bottomSpiderVelocityY;
	
	// Declaration of the objects needed to store the coordinates of the randomly generated enemies
	private static int[][] spikeCoordinates;
	private static int[][] peashooterCoordinates;
	private static int[][] peaCoordinates;
	private static int[][] topSpiderCoordinates;
	private static int[][] bottomSpiderCoordinates;

	// This variable is to be used to moderate how many seconds to draw "-10!", "-20!", or "-30!" depending on how much health you lost 
	private int time;
	
	// Declaration of enemy images
	BufferedImage spike, peashooter, spider;
	
	/**
	 * constructor
	 * pre: none
	 * post: An Enemy object created.
	 */
	// The first parameter is the spider count, the second parameter is the peashooter count, and the final one is the top and bottom spider count
	public Enemy(int spikeCn, int peashooterCn, int spiderCn) throws IOException {
		// Initializing the amount of spikes, peashooters, and spiders to what was passed in through as parameters
		spikeCount = spikeCn;
		peashooterCount = peashooterCn;
		spiderCount = spiderCn;
		
		// Initializing the enemy images
		spike = ImageIO.read(this.getClass().getResource("/spike.png"));
		peashooter = ImageIO.read(this.getClass().getResource("/peashooter.png"));
		spider = ImageIO.read(this.getClass().getResource("/spider.jpg"));

		// Intializing a 2D-array to store the x-coordinates and y-coordinates of the enemies
		// the first rectangle bracket is to determine how many of each enemy there are, and the '2' comes from the x-coordinates and y-coordinates of each enemy
		spikeCoordinates = new int[spikeCount][2];
		peashooterCoordinates = new int[peashooterCount][2];
		peaCoordinates = new int[peashooterCount][2];
		topSpiderCoordinates = new int[spiderCount][2];
		bottomSpiderCoordinates = new int[spiderCount][2];
		
		// Initializing the speed of the properties of the enemies created, as well as the player's health bar, the amount of damage received, 
		// the time to count the amount of damage you received, and the colour of the healthBar string
		bulletVelocityX = 0;
		topSpiderVelocityY = 0;
		bottomSpiderVelocityY = 0;
		healthBar = 100;
		damage = 0;
		time = 0;
		healthColour = Color.white;

		// This arraylist will keep the contained squares of spikes so they do not spawn in the same squares
		ArrayList<Integer> containedSquares = new ArrayList<Integer>();
		
		// This 'for' loop will generate the amount of spikes needed
		for (int i = 0; i < spikeCount; i ++) {
			// Initialing the variables needed to set the location for each spike.
			// Rand is used to determine which wall to set the spike in, randXExtra is to determine how much extra x-values to add, and randYExtra is to determine how much extra y-values to add
			int rand = 0, randXExtra = 0, randYExtra = 0;

			// Making sure no spikes are on the start square of the player
			while (rand == 0) {
				// Making sure there is not more than one spike in a passage square so the user can effectively dodge them
				while (containedSquares.contains(rand)) {
					
					// Randomizing the variables using Math.random() to ensure randomness of the spawn locations of the spikes
					rand = (int)(Math.random() * passagesX.size());
					randXExtra = (int)(Math.random() * super.CELL_SIZE);
					randYExtra = (int)(Math.random() * super.CELL_SIZE);
				}
				
				// If the extra x and y coordinates are more than the cell size + the spike size, then we center the spike in the passage square to ensure that the player can effectively dodge it
				if (passagesX.get(rand) + randXExtra > passagesX.get(rand) + CELL_SIZE - SPIKE_SIZE) randXExtra = (CELL_SIZE - SPIKE_SIZE) / 2;
				if (passagesY.get(rand) + randYExtra > passagesY.get(rand) + CELL_SIZE - SPIKE_SIZE) randYExtra =  (CELL_SIZE - SPIKE_SIZE) / 2;
				
				// Adding the square data to the containedSquares ArrayList
				containedSquares.add(rand);
			}
			
			// This is for the spike's x-coordinate, which is going to be in a passage square plus the extra xValues to ensure more randomness
			spikeCoordinates[i][0] = passagesX.get(rand) + randXExtra;
			
			// This is for the spike's y-coordinate, which is going to be in a passage square plus the extra yValues to ensure more randomness
			spikeCoordinates[i][1] = passagesY.get(rand) + randYExtra;
		}
		
		// This 'for' loop will generate the amount of peashooters needed
		for (int i = 0; i < peashooterCount; i ++) {
			// As mentioned before, rand is used to determine which wall to set the spike in, randXExtra is to determine how much extra x-values to add, and randYExtra is to determine how much extra y-values to add
			int rand = 0, randXExtra = 0, randYExtra = 0;
			
			// This 'while' statement prevents any peashooters from spawning the 'first' wall, which is the wall to the left of the spawn
			while (rand == 0) {
				
				// Randomizing the variables using Math.random() to ensure randomness of the spawn locations of the peashooters
			    rand = (int)(Math.random() * wallsX.size());
			    randXExtra = (int)(Math.random() * super.CELL_SIZE);
			    randYExtra = (int)(Math.random() * super.CELL_SIZE);
			    
			    // These 'if' statements serve the same purpose as the 'if' statements for the spikes
			    if (wallsX.get(rand) + randXExtra > wallsX.get(rand) + CELL_SIZE - PEASHOOTER_SIZE) randXExtra = CELL_SIZE - PEASHOOTER_SIZE;
			    if (wallsY.get(rand) + randYExtra > wallsY.get(rand) + CELL_SIZE - PEASHOOTER_SIZE) randYExtra = CELL_SIZE - PEASHOOTER_SIZE;
			}
			
			// Updating the peashooter coordinates
			peashooterCoordinates[i][0] = wallsX.get(rand) + randXExtra;
			peashooterCoordinates[i][1] = wallsY.get(rand) + randYExtra;
		}
		
		// This 'for' loop will generate the amount of spiders needed
		for (int i = 0; i < spiderCount; i ++) {
			// Since spiders only move up or down, we do not need to have a randYExtra variable
			int rand = 1, randXExtra = 0, topOrBottom = 0;
			
			// Making sure spiders do not spawn in the first or second column of the maze
			while (rand == 1 || rand == 0) {
				
				// Generating a random column number for the spider to crawl in
				rand = (int)(Math.random() * mazeRect.length); 
				
				// for topOrBottom, '0' means the spider will spawn on the top of the maze and slowly crawl down, '1' means the spider spawns at the bottom of maze and slowly crawls up
				topOrBottom = (int)(Math.random() * 2);
				randXExtra = (int)(Math.random() * super.CELL_SIZE);
			}
		
			// If topOrBottom is equal to 0, then the topSpiderCoordinates will be updated accordingly to the top of the maze
			if (topOrBottom == 0) {
				topSpiderCoordinates[i][0] = mazeRect[0][rand].x + randXExtra;
				topSpiderCoordinates[i][1] = 0;
			}
			// Else, topOrBottom must be equal to 1 and the bottomSpiderCoordinates will be updated accordingly to the bottom of the maze
			else {
				bottomSpiderCoordinates[i][0] = mazeRect[24][rand].x + randXExtra;
				bottomSpiderCoordinates[i][1] = 3600;
			}
		}
	}
	
	/**
	 * Helper method. This functions to draw out all the enemies and their velocity, if they have one. This method is to be used in the overriden paint method below.
	 * pre: none
	 * post: Enemies has been added to the container
	 */
	private void colourEnemies(Graphics g) {
		// Drawing the spike enemies
		for (int i = 0; i < spikeCoordinates.length; i ++) {
			// We are subtracting super.getCanvasX() and super.getCanvasY() because we don't want the enemies to be moving as our player moves around the maze
			g.drawImage(spike, spikeCoordinates[i][0] - super.getCanvasX(), spikeCoordinates[i][1] - super.getCanvasY(), SPIKE_SIZE, SPIKE_SIZE, null);
		}
		
		// Drawing the peashooter enemies
		for (int i = 0; i < peashooterCoordinates.length; i ++) {
			g.drawImage(peashooter, peashooterCoordinates[i][0] - super.getCanvasX(), peashooterCoordinates[i][1] - super.getCanvasY(), PEASHOOTER_SIZE, PEASHOOTER_SIZE, null);
			
			// Setting the color of the pea as red
			g.setColor(Color.red);
			
			// The '60' and '40' comes from the x and y difference of the pea to the barrel of the peashooter image
			g.fillOval((int) (peashooterCoordinates[i][0] - super.getCanvasX() + bulletVelocityX) + 60, peashooterCoordinates[i][1] - super.getCanvasY() + 40, PEA_SIZE, PEA_SIZE);
			
			// Adding 0.3 to the velocity of the pea
			bulletVelocityX += 0.3;
			
			// If the pea from the peashooter is greater than the peashooter's x-coordinate plus the dissapear distance, we set the bullet velocity of the pea back to 0
			if (peashooterCoordinates[i][0] + bulletVelocityX > peashooterCoordinates[i][0] + DISSAPEAR_DISTANCE) bulletVelocityX = 0;
			
			// Updating the coordinates of the pea from the peashooter
			peaCoordinates[i][0] = (int) (peashooterCoordinates[i][0] + bulletVelocityX) + 20;
			peaCoordinates[i][1] = peashooterCoordinates[i][1] + 40;
		}
		
		// Drawing the top spider enemies
		for (int i = 0; i < topSpiderCoordinates.length; i ++) {
			// Adding the velocity of the top spider to their y-coordinate
			topSpiderCoordinates[i][1] += topSpiderVelocityY;
			
			// Drawing the spider to its value minus the camera values
			g.drawImage(spider, topSpiderCoordinates[i][0] - super.getCanvasX(), (int) (topSpiderCoordinates[i][1] - super.getCanvasY()), SPIDER_SIZE, SPIDER_SIZE, null);
			
			// If the top spider's y-value is zero, then their yVelocity is set to 2 (going down)
			if (topSpiderCoordinates[i][1] == 0) topSpiderVelocityY = 2;
			// Else if the top spider's y-value is more than 3600, then their yVelocity is set to -2 (going up)
			else if (topSpiderCoordinates[i][1] > 3600) topSpiderVelocityY = -2;
		}
		
		// Drawing the bottom spider enemies
		for (int i = 0; i < bottomSpiderCoordinates.length; i ++) {
			// Same concept as the top spider's properties
			bottomSpiderCoordinates[i][1] += bottomSpiderVelocityY;
			g.drawImage(spider, bottomSpiderCoordinates[i][0] - super.getCanvasX(), bottomSpiderCoordinates[i][1] - super.getCanvasY(), SPIDER_SIZE, SPIDER_SIZE, null);
			
			// The reason it is negative -3600 here is because although the bottom spider does spawn at a yValue of 3600, their y-coordinate is still 0 for some weird reason
			if (bottomSpiderCoordinates[i][1] == 0) bottomSpiderVelocityY = -2;
			else if (bottomSpiderCoordinates[i][1] < -3600) bottomSpiderVelocityY = 2;
		}
	}
	
	/**
	 * Updating the current canvas for the maze with the enemies added in and the health bar of the player
	 * pre: none
	 * post: Enemies have been added to the maze with player's health bar
	 */
	public void paint(Graphics g) {
		// Overriding the paint method in the Maze class, which extends JPanel
		super.paint(g);
		
		// Calling the colourEnemies method to colour all the necessary enemies
		colourEnemies(g);
		
		// This website helped me set up color and font of a string
		// https://stackoverflow.com/questions/18249592/how-to-change-font-size-in-drawstring-java
		g.setFont(new Font("TimesRoman", Font.BOLD, 30)); 
		
		// These 'if' statements moderate the colour of the health string that will be drawn later on based on the player's current health
		if (healthBar >= 80) {
			
			// The reason why I created a variable healthColour is so I can use it for the colour in the "damage 'if' statements" below
			healthColour = Color.green;
		} else if (healthBar >= 50) {
			healthColour = Color.orange;
		} else if (healthBar >= 30) {
			healthColour = Color.yellow;
		} else if (healthBar >= 10){
			healthColour = Color.red;
		} else {
			// Showing a message called "GAME OVER!" if the player runs out of health and dies
			JOptionPane.showMessageDialog(null, "GAME OVER!");
			
			// Returning the player back into the main menu and setting the health bar back to 100 if they want to play again
			Game.userHasFinishedMaze();
			healthBar = 100;
		}
		
		// Setting the colour of the health string to green, orange, yellow, or red depending on how much health the player has
		g.setColor(healthColour);
		
		// Drawing out the health string of the player
		g.drawString("Health: " + healthBar, 10, 35);
		
		// These 'if' statements moderate the user's healthBar while also displaying how much health they have lost
		if (damage > 0) {
			
			// If the amount of damage the user received is greater than 0, then set the font of the string that will display how much health the player has lost
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			
			// Setting the health colour according to the amount of health they have
			g.setColor(healthColour);
			
			// If the user got hit by a spike, draw "-10!" right above the player for about 2 seconds
			if (damage == 10) {
				if (time < 100) {
					g.drawString("-10!", 220, 190);
					time ++;
				}
				else {
					// Setting the damage and time to display the message back to 0
					damage = 0; 
					time = 0;
				}
			} 
			// If the user got hit by a peashooter's pea, draw "-20!" right above the player for about 2 seconds
			else if (damage == 20) {
				if (time < 100) {
					g.drawString("-20!", 220, 190);
					time ++;
				} else {
					damage = 0;
					time = 0;
				}
			} 
			// Else, the user has got to be hit by a spider. Therefore, draw "-30!" right above the player for about 2 seconds
			else {
				if (time < 100) {
					g.drawString("-30!", 220, 190);
					time ++;
				} else {
					damage = 0;
					time = 0;
				}
			}
		}
		
		repaint();
	}
	
	/**
	 * Returns the coordinates of the spikes
	 * pre: none
	 * post: Spike coordinates returned
	 */
	public static int[][] getSpikeCoordinates() {
		return spikeCoordinates;
	}
	
	/**
	 * Subtracts the player's health bar by 10 because spikes do 10 damage when touched and set the location of the spike outside the maze
	 * pre: none
	 * post: Player's health bar has been decreased by 10 and the spikes have been relocated outside the maze
	 */
	public static void hitBySpike(int coordinates) {
		damage = 10;
		healthBar -= damage;
		
		// If the user got hit by a spike, we delete the spike and move it off of the screen
		spikeCoordinates[coordinates][0] = -1000;
		spikeCoordinates[coordinates][1] = -1000;
	}
	
	/**
	 * Returns the size of the spike
	 * pre: none
	 * post: Spike size returned
	 */
	public static int getSpikeSize() {
		return SPIKE_SIZE;
	}
	
	/**
	 * Returns the coordinates of the peashooter's bullets/peas
	 * pre: none
	 * post: Peashooter's pea coordinates returned
	 */
	public static int[][] getPeaCoordinates() {
		return peaCoordinates;
	}
	
	/**
	 * Subtracts the player's health bar by 20 because peas do 20 damage when touched and set the velocity of the peas from the peashooters back to 0
	 * pre: none
	 * post: Player's health bar has been decreased by 20 and pea(bullet)'s velocity has been set back to 0
	 */
	public static void hitByPeashooter() {
		damage = 20;
		healthBar -= damage;
		bulletVelocityX = 0;
	}
	
	/**
	 * Returns the size of a pea
	 * pre: none
	 * post: Size of a pea returned
	 */
	public static int getPeaSize() {
		return PEA_SIZE;
	}
	
	/**
	 * Returns the coordinates of the spiders that spawn at the top of the maze
	 * pre: none
	 * post: Top spider coordinates returned
	 */
	public static int[][] getTopSpiderCoordinates() {
		return topSpiderCoordinates;
	}
	
	/**
	 * Returns the coordinates of the spiders that spawn at the bottom of the maze
	 * pre: none
	 * post: Bottom spider coordinates returned
	 */
	public static int[][] getBottomSpiderCoordinates() {
		return bottomSpiderCoordinates;
	}
	
	/**
	 * Returns the size of a spider
	 * pre: none
	 * post: Spider size returned
	 */
	public static int getSpiderSize() {
		return SPIDER_SIZE;
	}
	
	/**
	 * Subtracts the player's health bar by 30 because spiders do 30 damage when touched and set the location of the spider outside the maze
	 * pre: none
	 * post: Player's health bar has been decreased by 30 and the spiders have been relocated outside the maze
	 */
	public static void hitByTopSpider(int coordinates) {
		damage = 30;
		healthBar -= damage;
		
		// If the user got hit by a spike, we delete the spike and move it off of the screen
		topSpiderCoordinates[coordinates][0] = -1000;
		topSpiderCoordinates[coordinates][1] = -1000;
	}
	
	/**
	 * Subtracts the player's health bar by 30 because spiders do 30 damage when touched and set the location of the spider outside the maze
	 * pre: none
	 * post: Player's health bar has been decreased by 30 and the spiders have been relocated outside the maze
	 */
	public static void hitByBottomSpider(int coordinates) {
		damage = 30;
		healthBar -= damage;
		
		// If the user got hit by a spike, we delete the spike and move it off of the screen
		bottomSpiderCoordinates[coordinates][0] = -1000;
		bottomSpiderCoordinates[coordinates][1] = -1000;
	}
	
	/**
	 * Refreshes the player's health bar back to 100. This is called when the user has finished the maze or when they have ran out of health while solving it
	 * pre: none
	 * post: Player's health bar has been updated
	 */
	public static void resetHealth() {
		healthBar = 100;
	}
	
	/**
	 * Returns the current amount of health the player has
	 * pre: none
	 * post: Player's current health returned
	 */
	public static int getHealth() {
		return healthBar;
	}
}
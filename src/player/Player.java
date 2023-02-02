package player;

// Importing the necessary libraries
import java.awt.*;
import javax.swing.*;
import dungeonQuest.Game;
import maze.*;

// Player Class, subclass of PlayerBehvaiours which extends PlayerFrame and implements KeyListener
public class Player extends PlayerBehaviours {
	private static final long serialVersionUID = 1L;
	
	// Declaration of objects
	private Rectangle hitBox;
	private int[][] spikeCoordinates;
	private int[][] peaCoordinates;
	private int[][] topSpiderCoordinates;
	private int[][] bottomSpiderCoordinates;
	
	/**
	 * constructor
	 * pre: none
	 * post: A Player object created.
	 */
	public Player(JPanel canvas) {
		super(canvas);
		hitBox = super.getHitbox();
		spikeCoordinates = Enemy.getSpikeCoordinates();
		peaCoordinates = Enemy.getPeaCoordinates();
		topSpiderCoordinates = Enemy.getTopSpiderCoordinates();
		bottomSpiderCoordinates = Enemy.getBottomSpiderCoordinates();	
	}
	
	/**
	 * Essentially calling the paint() method from the its superclass(PlayerBehaviours), but if the user has won, it will exit the maze and print the congratulations message
	 * pre: none
	 * post: Painting the player in the maze and checks if the user has won, and if so, exits the maze and returns back to main menu with congratulations message
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		// If the player has entered the finish square (which is on the bottom right of the maze), return to main menu
		if (xValue > maze[23][23].x && yValue > maze[23][23].y) {
			
			// Sets the maze invisible and main menu visible along with a message indicating how many health you had while you won and how much time it took for your to complete it
			Game.userHasFinishedMaze();
			JOptionPane.showMessageDialog(null, "Congratulations! You have won with " + Enemy.getHealth() + " health left and in " + Game.getTimeCompletion() + " seconds!");
			
			// Resets the player's health bar back to 100 in case the player wants to play again
			Enemy.resetHealth();
		}
		
		// Checks if the player has intersected a spike
		for (int i = 0; i < spikeCoordinates.length; i ++) {
			
			// This is the hitbox for the spike
			Rectangle spike = new Rectangle(spikeCoordinates[i][0] - Maze.getCanvasX() + 10, spikeCoordinates[i][1] - Maze.getCanvasY() + Enemy.getSpikeSize(), Enemy.getSpikeSize(), Enemy.getSpikeSize());
			
			// If the player has intersected a spike, their health bar will be decreased by 10 and the location of the spike will be set outside the maze
			if (hitBox.getBounds().intersects(spike.getBounds())) {
				Enemy.hitBySpike(i);
			}
		}
		
		// Checks if the player has intersected a pea from a peashooter
		for (int i = 0; i < peaCoordinates.length; i ++) {
			
			// Hitbox for the peashooter's pea/bullet
			Rectangle pea = new Rectangle(peaCoordinates[i][0] - Maze.getCanvasX() + 50, peaCoordinates[i][1] - Maze.getCanvasY() + Enemy.getPeaSize(), Enemy.getPeaSize(), Enemy.getPeaSize());
			
			// If the player has intersected a peashooter's pea, their health bar will be decreased by 20 and the location of the pea will reset back to the peashooter
			if (hitBox.getBounds().intersects(pea.getBounds())) {
				Enemy.hitByPeashooter();
			}
		}
		
		// Checks if the player has intersected a spider that travels from the top to the bottom
		for (int i = 0; i < topSpiderCoordinates.length; i ++) {
			
			// Hitbox for the spider
			Rectangle spider = new Rectangle(topSpiderCoordinates[i][0] - Maze.getCanvasX() + 10, topSpiderCoordinates[i][1] - Maze.getCanvasY() + 30, Enemy.getSpiderSize(), Enemy.getSpiderSize());
			
			// If the player has intersected a spider, their health bar will be decreased by 30 and the location of the spider will be set outside the maze
			if (hitBox.getBounds().intersects(spider.getBounds())) {
				Enemy.hitByTopSpider(i);
			}
		}
		
		// Checks if the player has intersected a spider that travels from the bottom to the top
		for (int i = 0; i < bottomSpiderCoordinates.length; i ++) {
			
			// Same concept as the topSpiders above
			Rectangle spider = new Rectangle(bottomSpiderCoordinates[i][0] - Maze.getCanvasX() + 10, bottomSpiderCoordinates[i][1] - Maze.getCanvasY() + 30, Enemy.getSpiderSize(), Enemy.getSpiderSize());
			if (hitBox.getBounds().intersects(spider.getBounds())) {
				Enemy.hitByBottomSpider(i);
			}
		}
		
		repaint();
	}
}
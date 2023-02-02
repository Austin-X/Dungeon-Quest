package dungeonQuest;

/* Austin Xu
 * This game is a maze solver simulation where the user navigates 
 * through a random generated maze while dodging enemies on the way.
 * When you successfully finish, you will get a completion time and 
 * the amount of health you have as an indication of how well you did 
 */

// Importing the necessary libraries
import java.io.IOException;

// Importing all classes from the maze, player, and mainMenu packages
import maze.*;
import player.*;
import mainMenu.*;

// Game Class, the client code to run the program.
public class Game {
	
	// Declaration of necessary objects needed to run game
	private static MainMenuCanvas homeScreen;
	private static MainMenu mainMenu;
	private static InstructionsPageCanvas pageCanvas;
	private static Instructions instructions;
	private static DifficultyModeCanvas difficultyModeCanvas;
	private static DifficultyMode difficultyMode;
	private static Enemy canvas;
	private static Player maze;
	
	// These variables will moderate the time it takes for the user to complete the maze, if the user even does
	private static long startTime;
	private static int timeCompleted;
	
	// Main method
    public static void main(String[] args) throws IOException {
    	// The reason the resolution is much smaller is because on my gaming laptop at home, the resolution is 1920 x 1080
    	
    	// Initializing the objects needed for the homescreen
    	homeScreen = new MainMenuCanvas();
    	mainMenu = new MainMenu(homeScreen);
    	
    	// Initializing the objects needed for the instructions
    	pageCanvas = new InstructionsPageCanvas();
    	instructions = new Instructions(pageCanvas);
    	
    	// Initializing the objects needed for the difficulty mode
    	difficultyModeCanvas = new DifficultyModeCanvas();
    	difficultyMode = new DifficultyMode(difficultyModeCanvas);
    }
    
    /** 
     * Occurs when the user clicks the play button
     * pre: none
     * post: Sets the main menu screen invisible and sets the difficulty mode screen visible
     */
    public static void userClicksPlay() {
    	mainMenu.setVisible(false);
    	difficultyMode.setVisible(true);
    }
    
    /** 
     * Occurs when the user clicks the instructions button in the main menu
     * pre: none
     * post: Sets the instructions screen visible
     */
    public static void setInstructionsVisible() {
    	instructions.setVisible(true);
    }
    
    /**
     * Occurs when the user clicks the easy, medium, or hard mode button
     * pre: none
     * post: Initializes a new maze with the number of enemies depending on what mode the user chose,
     * shows the maze as visible and starts the time clock.
     */
    public static void userSelectsMode(int mode) {
       	difficultyMode.setVisible(false);
       	
    	try {
        	// 1 represents easy mode, 2 represents medium, 3 represents hard
    		if (mode == 1) {
    			// First parameter is amount of spikes, second is amount of peashooters, third is amount of spiders / 2
    			canvas = new Enemy(25, 5, 2);
    		} else if (mode == 2) {
    			canvas = new Enemy(40, 8, 4);
    		} else if (mode == 3) {
    			canvas = new Enemy(55, 14, 8);
    		}
	    	maze = new Player(canvas);
	    	maze.setVisible(true);
	    	
	    	// Starting the current clock for the player to determine how fast the player has finished the maze
	    	startTime = System.currentTimeMillis();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Occurs when the user has died or finished the maze 
     * pre: nonw
     * post: sets the maze invisible, sets the main menu screen visible, calculates the amount of time the user took to finish, and plays the main menu music
     */
    public static void userHasFinishedMaze() {
    	maze.setVisible(false);
    	mainMenu.setVisible(true);
    	long endTime = System.currentTimeMillis();
        timeCompleted = (int)((endTime - startTime) / 1000);
    	difficultyMode.stopMazeMusic();
        mainMenu.playMainMenuMusic();
    }
    
    /**
     * Occurs when the user successfully finished the maze
     * pre: none
     * post: How much time the user took to finish the maze returned
     */
    public static int getTimeCompletion() {
    	return timeCompleted;
    }
    
    /**
     * Occurs when the user wants to go back to the main menu
     * pre: none
     * post: Sets the main menu screen visible and plays the main menu music
     */
    public static void goBackToMainMenu() {
    	mainMenu.setVisible(true);
    	mainMenu.playMainMenuMusic();
    }
}
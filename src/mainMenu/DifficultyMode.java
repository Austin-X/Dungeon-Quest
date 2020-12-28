package mainMenu;

// Importing the necessary libraries 
import java.awt.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import dungeonQuest.Game;
import java.awt.event.*;
import java.io.*;

// Difficulty Mode class
public class DifficultyMode extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Declaration of buttons
	JButton easyButton, mediumButton, hardButton, mainMenuButton;
	
	// Declaration of button icons
	private Icon easyIcon, mediumIcon, hardIcon, mainMenuIcon;
	
	// Declaration of Objects needed to play the music
	private static AudioInputStream mazeAudio;
	private static Clip mazeClip;
	
	/**
	 * Plays the music for the maze
	 * pre: none
	 * post: Maze Music played
	 */
	public void mazeSound() {
		try {
			mazeAudio = AudioSystem.getAudioInputStream(this.getClass().getResource("/mazeMusic.wav"));
			mazeClip = AudioSystem.getClip();
			mazeClip.open(mazeAudio);
			mazeClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * constructor
	 * pre: none
	 * post: A DifficultyMode object created.
	 * DifficultyMode canvas has been added, sets the properties of it, initializing the buttons and adding them onto the canvas
	 */
	public DifficultyMode(JPanel canvas) throws IOException {
		// The name of the frame
		super("Difficulty Mode");
		
		// Adding the canvas 
		add(canvas);
		
		// Setting the properties of the JFrame
		setSize(1200, 800);
		setVisible(false);
		setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Easy Button
        easyIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/easyButton.png")));
        easyButton = new JButton(easyIcon);
        easyButton.setSize(220, 100);
        easyButton.setLocation(20, 350);
        easyButton.setOpaque(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setBorderPainted(false);
        
		// Medium Button
	    mediumIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/mediumButton.png")));
        mediumButton = new JButton(mediumIcon);
        mediumButton.setSize(300, 100);
        mediumButton.setLocation(435, 350);
        mediumButton.setOpaque(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.setBorderPainted(false);
		
        // Hard Button
        hardIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/hardButton.png")));
        hardButton = new JButton(hardIcon);
        hardButton.setSize(300, 100);
        hardButton.setLocation(890, 350);
        hardButton.setOpaque(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setBorderPainted(false);
        
        // Main Menu Button
        mainMenuIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/mainMenuButton.png")));
		mainMenuButton = new JButton(mainMenuIcon);
		mainMenuButton.setSize(170, 70);
		mainMenuButton.setLocation(510, 690);
		mainMenuButton.setOpaque(false);
		mainMenuButton.setContentAreaFilled(false);
		mainMenuButton.setBorderPainted(false);
		
        // Adding the action listeners
        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);
        mainMenuButton.addActionListener(this);
		
        // Adding the buttons to my DifficultyModeCanvas container
        canvas.add(easyButton);
        canvas.add(mediumButton);
        canvas.add(hardButton);
        canvas.add(mainMenuButton);
	}
	
	/**
	 * Paints the canvas and the buttons onto the frame
	 * pre: none
	 * post: Canvas and buttons have been added to the frame
	 */
	public void paint(Graphics g) {
		super.paintComponents(g);
		repaint();
	}

	/**
	 * Decides what mode the user wants to play, or if they want to return back to main menu
	 * pre: none
	 * post: Displays the maze with the requested difficulty or displays the main menu and set the difficulty screen invisible
	 */
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		
		// Stopping the Difficulty Mode Music
		MainMenu.stopDifficultyModeMusic();
		if (button.equals(easyButton)) {
			// These methods set the difficulty frame not visible and the maze visible with the maze music to accompany it
			Game.userSelectsMode(1);
			mazeSound();
		} else if (button.equals(mediumButton)) {
			Game.userSelectsMode(2);
			mazeSound();
		} else if (button.equals(hardButton)) {
			Game.userSelectsMode(3);
			mazeSound();
		} else if (button.equals(mainMenuButton)) {
			setVisible(false);
			
			// Stopping the difficulty mode music and going back to the main menu
			MainMenu.stopDifficultyModeMusic();
			Game.goBackToMainMenu();
		}
	}
	
	/**
	 * Stops playing the maze music
	 * pre: none
	 * post: Maze music stops playing
	 */
	public void stopMazeMusic() {
		mazeClip.stop();
	}
}
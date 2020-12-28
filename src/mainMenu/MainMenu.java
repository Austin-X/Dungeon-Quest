package mainMenu;

// Importing the necessary libraries
import java.awt.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import dungeonQuest.Game;
import java.awt.event.*;
import java.io.*;

// MainMenu class
public class MainMenu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Declaration of Buttons for the main menu frame
	private JButton quitButton, playButton, instructionsButton;
	
	// Declaration of button icons
	private Icon playIcon, quitIcon, instructionsIcon;
	
	// Declaration of Objects needed to play the music
	private static AudioInputStream mainMenuAudio, instructionsAudio, difficultyModeAudio;
	private static Clip mainMenuClip, instructionsClip, difficultyModeClip;
	 
	/**
	 * Plays the music for the main menu
	 * pre: none
	 * post: Main Menu Music played
	 */
	// I had help making sound from Samuel and Kevin's instrumental instructions. Big Help.
	public void mainMenuSound() {
		try {
			// Open a new audio input stream
			mainMenuAudio = AudioSystem.getAudioInputStream(this.getClass().getResource("/mainMenuMusic.wav"));
			
			// Getting a sound clip resource
			mainMenuClip = AudioSystem.getClip();
			
			// Open the audio clip and load samples from the audio input stream
			mainMenuClip.open(mainMenuAudio);
			mainMenuClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Plays the music for the instructions
	 * pre: none
	 * post: Instructions Music played
	 */
	public void instructionsSound() {
		try {
			instructionsAudio = AudioSystem.getAudioInputStream(this.getClass().getResource("/instructionsMusic.wav"));
			instructionsClip = AudioSystem.getClip();
			instructionsClip.open(instructionsAudio);
			instructionsClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Plays the music for the difficulty mode
	 * pre: none
	 * post: Difficulty Mode Music played
	 */
	public void difficultyModeSound() {
		try {
			difficultyModeAudio = AudioSystem.getAudioInputStream(this.getClass().getResource("/difficultyModeMusic.wav"));
			difficultyModeClip = AudioSystem.getClip();
			difficultyModeClip.open(difficultyModeAudio);
			difficultyModeClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * constructor
	 * pre: none
	 * post: A MainMenu object created. 
	 */
	// I had a lot of help on this main menu from powerpoints 1-3 of Mr. Anthony
	// The reason why it throws IOException is in case the program does not find the files needed for my images
	public MainMenu(JPanel canvas) throws IOException {
		// Setting the title of the frame to "Main Menu"
		super("Main Menu");
		
		// Plays the main menu music
		mainMenuSound();
		
		// Adding the mainMenuCanvas as its container
    	add(canvas);
    	
    	// Setting the properties of the Frame
        setSize(1200, 820);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
        // Play Button
        // For the icon image in my image, I had help from: https://www.youtube.com/watch?v=rBYcwTYr4As
		playIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/playButton.png")));
		playButton = new JButton(playIcon);
		
	    playButton.setSize(260, 70);
		playButton.setLocation(460, 580);
			
		// Setting the background color of the play button transparent
		// I had help from: https://stackoverflow.com/questions/4585867/transparent-jbutton
		playButton.setOpaque(false);
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		
		// Instructions Button
		instructionsIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/instructionsButton.png")));
		instructionsButton = new JButton(instructionsIcon);
		instructionsButton.setSize(340, 70);
		instructionsButton.setLocation(840, 700);	
		instructionsButton.setOpaque(false);
		instructionsButton.setContentAreaFilled(false);
		instructionsButton.setBorderPainted(false);
		
		// Quit Button
		quitIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/quitButton.png")));
		quitButton = new JButton(quitIcon);
		quitButton.setSize(200, 70);
		quitButton.setLocation(0, 700);
		quitButton.setOpaque(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setBorderPainted(false);
		
		// Adding action listeners to my buttons so they do something when pressed
		playButton.addActionListener(this);
		quitButton.addActionListener(this);
		instructionsButton.addActionListener(this);
		
		// Adding Buttons to the MainMenuCanvas container
		canvas.add(quitButton);
		canvas.add(playButton);
		canvas.add(instructionsButton);
	}
	
	/**
	 * Paints the canvas and the buttons
	 * pre: none
	 * post: Canvas and buttons have been added to the frame
	 */
    public void paint(Graphics g) {
    	super.paintComponents(g);
    	repaint();
    }
	
	/**
	 * Decides what frame to show based on which button was pressed, or to quit the program if the user pressed the quit button
	 * pre: none
	 * post: Frame has been updated or the user has quit the program
	 */
	public void actionPerformed(ActionEvent e) {
		// I had help from: https://www.dreamincode.net/forums/topic/221316-have-2-buttons-do-2-different-things/
		JButton button = (JButton) e.getSource();
		if (button.equals(quitButton)) {
			System.exit(0);
		} else if (button.equals(playButton)) {
			mainMenuClip.stop();
			difficultyModeSound();
			
			// Calling the class method from the client code to set the main menu invisible and the difficulty mode visible
			Game.userClicksPlay();
		} else if (button.equals(instructionsButton)) {
			setVisible(false);
			
			// Stops the main menu music and plays the instructions music
			mainMenuClip.stop();
			instructionsSound();
			
			// Calling the class method from the client code to set the main menu invisible and the instructions frame visible
			Game.setInstructionsVisible();
		}
	}
	
	/**
	 * Starts playing the main menu music
	 * pre: none
	 * post: Main menu music starts playing
	 */
	public void playMainMenuMusic() {
		try {
			mainMenuAudio = AudioSystem.getAudioInputStream(this.getClass().getResource("/mainMenuMusic.wav"));
			mainMenuClip = AudioSystem.getClip();
			mainMenuClip.open(mainMenuAudio);
			mainMenuClip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Stops playing the instructions music
	 * pre: none
	 * post: Instructions music stops playing
	 */
	public static void stopInstructionsMusic() {
		instructionsClip.stop();
	}
	
	/**
	 * Stops playing the difficulty mode music
	 * pre: none
	 * post: Difficulty mode music stops playing
	 */
	public static void stopDifficultyModeMusic() {
		difficultyModeClip.stop();
	}
}
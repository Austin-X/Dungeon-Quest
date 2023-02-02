package mainMenu;

// Importing the necessary libraries
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// DifficultyModeCanvas class
public class DifficultyModeCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	 // This image will be used as the background for the difficulty mode screen
	 BufferedImage background;
	 
	 /**
	  * constructor
	  * pre: none
	  * post: A DifficultyModeCanvas object created. Background initialized to an image and layout intialized to null
	  */
	 public DifficultyModeCanvas() throws IOException {
		background = ImageIO.read(this.getClass().getResource("/difficultyBackground.jpg"));
		setLayout(null);
	 }
	 
	 /**
	  * Overrides the current JPanel and updating the canvas
	  * pre: none
	  * post: Sets the bakground of the screen, and the labels that go along with each mode
	  */
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 
		 // Setting the background
		 g.drawImage(background, 0, 0, 1200, 800, null);
		 
		 // Setting the title of the screen, which asks the user for what difficulty mode they would like to play
		 g.setFont(new Font("TimesRoman", Font.BOLD, 55)); 
		 g.setColor(Color.orange);
		 g.drawString("What Difficulty Mode would you like to play in?", 30, 100);
		 
		 // Drawing the label for the easy button mode
		 g.setFont(new Font("TimesRoman", Font.PLAIN, 16)); 
		 g.setColor(Color.green);
		 g.drawString("Easy Mode:", 90, 250);
		 g.drawString("x25 spikes, x5 peashooters, x4 spiders", 10, 280);
		 
		// Drawing the label for the medium button mode
		 g.setColor(Color.cyan);
		 g.drawString("Medium Mode:", 535, 250);
		 g.drawString("x40 spikes, x8 peashooters, x8 spiders", 455, 280);
		 
		// Drawing the label for the hard button mode
		 g.setColor(Color.red);
		 g.drawString("Hard Mode:", 1000, 250);
		 g.drawString("x55 spikes, x14 peashooters, x16 spiders", 900, 280);
	 }
}
package mainMenu;

// Importing the necessary libraries
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// MainMenuCanvas class
public class MainMenuCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	 // Background image for my main menu container
	 BufferedImage background;
	 
	/**
	 * constructor
	 * pre: none
	 * post: A MainMenuCanvas object created. 
	 */
	 public MainMenuCanvas() throws IOException {
		background = ImageIO.read(this.getClass().getResource("/mainMenuBackground.jpg"));
		setLayout(null);
	 }
 
	/**
	 * Overrides the current canvas to the background image
	 * pre: none
	 * post: Background image for the main menu has been shown
	 */
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 g.drawImage(background, 0, 0, 1200, 800, null);
	 }
}
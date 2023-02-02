package mainMenu;

// Importing the necessary libraries
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

// InstructionsPageCanvas class
public class InstructionsPageCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// Declaration of variables
	private static int pageNumber = 1;
	BufferedImage instructionsPage1, instructionsPage2, instructionsPage3;

	/**
	 * constructor
	 * pre: none
	 * post: An InstructionsPageCanvas object created. 
	 */
	public InstructionsPageCanvas() throws IOException {
		instructionsPage1 = ImageIO.read(this.getClass().getResource("/instructionsPage1.jpg"));
		instructionsPage2 = ImageIO.read(this.getClass().getResource("/instructionsPage2.jpg"));
		instructionsPage3 = ImageIO.read(this.getClass().getResource("/instructionsPage3.jpg"));
		setLayout(null);
		setBackground(Color.gray);
	}

	/**
	 * Overrides the current canvas based on what page number the user is on
	 * pre: none
	 * post: Page 1, 2, or 3 of the instructions shown
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (pageNumber == 1) {
			g.drawImage(instructionsPage1, 0, 0, 820, 710, null);
		} else if (pageNumber == 2) {
			g.drawImage(instructionsPage2, 0, 0, 820, 710, null);
		} else if (pageNumber == 3) {
			g.drawImage(instructionsPage3, 0, 0, 820, 710, null);
		}
		repaint();
	}

	/**
	 * Adds a page number to the class variable 'pageNumber'
	 * pre: none
	 * post: pageNumber has been increased by 1
	 */
	public static void addPageNumber() {
		pageNumber ++;
	}
	
	/**
	 * Subtracts a page number to the class variable 'pageNumber'
	 * pre: none
	 * post: pageNumber has been decreased by 1
	 */
	public static void subtractPageNumber() {
		pageNumber --;
	}
	
	/**
	 * Determines what page number the user is currently on
	 * pre: none
	 * post: Page Number that the user is on returned
	 */
	public static int getPageNumber() {
		return pageNumber;
	}

	/**
	 * Sets the page number of the instructions
	 * pre: none
	 * post: pageNumber has been changed to the parameter 'num'
	 */
	public static void setPageNumber(int num) {
		pageNumber = num;
	}
}
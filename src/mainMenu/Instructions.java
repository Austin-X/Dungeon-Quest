package mainMenu;

// Importing the necessary libraries
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import dungeonQuest.Game;
import java.awt.event.*;
import java.io.IOException;

// Instructions Class
public class Instructions extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Declaration of buttons
	private JButton nextButton, backButton, mainMenuButton;
	
	// Declaration of button icons
	private Icon mainMenuIcon, nextIcon, backIcon;
	
	/**
	 * constructor
	 * pre: none
	 * post: An Instructions object created. 
	 */
	public Instructions(JPanel page) throws IOException {
		// The title of the frame
		super("Instructions");

		// Adding the canvas to the frame
		add(page);

		// Setting the properties of the frame
		setSize(820, 820);
		setVisible(false);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// Main Menu Button for when user wants to return back to main menu from Instructions
		mainMenuIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/mainMenuButton.png")));
		mainMenuButton = new JButton(mainMenuIcon);
		mainMenuButton.setSize(170, 70);
		mainMenuButton.setLocation(310, 710);
		mainMenuButton.setOpaque(false);
		mainMenuButton.setContentAreaFilled(false);
		mainMenuButton.setBorderPainted(false);

		// Next Button to go to the next page of the instructions
		nextIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/nextButton.png")));
		nextButton = new JButton(nextIcon);
		nextButton.setSize(150, 70);
		nextButton.setLocation(660, 715);
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);

		// Back button to go the the previous page of the instructions
		backIcon = new ImageIcon(ImageIO.read(this.getClass().getResource("/backButton.png")));
		backButton = new JButton(backIcon);
		backButton.setSize(190, 90);
		backButton.setLocation(0, 702);
		backButton.setVisible(false);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);

		// Adding action listeners to each button so they do something when clicked
		backButton.addActionListener(this);
		mainMenuButton.addActionListener(this);
		nextButton.addActionListener(this);
		
		// Adding buttons to the InstructionsPageCanvas container
		page.add(backButton);
		page.add(nextButton);
		page.add(mainMenuButton);
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
	 * Decides what page the user is on depending on how much time the next button or back button is clicked
	 * pre: none
	 * post: Displays the instructions page with its canvas updated each time the next or back button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		if (button.equals(mainMenuButton)) {
			setVisible(false);
			
			// Stopping the instructions music
			MainMenu.stopInstructionsMusic();
			
			// Class Methods to set the main menu frame visible and to reset the page number of the InstructionsPageCanvas container to 1
			Game.goBackToMainMenu();		
			InstructionsPageCanvas.setPageNumber(1);
			
			backButton.setVisible(false);
			nextButton.setVisible(true);
		} else if (button.equals(nextButton)) {
			InstructionsPageCanvas.addPageNumber();
			
			// If the page number is 3, we should set the nextButton invisible because there are no more pages in the instructions beyond page 3
			if (InstructionsPageCanvas.getPageNumber() == 3)
				nextButton.setVisible(false);
			
			backButton.setVisible(true);
		} else if (button.equals(backButton)) {
			InstructionsPageCanvas.subtractPageNumber();
			
			// If the page number is 1, we should set the backButton invisible because you can't have a page 0 
			if (InstructionsPageCanvas.getPageNumber() == 1)
				backButton.setVisible(false);
			
			nextButton.setVisible(true);
		}
	}
}
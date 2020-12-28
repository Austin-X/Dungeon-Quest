package player;

// Importing everything from the javax.swing library
import javax.swing.*;

// PlayerFrame class which extends JFrame
public class PlayerFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 * pre: none
	 * post: A PlayerFrame object created.
	 */
    public PlayerFrame(JPanel canvas) {
    	// Sets the title of the frame to "Maze"
    	super("Maze");
    	
    	// Setting the size of the frame to the canvas's dimensions and then adding the canvas into the frame
    	setSize(canvas.getWidth(), canvas.getHeight());
    	add(canvas);
    	
    	// Setting the basic properties of the frame
        setSize(900, 800);
        setVisible(false);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
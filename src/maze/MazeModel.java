package maze;

// MazeModel Class
public class MazeModel {
	
    // Declaration of variables
    private static char PASSAGE = ' ';
    private static char WALL = '#';
    private static char[][] maze;
    
    /**
      * constructor
      * pre: none
      * post: A MazeModel object created.
      */
    public MazeModel(int rowCells, int colCells) {
        
        // Total maze size will be 2 times the amount of units needed to reach up to the middle line, plus 1 because it is including the middle line
        maze = new char[2 * rowCells + 1][2 * colCells + 1];
        
        // Initializes a new maze
        newMaze();
        
        // Uses recursive division algorithm to create all the necessary passage spaces for the user
        divide(0, rowCells, 0, colCells);
        
        // This string and 'for' loop will be used in my explanation of how recursive division really works
        System.out.println("\nEnd maze design");
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                System.out.print(maze[r][c] + " ");
            }
            System.out.println();
        }
    }
    
    /** 
     * Helper method. Used to initialize the walls of the maze.
     * pre: none
     * post: A maze has been initialized and ready for the recursive division process.
     */ 
    private void newMaze() {
    	
    	// 'For' loop running from 0 to how many rows and columns in the maze
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
            	
            	// If row number is odd and column number is odd, then that is a passage square for the user (' ')
                if (r%2 == 1 && c%2 == 1) {
                    maze[r][c] = PASSAGE;
                } 
            	// Else, it is a wall (#)
                else {
                    maze[r][c] = WALL;
                }
            }
        }
        
        // This string and 'for' loop will be used in my explanation of how recursive division really works
        System.out.println("Initial maze design");
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                System.out.print(maze[r][c] + " ");
            }
            System.out.println();
        }
    }
    
    /** 
     * Helper method. This is where the recursive division process begins and how the maze gets updated every step
     * pre: none
     * post: A newly random generated maze model has been set
     */
    private void divide(int rowA, int rowB, int colA, int colB) {
    	
    	// If the space between the row bounds and the column bounds are 0, then do not create another passage and return nothing.
        if (rowB-rowA <= 1 && colB-colA <= 1) return;
        
        // If the height is greater than or equal to the width, then there will be a horizontal cut 
        if (rowB-rowA >= colB-colA) { 
        	// Finding the average between the two rows (to be used in the recursive functions at the bottom)
            int midR = (rowA+rowB)/2; 
            
            // Generate y-coordinate for a random column space in the horizontal line to be created as a passage space(' ')
            int colPassage = (int)(Math.random()*(colB-colA))+colA;

            // Generating a random passage square in the horizontal line of walls with x-coordinate midR * 2 and y-coordinate colPassage * 2 + 1
            maze[midR*2][colPassage*2+1] = PASSAGE;
            
            // This first recursive functions checks the vertical line above the current row number and below the upper bound row number
            // This vertical line will have x-coordinates of the middle x-coordinate of the horizontal line
            divide(rowA, midR, colA, colB);
            
            // This recursive function checks the vertical line below the current row number and above the lower bound row number
            // This vertical line will have x-coordinates of the middle x-coordinate of the horizontal line
            divide(midR, rowB, colA, colB);
        } 
        // Otherwise, the height is smaller than the width and there will be a vertical cut
        else {   
        	// Same steps as the horizontal cut, except this time, there will be a vertical cut and a random passage through a vertical line of walls
            int midC = (colA+colB)/2;
            int offR = (int)(Math.random()*(rowB-rowA))+rowA;
            maze[offR*2+1][midC*2] = PASSAGE;
 
            divide(rowA, rowB, colA, midC);
            divide(rowA, rowB, midC, colB);
        }
    } 
 
    /**
     * Returns the randomly genereated maze model with '#' representing a wall and an empty ' ' representing a passage
     * pre: none
     * post: Randomly genereated maze model returned
     */
    public char[][] getMazeModel() {
        return maze;
    }
}

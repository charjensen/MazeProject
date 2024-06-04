
public class Driver {

	public static void main(String[] args) {
		
		//make new maze, make sure that dimensions are even
		Maze m = new Maze(94, 30);
		
		//make the maze
		m.generateMaze();
		
		// solve the maze
		m.solveMaze();
		
	}
	
}

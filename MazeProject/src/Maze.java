import java.awt.Color;
import java.util.ArrayList;
import edu.du.dudraw.DUDraw;
import java.util.Collections;

public class Maze {

	//declare 2d arrays for cells and maze
	private CellValue[][] maze;
	private Cell[][] cells;

	//enum value to tell whether each cell is explored or not
	enum CellValue {
		WALL, OPEN, EXPLORED
	}

	//constructor
	public Maze(int width, int height) {

		// coefficient to get right aspect ratio for the draw window
		int coef;
		if (height > width) {
			coef = height / width;
		} else {
			coef = width / height;
		}

		//dudraw and ssetup canvas
		DUDraw.setCanvasSize(width * coef * 5, height * coef * 5);
		DUDraw.setXscale(0, width);
		DUDraw.setYscale(0, height);
		DUDraw.clear(DUDraw.BLACK);
		DUDraw.enableDoubleBuffering();

		//instantiate 2d arrays
		maze = new CellValue[width][height];
		cells = new Cell[width][height];

		//give each indicie of arrays default values
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				maze[i][j] = CellValue.WALL;
				cells[i][j] = new Cell(i, j);

			}
		}

	}

	//make the maze
	public void generateMaze() {
		
		//make a new stack and starting cell
		DLLStack<Cell> MazeStack = new DLLStack<Cell>();
		Cell currentCell = new Cell(5, 7);

		//start the maze by opening the first cell
		maze[currentCell.xPosition][currentCell.yPosition] = CellValue.OPEN;

		// push the current cell onto the stack
		MazeStack.push(currentCell);

		//loop until no more items are in the stack
		while (!MazeStack.isEmpty()) {

			//pop the top cell off the stack
			currentCell = MazeStack.pop();
			//instantiate arraylist of neighboring cells relative to current cell
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			//draw the board
			draw();

			//for loop to start at the left and bottom cells relative to
			//the current cell, and then iterate to move to the upper and rightmost cell
			for (int i = -2; i <= 2; i += 4) {
				
				//first work on the leftmost cell
				//make sure that the cell we are checking is in bounds
				if ((currentCell.xPosition + i < maze.length && currentCell.xPosition + i > 0)) {
					//if the cell is not open, open it  
					if (maze[currentCell.xPosition + i][currentCell.yPosition] == CellValue.WALL) {
						maze[currentCell.xPosition + i][currentCell.yPosition] = CellValue.OPEN;
						neighbors.add(cells[currentCell.xPosition + i][currentCell.yPosition]);

						//open inbetween cells between current and neighbor
						if (i < 0) {
							maze[currentCell.xPosition + (i + 1)][currentCell.yPosition] = CellValue.OPEN;
						} else {
							maze[currentCell.xPosition + (i - 1)][currentCell.yPosition] = CellValue.OPEN;
						}
					}

				}

				//for loop to start at the left and bottom cells relative to
				//the current cell, and then iterate to move to the upper and rightmost cell
				if ((currentCell.yPosition + i < maze[0].length && currentCell.yPosition + i > 0)) {
					//check the upper cell
					if (maze[currentCell.xPosition][currentCell.yPosition + i] == CellValue.WALL) {
						//open it
						maze[currentCell.xPosition][currentCell.yPosition + i] = CellValue.OPEN;
						//add neighbors to be processed
						neighbors.add(cells[currentCell.xPosition][currentCell.yPosition + i]);
						
						//open cells between wall and neighbors
						if (i < 0) {
							maze[currentCell.xPosition][currentCell.yPosition + (i + 1)] = CellValue.OPEN;
						} else {
							maze[currentCell.xPosition][currentCell.yPosition + (i - 1)] = CellValue.OPEN;
						}
					}

				}

			}

			//randomize what order the neighbors are processed in
			Collections.shuffle(neighbors);

			//push all cell neighbors onto the stack
			for (Cell c : neighbors) {
				MazeStack.push(c);
			}

		}
	}

	// use breadth depth search to solv emaze
	public boolean solveMaze() {

		//set stat, goal, and current cells
		Cell start = cells[1][1];
		Cell goal = cells[cells.length - 1][cells[0].length - 1];
		Cell currentCell = start;
		//make new queue
		DLLQueue<Cell> queue = new DLLQueue<Cell>();

		//set the current cell to explored to start the queue
		maze[currentCell.xPosition][currentCell.yPosition] = CellValue.EXPLORED;

		//queue the first cell
		queue.enqueue(currentCell);

		//keep dequeuing while there is stuff in the queue
		while (!queue.isEmpty()) {

			//dequeue the top cell and assign it to currentcell
			currentCell = queue.dequeue();

			//draw to canvas
			draw();

			//exit loop if the goal has been found
			if (currentCell.equals(goal)) {
				return true;
			}

			//check the left and right cells first
			for (int i = -1; i <= 1; i += 2) {
				//make sure it is in bounds and not a wall or already explored cell
				if ((currentCell.xPosition + i < maze.length && currentCell.xPosition + i > 0) 
						&& maze[currentCell.xPosition + i][currentCell.yPosition] == CellValue.OPEN) {
					//set cell to explored and queue the cell to be processed
					maze[currentCell.xPosition + i][currentCell.yPosition] = CellValue.EXPLORED;
					queue.enqueue(cells[currentCell.xPosition + i][currentCell.yPosition]);
				}
			}

			//check the upper and lower cells
			for (int i = -1; i <= 1; i += 2) {
				// make sure they are in bounds and unxplored + not walls
				if ((currentCell.yPosition + i < maze[0].length && currentCell.yPosition + i > 0) 
						&& maze[currentCell.xPosition][currentCell.yPosition + i] == CellValue.OPEN) {
					//sest them to explored and queue them to be processed
					maze[currentCell.xPosition][currentCell.yPosition + i] = CellValue.EXPLORED;
					queue.enqueue(cells[currentCell.xPosition][currentCell.yPosition + i]);
				}
			}
			
		}

		//if some how goal is not found just return false
		return false;

	}

	//draw the maze
	public void draw() {

		// redraw each tile of the board
		for (int i = 0; i < maze[0].length; i++) {
			for (int j = 0; j < maze.length; j++) {

				//depending on cell draw it to be different color
				if (maze[j][i] == CellValue.OPEN) {
					DUDraw.setPenColor(DUDraw.WHITE);
					DUDraw.filledSquare(j, i, 0.5);
				} else if (maze[j][i] == CellValue.EXPLORED) {
					DUDraw.setPenColor(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)));
					DUDraw.filledSquare(j, i, 0.5);
				}

			}
		}

		//framerate and show
		DUDraw.pause(10);
		DUDraw.show();

	}

	//cell inner class
	public class Cell {

		//has x and y pos
		private int xPosition;
		private int yPosition;

		//constructor
		public Cell(int x, int y) {

			xPosition = x;
			yPosition = y;

		}

		//to string
		public String toString() {
			return "x: " + xPosition + "y: " + yPosition;
		}

	}

}

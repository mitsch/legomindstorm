import common.Robot;

import labyrinth.LabyrinthSolver;


public class LabyrinthMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot sadRobot = new Robot();
		solveLabyrinth(sadRobot);
	}
	
	public static void solveLabyrinth(Robot robot) {
		LabyrinthSolver labyrinthSolver = new LabyrinthSolver(robot);
		labyrinthSolver.go();
	}
}

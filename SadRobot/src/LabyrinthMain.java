import common.Robot;

import labyrinth.LabyrinthSolver;
import lejos.nxt.Button;

public class LabyrinthMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		Robot sadRobot = new Robot();
		solveLabyrinth(sadRobot);
	}
	
	public static void solveLabyrinth(Robot robot) {
		LabyrinthSolver labyrinthSolver = new LabyrinthSolver(robot, true);
		labyrinthSolver.go();
	}
}

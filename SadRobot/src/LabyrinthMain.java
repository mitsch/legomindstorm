import common.Robot;

import labyrinth.LabyrinthStrategy;
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
		LabyrinthStrategy labyrinthSolver = new LabyrinthStrategy(robot, true);
		labyrinthSolver.start();
	}
}

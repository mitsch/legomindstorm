import common.Robot;

import naiveLabyrinth.NaiveLabyrinthSolver;
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
		robot.joker.rotateTo(-45);
		NaiveLabyrinthSolver labyrinthSolver = new NaiveLabyrinthSolver(robot, true);
		labyrinthSolver.go();
	}
}

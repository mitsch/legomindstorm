package naiveLabyrinth;

import common.Robot;
import lejos.robotics.subsumption.Behavior;

/**
 * This Behavior enables the robot to save obstacles in the labyrinth map when
 * he bumps into them.
 * @author Thomas
 *
 */
public class LeftBumper implements Behavior {
	private Robot robot;
	
	public LeftBumper(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return !NaiveLabyrinthSolver.solved && robot.leftTouch.isPressed();
	}

	@Override
	public void action() {
		robot.pilot.travel(-10);
		robot.pilot.rotate(-30);
	}

	@Override
	public void suppress() {
	}
}

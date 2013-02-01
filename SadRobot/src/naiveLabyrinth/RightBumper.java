package naiveLabyrinth;

import common.Robot;
import lejos.robotics.subsumption.Behavior;

/**
 * This Behavior enables the robot to save obstacles in the labyrinth map when
 * he bumps into them.
 * @author Thomas
 *
 */
public class RightBumper implements Behavior {
	private Robot robot;
	
	public RightBumper(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return !NaiveLabyrinthSolver.solved && robot.rightTouch.isPressed();
	}

	@Override
	public void action() {
		robot.pilot.travel(-10);
		robot.pilot.rotate(30);
	}

	@Override
	public void suppress() {
	}
}

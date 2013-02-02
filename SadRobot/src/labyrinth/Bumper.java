package labyrinth;

import common.Robot;
import lejos.robotics.subsumption.Behavior;

/**
 * This Behavior enables the robot to save obstacles in the labyrinth map when
 * he bumps into them.
 * @author Thomas
 *
 */
public class Bumper implements Behavior {
	private Robot robot;
	
	public Bumper(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return !LabyrinthSolver.solved &&
				(robot.leftTouch.isPressed() || robot.rightTouch.isPressed());
	}

	@Override
	public void action() {
		robot.pilot.travel(-10);
		robot.pilot.rotate(-90);
	}

	@Override
	public void suppress() {
	}
}

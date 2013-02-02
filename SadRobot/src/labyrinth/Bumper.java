package labyrinth;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

/**
 * This Behavior enables the robot to save obstacles in the labyrinth map when
 * he bumps into them.
 * @author Thomas
 *
 */
public class Bumper extends StrategyBehavior {
	private Robot robot;
	
	public Bumper(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return robot.leftTouch.isPressed() || robot.rightTouch.isPressed();
	}

	@Override
	public void work() {
		robot.pilot.travel(-10);
		robot.pilot.rotate(-90);
	}
}

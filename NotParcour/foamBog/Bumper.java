package foamBog;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

/**
 * If we bump into something, we have passed the foam bog.
 * 
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
		return robot.leftTouch.isPressed() && robot.rightTouch.isPressed();
	}

	@Override
	public void work() {
		parent.stop();
	}
}

package foamBog;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class Drive extends StrategyBehavior {
	private Robot robot;
	
	public Drive(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return true;
	}

	@Override
	public void work() {
		robot.pilot.forward();
		while (!suppressed);
	}
}

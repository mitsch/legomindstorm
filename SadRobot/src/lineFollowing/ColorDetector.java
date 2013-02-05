package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class ColorDetector extends StrategyBehavior {
	private Robot robot;
	private boolean justExecuted;
	
	public ColorDetector(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	protected boolean wantsToWork() {
		return !justExecuted && robot.light.readNormalizedValue() < 507
				&& robot.light.readNormalizedValue() > 350;
	}

	@Override
	protected void work() {
		//check if we really are at the colors by swinging our sensor arm
		robot.arm.alignLeft();
		if (robot.sonar.getDistance() < 20)
			parent.stop();
		robot.arm.alignCenter();
	}

}

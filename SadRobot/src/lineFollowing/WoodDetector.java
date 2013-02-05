package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class WoodDetector extends StrategyBehavior {
	private Robot robot;
	
	public WoodDetector(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	protected boolean wantsToWork() {
		return robot.isWoodBeneath();
	}

	@Override
	protected void work() {
		parent.stop();
	}

}

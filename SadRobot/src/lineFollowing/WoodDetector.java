package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class WoodDetector extends StrategyBehavior {
	private Robot robot;
	
	public WoodDetector(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	protected boolean wantsToWork() {
		return (Color.BROWN == this.robot.color.getColor(Color.BLACK, Color.SILVER, Color.BROWN));
	}

	@Override
	protected void work() {
		parent.stop();
	}

}

package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class WoodDetector extends StrategyBehavior {
	private Robot robot;
	
	private boolean isWoodBeneath(){
		return (Color.BROWN_DARK == this.robot.color.getColor(Color.BLACK, Color.SILVER, Color.BROWN_DARK));
	}
	
	public WoodDetector(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	protected boolean wantsToWork() {
		return this.isWoodBeneath();
	}

	@Override
	protected void work() {
		parent.stop();
	}

}

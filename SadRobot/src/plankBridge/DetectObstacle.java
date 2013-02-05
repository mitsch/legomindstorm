package plankBridge;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class DetectObstacle extends StrategyBehavior {
	private Robot robot;
	
	public DetectObstacle(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	protected boolean wantsToWork() {
		return robot.sonar.getDistance() < 80;
	}

	@Override
	protected void work() {
		parent.stop();
	}	
}

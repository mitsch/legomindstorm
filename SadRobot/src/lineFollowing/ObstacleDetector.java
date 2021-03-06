package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

/**
 * Hindernis
 * @author HM42
 *
 */
public class ObstacleDetector extends StrategyBehavior {
	private Robot robot;
	
	public ObstacleDetector(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}
	
	@Override
	protected boolean wantsToWork() {
		return robot.sonar.getDistance() < 40;
	}

	@Override
	protected void work() {
		parent.stop();
	}
}

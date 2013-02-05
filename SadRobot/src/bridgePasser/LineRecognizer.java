package bridgePasser;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

/**
 * The bridge is crossed, if we see a wall to the left
 * 
 * @author Thomas
 *
 */
public class LineRecognizer extends StrategyBehavior {
	private Robot robot;

	public LineRecognizer(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return robot.sonar.getDistance() < 30;
	}

	@Override
	public void work() {
		parent.stop();
	}
}

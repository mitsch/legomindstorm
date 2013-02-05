package bridgePasser;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

/**
 * If we see a bridge below us, we drive in a slight curve.
 * 
 * @author Thomas
 *
 */
public class DriveBridge extends StrategyBehavior {
	private Robot robot;
	private boolean leftBridge;
	
	public DriveBridge(Robot robot, boolean leftBridge, Strategy parent) {
		super(parent);
		this.robot = robot;
		this.leftBridge = leftBridge;
	}

	@Override
	public boolean wantsToWork() {
		return robot.isWoodBeneath();
	}

	@Override
	public void work() {
		if (leftBridge)
			robot.pilot.steer(-20);
		else
			robot.pilot.steer(20);
		while (!suppressed);
		robot.pilot.stop();
	}
}

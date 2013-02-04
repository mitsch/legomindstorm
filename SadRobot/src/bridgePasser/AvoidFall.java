package bridgePasser;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class AvoidFall extends StrategyBehavior {
	private Robot robot;
	private boolean leftBridge;
	
	public AvoidFall(Robot robot, boolean leftBridge, Strategy parent) {
		super(parent);
		this.robot = robot;
		this.leftBridge = leftBridge;	
	}

	@Override
	public boolean wantsToWork() {
		return robot.color.getColor(Color.ABYSS, Color.BROWN) == Color.ABYSS;
	}

	@Override
	public void work() {
		if (leftBridge)
			robot.pilot.steer(-100, 20);
		else
			robot.pilot.steer(100, -20);
		while (robot.pilot.isMoving());
		robot.pilot.stop();
	}
}

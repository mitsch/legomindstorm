package plankBridge;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class White extends StrategyBehavior {
	private Robot robot;
	
	public White(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot; 
	}

	@Override
	public boolean wantsToWork() {
		return robot.isLineBeneath();
	}

	@Override
	public void work() {
		robot.pilot.rotateLeft();
		while (!suppressed && robot.isLineBeneath());
		robot.pilot.stop();
	}
}

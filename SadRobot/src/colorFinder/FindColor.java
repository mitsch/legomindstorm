package colorFinder;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class FindColor extends StrategyBehavior {
	private Robot robot;
	
	public FindColor(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}
	
	@Override
	public boolean wantsToWork() {
		return robot.isLineBeneath();
	}
	
	@Override
	public void work() {
		parent.stop();
	}
}
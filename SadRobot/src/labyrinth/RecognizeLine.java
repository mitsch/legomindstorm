package labyrinth;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class RecognizeLine extends StrategyBehavior {
	private Robot robot;
	
	public RecognizeLine(Robot robot, Strategy parent) {
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
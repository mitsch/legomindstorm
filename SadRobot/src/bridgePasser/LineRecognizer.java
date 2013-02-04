package bridgePasser;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

import lejos.nxt.Sound;

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

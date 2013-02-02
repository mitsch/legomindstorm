package bridgePasser;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

import lejos.nxt.Sound;

public class LineRecognizer extends StrategyBehavior {
	private Robot robot;

	public LineRecognizer(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return robot.light.readValue() > (robot.getLineValue()
				+ robot.getWoodValue()) / 2;
	}

	@Override
	public void work() {
		parent.stop();
		Sound.beepSequenceUp();
	}
}

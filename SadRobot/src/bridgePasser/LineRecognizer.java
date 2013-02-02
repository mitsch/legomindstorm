package bridgePasser;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class LineRecognizer implements Behavior {
	private Robot robot;
	
	public LineRecognizer(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return robot.isLineBeneath() && !robot.isFallBeneath();
	}

	@Override
	public void action() {
		BridgeStrategy.bridgePassed = true;
	}

	@Override
	public void suppress() {
	}
}

package foamBog;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class Bumper implements Behavior {
	private Robot robot;
	
	public Bumper(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return robot.leftTouch.isPressed() && robot.rightTouch.isPressed();
	}

	@Override
	public void action() {
		FoamBogStrategy.passed = true;
	}

	@Override
	public void suppress() {
	}

}

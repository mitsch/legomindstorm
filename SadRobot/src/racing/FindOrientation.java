package racing;

import common.Robot;
import racing.Racer;

import lejos.robotics.subsumption.Behavior;

public class FindOrientation implements Behavior {

	private boolean suppressed;
	private Robot robot;

	public FindOrientiation(Robot robot) {
		this.suppressed = false;
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return !suppressed;
	}

	@Override
	public void action() {
		suppressed = false;

		robot.pilot.stop();
		robot.joker.rotateTo(robot.joker.getLeftAngle());
		int leftDistance = 
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}


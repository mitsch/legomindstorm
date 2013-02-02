package foamBog;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class Drive implements Behavior {
	private Robot robot;
	private boolean suppressed;
	
	public Drive(Robot robot) {
		this.robot = robot;
		suppressed = false;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		
		robot.pilot.forward();
		while (!suppressed);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}

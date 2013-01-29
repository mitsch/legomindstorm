package lineFollowing;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class FollowLine implements Behavior {
	private boolean suppressed;
	private Robot robot;
	
	public FollowLine(Robot robot) {
		this.robot = robot; 
		this.suppressed = false;
	}

	@Override
	public boolean takeControl() {
		return robot.isGroundSilver();
	}

	@Override
	public void action() {
		suppressed = false;
		
		robot.pilot.steer(LineFollower.lineCurvature);
		while (!suppressed && robot.isGroundSilver());
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

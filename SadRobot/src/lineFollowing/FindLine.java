package lineFollowing;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class FindLine implements Behavior {
	private Robot robot;
	private boolean suppressed;
	
	public FindLine(Robot robot) {
		this.robot = robot;
		this.suppressed = false;
	}

	@Override
	public boolean takeControl() {
		return !robot.isLineBeneath();
	}

	@Override
	public void action() {
		suppressed = false;
		
		//find line by rotating a bit to the right and left
		int turn = 10;
		short sign = -1;
		while (!suppressed && !robot.isLineBeneath()) {
			robot.pilot.rotate(sign * turn, true);
			while (!suppressed && !robot.isLineBeneath() && robot.pilot.isMoving());	
			robot.pilot.stop();
			
			turn *= 2;
			sign *= -1;
		}
		
		//update the estimated lineCurvature
		LineFollower.lineCurvature += (20 * sign);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
package lineFollowing;

import common.Robot;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class FindLine implements Behavior {
	private Robot robot;
	private boolean suppressed;
	private boolean lastHeadingLeft;
	
	public FindLine(Robot robot) {
		this.robot = robot;
		this.suppressed = false;
		this.lastHeadingLeft = true;
	}

	@Override
	public boolean takeControl() {
		return !robot.isLineBeneath();
	}

	@Override
	public void action() {
		suppressed = false;
		
		//find line by rotating a bit to the right and left	
		robot.pilot.setRotateSpeed(60);
		int currentHeading = 0;
		int turn = 20;
		short sign;
		if(lastHeadingLeft)
			sign = 1;
		else
			sign = -1;
		
		while (!suppressed && !robot.isLineBeneath()) {
			//if we would rotate too far
			if (turn >= 180) {
				Sound.beep();
				robot.pilot.rotate(-currentHeading + sign*20);
				Sound.beep();
				if (!suppressed) {
					robot.pilot.travel(30);
					while (!suppressed && robot.navigator.isMoving());
					robot.pilot.stop();
				}
				break;
			} else {
				for (int i=0; i<2 && !suppressed && !robot.isLineBeneath(); i++) {	
					robot.pilot.rotate(-currentHeading + sign*turn, true);
					while (!suppressed && !robot.isLineBeneath() && robot.pilot.isMoving());	
					robot.pilot.stop();
					currentHeading = sign*turn;
				
					if (!robot.isLineBeneath())
						sign *= -1;
						turn += 20;
				}
			}	
		}
		
		lastHeadingLeft = sign == 1;
		LineFollower.lineCurvature += sign*20;
		robot.pilot.setRotateSpeed(90);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
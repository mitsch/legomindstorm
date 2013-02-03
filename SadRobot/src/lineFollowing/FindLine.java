package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class FindLine extends StrategyBehavior {
	private Robot robot;
	private boolean lastHeadingLeft;
	
	public FindLine(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
		this.lastHeadingLeft = true;
	}

	@Override
	public boolean wantsToWork() {
		return !robot.isLineBeneath();
	}

	@Override
	public void work() {
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
				robot.pilot.rotate(-currentHeading + sign*20);
				if (!suppressed) {
					robot.pilot.travel(30);
					while (!suppressed && robot.pilot.isMoving());
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
}
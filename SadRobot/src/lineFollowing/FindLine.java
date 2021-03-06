package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class FindLine extends StrategyBehavior {
	private Robot robot;
	private boolean lastHeadingLeft;
	private boolean detectEndOfLine;
	
	public FindLine(Robot robot, boolean detectEndOfLine, Strategy parent) {
		super(parent);
		
		this.robot = robot;
		this.lastHeadingLeft = true;
		this.detectEndOfLine = detectEndOfLine;
	}

	/**
	 * Wants to work if line is NOT beneath.
	 */
	@Override
	public boolean wantsToWork() {
		return !robot.isLineBeneath();
	}
	
	/**
	 * This is what it wants to do if the line is NOT beneath.
	 */
	@Override
	public void work() {
		robot.pilot.travel(10);
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
			//TODO: react to end of line
			if (turn >= 130) {
				robot.pilot.rotate(-currentHeading);
				if (!suppressed) {
					robot.pilot.travel(15);
					while (!suppressed && robot.pilot.isMoving());
					robot.pilot.stop();
					
					if (detectEndOfLine) {
						parent.stop();
					}
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
		LineFollower.lineCurvature = sign*20;
		if (Math.abs(LineFollower.lineCurvature) > 100) {
			if (Math.signum(LineFollower.lineCurvature) < 0)
				LineFollower.lineCurvature = -100;
			else
				LineFollower.lineCurvature = 100;
			
		}
		robot.pilot.setRotateSpeed(90);
	}
}
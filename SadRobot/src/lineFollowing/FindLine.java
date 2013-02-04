package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;
import common.color.ColorOracle;

public class FindLine extends StrategyBehavior {
	private Robot robot;
	private boolean lastHeadingLeft;
	private boolean detectEndOfLine;
	
	
	private boolean isLineBeneath(){
		return (Color.SILVER == this.robot.color.getColor(Color.BLACK, Color.SILVER));
	}
	
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
		
		return !this.isLineBeneath();
	}
	
	/**
	 * This is what it wants to do if the line is NOT beneath.
	 */
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
		// 		while (!suppressed && !robot.isLineBeneath()) {

		while (!suppressed && !this.isLineBeneath()) {
			//if we would rotate too far
			//TODO: react to end of line
			if (turn >= 180) {
				robot.pilot.rotate(-currentHeading + sign*20);
				if (!suppressed) {
					robot.pilot.travel(30);
					while (!suppressed && robot.pilot.isMoving());
					robot.pilot.stop();
				}
				break;
			} else {
				for (int i=0; i<2 && !suppressed && !this.isLineBeneath(); i++) {	
					robot.pilot.rotate(-currentHeading + sign*turn, true);
					while (!suppressed && !this.isLineBeneath() && robot.pilot.isMoving());	
					robot.pilot.stop();
					currentHeading = sign*turn;
				
					if (!this.isLineBeneath())
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
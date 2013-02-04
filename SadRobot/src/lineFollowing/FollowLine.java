package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class FollowLine extends StrategyBehavior {
	private Robot robot;
	
	private boolean isLineBeneath(){
		return (Color.SILVER == this.robot.color.getColor(Color.BLACK, Color.SILVER));
	}
	
	public FollowLine(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot; 
	}
	
	/**
	 * Wants to work if line is beneath.
	 */
	@Override
	public boolean wantsToWork() {
		return this.isLineBeneath();
	}
	
	/**
	 * This is what it wants to do if the line is beneath.
	 */
	@Override
	public void work() {
		robot.pilot.steer(LineFollower.lineCurvature);
		while (!suppressed && this.isLineBeneath());
		robot.pilot.stop();
	}
}

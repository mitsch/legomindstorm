package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class FollowLine extends StrategyBehavior {
	private Robot robot;
	
	public FollowLine(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot; 
	}
	
	/**
	 * Wants to work if line is beneath.
	 */
	@Override
	public boolean wantsToWork() {
		return robot.isLineBeneath();
	}
	
	/**
	 * This is what it wants to do if the line is beneath.
	 */
	@Override
	public void work() {
		robot.pilot.steer(LineFollower.lineCurvature);
		while (!suppressed && robot.isLineBeneath());
	}
}

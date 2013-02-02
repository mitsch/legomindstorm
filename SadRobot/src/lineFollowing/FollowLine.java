package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class FollowLine extends StrategyBehavior {
	private Robot robot;
	
	public FollowLine(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot; 
	}

	@Override
	public boolean wantsToWork() {
		return robot.isLineBeneath();
	}

	@Override
	public void work() {
		robot.pilot.steer(LineFollower.lineCurvature);
		while (!suppressed && robot.isLineBeneath());
		robot.pilot.stop();
	}
}

package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class LineFinder extends StrategyBehavior {
	private Robot robot;
	
	public LineFinder(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	protected boolean wantsToWork() {
		return LineFollower.mode == LineFollower.Mode.AVOID_OBSTACLE
				&& robot.isLineBeneath(); 
	}

	@Override
	protected void work() {
		LineFollower.mode = LineFollower.Mode.FOLLOW_LINE;
		robot.arm.alignCenter();
		robot.pilot.rotate(90);
	}
}

package lineFollowing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class ObstacleAvoider extends StrategyBehavior {
	private Robot robot;
	
	public ObstacleAvoider(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	protected boolean wantsToWork() {
		return LineFollower.mode == LineFollower.Mode.AVOID_OBSTACLE
				|| robot.sonar.getDistance() < 20;
	}

	@Override
	protected void work() {
		switch (LineFollower.mode) {
		case FOLLOW_LINE:
			robot.pilot.rotate(90);
			robot.arm.alignRight();
			LineFollower.mode = LineFollower.Mode.AVOID_OBSTACLE;
			break;
		case AVOID_OBSTACLE:			
			while (!suppressed) {
				int distance = robot.sonar.getDistance();
				if (distance < 15) {
						robot.pilot.steer((15-distance)*10);
				} else if (distance > 20) {
					double adjust = Math.min(5.0f/3*distance - 7/4.0f, 70.0f);
						robot.pilot.steer(-adjust);
				} else
					robot.pilot.forward();
			}	
			break;
		}
	}
}

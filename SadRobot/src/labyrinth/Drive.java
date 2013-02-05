package labyrinth;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class Drive extends StrategyBehavior {
	private Robot robot;
	
	public Drive(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return true;
	}

	@Override
	public void work() {
		while (!suppressed) {
			int distance = robot.sonar.getDistance();
			if (distance < 15) {
				if (WallFollower.sonarLeft)
					robot.pilot.steer((distance-15)*10);
				else
					robot.pilot.steer((15-distance)*10);
			} else if (distance > 20) {
				double adjust = Math.min(5.0f/3*distance - 7/4.0f, 70.0f);
				if (WallFollower.sonarLeft)
					robot.pilot.steer(adjust);
				else
					robot.pilot.steer(-adjust);
			} else
				robot.pilot.forward();
		}
	}
}

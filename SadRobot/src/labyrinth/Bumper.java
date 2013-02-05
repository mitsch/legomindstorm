package labyrinth;

import testing.SonarTester;
import labyrinth.WallFollower.BumpResult;
import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

/**
 * This Behavior enables the robot to save obstacles in the labyrinth map when
 * he bumps into them.
 * @author Thomas
 *
 */
public class Bumper extends StrategyBehavior {
	private BumpResult bumpResult;
	private Robot robot;
	
	public Bumper(Robot robot, BumpResult bumpResult, Strategy parent) {
		super(parent);
		this.robot = robot;
		this.bumpResult = bumpResult;
	}

	@Override
	public boolean wantsToWork() {
		return robot.leftTouch.isPressed() || robot.rightTouch.isPressed();
	}

	@Override
	public void work() {
		switch (bumpResult) {
		case NONE:
			break;
		case HALT:
			robot.pilot.travel(-10);
			robot.pilot.rotate(90);
			parent.stop();
			break;
		case HALT_AGGRESSIVE:
			robot.pilot.rotate(90);
			parent.stop();
			break;
		case TURN:
			robot.pilot.travel(-10);
			robot.pilot.rotate(-90);
			break;
		case EVADE:
			//short sign;
			if (robot.leftTouch.isPressed() && robot.rightTouch.isPressed()) {
				robot.pilot.travel(-10);
				if (WallFollower.sonarLeft)
					robot.pilot.rotate(-90);
				else
					robot.pilot.rotate(90);
				return;
			}/* else if (robot.leftTouch.isPressed())
				sign = -1;
			else if (robot.leftTouch.isPressed())
				sign = 1;
			else
				sign = 0;
			
			robot.pilot.travel(-10, true);
			while (!suppressed && robot.pilot.isMoving());				
			robot.pilot.rotate(sign*30, true);
			while (!suppressed && robot.pilot.isMoving());
			robot.pilot.travel(20, true);
			while (!suppressed && robot.pilot.isMoving());
			robot.pilot.travel(-30*sign, true);
			while (!suppressed && robot.pilot.isMoving());*/
		}
	}
}

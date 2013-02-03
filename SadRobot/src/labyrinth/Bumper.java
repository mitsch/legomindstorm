package labyrinth;

import labyrinth.LabyrinthStrategy.BumpResult;
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
		case HALT:
			robot.pilot.travel(-10);
			robot.pilot.rotate(-90);
			parent.stop();
			break;
		case TURN:
			robot.pilot.travel(-10);
			robot.pilot.rotate(-90);
			break;
		case EVADE:
			short sign;
			if (robot.leftTouch.isPressed())
				sign = -1;
			else if (robot.leftTouch.isPressed())
				sign = 1;
			else
				sign = 0;
			
			robot.pilot.travel(-10, true);
			while (!suppressed && robot.pilot.isMoving());				
			robot.pilot.arc(sign*20, 45, true);	
			while (!suppressed && robot.pilot.isMoving());
			robot.pilot.arc(-20*sign, 45, true);
			while (!suppressed && robot.pilot.isMoving());
		}
	}
}

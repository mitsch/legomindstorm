package racing;

import common.Robot;
import racing.Racer;

import lejos.robotics.subsumption.Behavior;

public class FindWall implements Behavior {
	
	private boolean suppressed;
	private Robot robot;

	public FindWall(Robot robot) {
		this.suppressed = false;
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return 
	}

	@Override
	public void action() {
		suppressed = false;

		robot.pilot.stop();
		robot.joker.rotateTo(robot.getLeftJoker());
		robot.joker.setSpeed(45);
		robot.joker.rotate(180, true);
		while (robot.joker.isMoving()) {
			
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

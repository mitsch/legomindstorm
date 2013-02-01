package naiveLabyrinth;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class ScanFront implements Behavior {
	private Robot robot;
	private boolean suppressed;
	
	public ScanFront(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return robot.leftMotor.getTachoCount() > 1000;
	}

	@Override
	public void action() {
		robot.alignLightMiddle();
		if (robot.sonar.getDistance() < 50) {
			if (NaiveLabyrinthSolver.sonarLeft) {
				robot.pilot.rotate(45, true);
				while (!suppressed && robot.pilot.isMoving());
			} else {
				robot.pilot.rotate(-45, true);
				while (!suppressed && robot.pilot.isMoving());
			}
			robot.pilot.stop();
		} else {
			robot.leftMotor.resetTachoCount();
		}
		robot.joker.rotateTo(-45);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

package naiveLabyrinth;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class WallToClose implements Behavior {
	private Robot robot;
	
	public WallToClose(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return robot.sonar.getDistance() < 30;
	}

	@Override
	public void action() {
		if (NaiveLabyrinthSolver.sonarLeft)
			robot.pilot.steerBackward(100);
		else
			robot.pilot.steerBackward(-100);
	}

	@Override
	public void suppress() {
	}

}

package naiveLabyrinth;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class WallToFar implements Behavior {
	private Robot robot;
	
	public WallToFar(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return robot.sonar.getDistance() > 50;
	}

	@Override
	public void action() {
		if (NaiveLabyrinthSolver.sonarLeft)
			robot.pilot.steer(60);
		else
			robot.pilot.steer(-60);
	}

	@Override
	public void suppress() {
	}

}

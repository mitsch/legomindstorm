package labyrinth;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class Drive implements Behavior {
	private Robot robot;
	private boolean suppressed;
	
	public Drive(Robot robot) {
		this.robot = robot;
		this.suppressed = false;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		
		while (!suppressed) {
			int distance = robot.sonar.getDistance();
			if (distance < 15) {
				if (LabyrinthSolver.sonarLeft)
					robot.pilot.steer((distance-15)*10);
				else
					robot.pilot.steer((15-distance)*10);
			} else if (distance > 20) {
				double adjust = Math.min(5.0f/3*distance - 7/4.0f, 70.0f);
				if (LabyrinthSolver.sonarLeft)
					robot.pilot.steer(adjust);
				else
					robot.pilot.steer(-adjust);
			} else
				robot.pilot.forward();
		}
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}
}

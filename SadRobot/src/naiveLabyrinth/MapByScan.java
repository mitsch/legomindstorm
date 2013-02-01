package naiveLabyrinth;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class MapByScan implements Behavior {
	private Robot robot;
	private boolean suppressed;
	
	public MapByScan(Robot robot) {
		this.robot = robot;
		suppressed = false;
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppressed = false;
		
		robot.alignLightLeft();
		if (!suppressed)
			robot.alignLightRight();
		robot.alignLightMiddle();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}

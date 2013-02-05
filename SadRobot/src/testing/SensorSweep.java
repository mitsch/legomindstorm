package testing;

import common.Robot;

public class SensorSweep {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		robot.arm.alignLeft();
		robot.arm.rotateTo(180);

	}

}

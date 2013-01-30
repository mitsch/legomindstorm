package testing;

import common.Robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class RotateSpeed {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();	
		LCD.drawString(String.valueOf(robot.pilot.getRotateSpeed()), 0, 0);
		Button.waitForAnyPress();
	}
}

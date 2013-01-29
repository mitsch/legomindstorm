package testing;

import lejos.nxt.Sound;
import common.Robot;

public class MotorTester1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		int rotations = 10;
		
		for (int i=0; i<rotations; i++) {
			robot.pilot.rotate(360);
			Sound.beep();
		}

	}

}

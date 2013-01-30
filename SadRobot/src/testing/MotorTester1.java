package testing;

import lejos.nxt.Button;
import lejos.nxt.Sound;
import common.Robot;

public class MotorTester1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		int rotations = 1;
		
		Button.waitForAnyPress();
		
		for (int i=0; i<rotations; i++) {
			robot.pilot.rotate(360);
			Sound.beep();
			if (Button.readButtons() == Button.ID_ESCAPE)
				break;
		}
	}
}

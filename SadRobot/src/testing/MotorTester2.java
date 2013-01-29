package testing;

import lejos.nxt.Sound;
import lejos.util.Delay;
import common.Robot;

public class MotorTester2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		int[] rotations = {90, -180, -90, 180};
		for (int rotation : rotations) {
			robot.pilot.rotate(rotation);
			Sound.beep();
			Delay.msDelay(5000);
		}
	}
}

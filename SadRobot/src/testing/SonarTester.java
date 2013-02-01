package testing;

import common.Robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

public class SonarTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		while (Button.readButtons() != Button.ID_ESCAPE) {
			int distance = robot.sonar.getDistance();
			LCD.drawInt(distance, 0, 0);
			if (distance < 20) {
				Sound.beepSequenceUp();
			}
		}

	}

}

package testing;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Delay;
import common.Robot;

public class DisplaySonar {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		while (Button.readButtons() != Button.ID_ESCAPE) {
			robot.sonar.ping();
			LCD.drawString("    ", 0, 0);
			LCD.drawInt(robot.sonar.getDistance(), 0, 0);
			
			Delay.msDelay(1000);
		}
	}
}

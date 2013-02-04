package testing;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import common.Robot;

public class DisplayLight {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot sadRobot = new Robot();
		
		while (Button.readButtons() != Button.ID_ESCAPE) {
			LCD.drawInt(sadRobot.color.getNormalizedLightValue(), 0, 0);
			LCD.drawInt(sadRobot.color.getLightValue(), 0, 1);
			LCD.drawInt(sadRobot.color.readValue(), 0, 2);
		}
	}
}

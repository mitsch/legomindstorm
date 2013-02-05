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
			System.out.println(sadRobot.light.readValue());
		}
	}
}

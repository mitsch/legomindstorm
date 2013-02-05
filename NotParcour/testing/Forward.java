package testing;

import lejos.nxt.Button;
import common.Robot;

public class Forward {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		robot.pilot.forward();
		while (Button.readButtons() != Button.ID_ESCAPE);
		robot.pilot.stop();

	}

}

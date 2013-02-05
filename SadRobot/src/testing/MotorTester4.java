package testing;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import common.Robot;

public class MotorTester4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		robot.joker.flt();
		
		while (Button.readButtons() != Button.ID_ESCAPE)
			LCD.drawInt(robot.joker.getTachoCount(), 0, 0);
	}
}

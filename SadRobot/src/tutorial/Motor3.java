package tutorial;

import lejos.nxt.*;

public class Motor3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LCD.drawString("Program 3", 0, 0);
		
		Motor.A.rotate(4*360);
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 0);
		Motor.A.rotateTo(0);
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 1);
		Button.waitForAnyPress();
	}
}

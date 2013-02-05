package tutorial;

import lejos.nxt.*;
import lejos.util.Delay;

public class Motor4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LCD.drawString("Program 4", 0, 0);
		Motor.A.rotateTo(-4*360, true);
		
		while (Motor.A.isMoving()) {
			Delay.msDelay(200);
			LCD.drawInt(Motor.A.getTachoCount(), 0, 0);
			if (Button.readButtons() > 0) Motor.A.stop();
		}
		LCD.drawInt(Motor.A.getTachoCount(), 0, 0);
		Button.waitForAnyPress();
	}

}

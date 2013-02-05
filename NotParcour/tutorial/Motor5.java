package tutorial;

import lejos.nxt.*;
import lejos.util.Delay;

public class Motor5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LCD.drawString("Program 5", 0, 0);
		NXTRegulatedMotor[] motors = { Motor.A, Motor.B, Motor.C };
		for (NXTRegulatedMotor motor : motors) {
			motor.setSpeed(720);
		}
		for (NXTRegulatedMotor motor : motors) {
			motor.rotate(720, true);
		}
		for (int i=0; i<8; i++) {
			Delay.msDelay(200);
			LCD.drawInt(Motor.A.getTachoCount(), 0, i);
			LCD.drawInt(Motor.B.getTachoCount(), 5, i);
			LCD.drawInt(Motor.C.getTachoCount(), 10, i);
		}
		Button.waitForAnyPress();
	}

}

package tutorial;

import lejos.nxt.*;
import lejos.util.Delay;

public class Motor2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LCD.drawString("Program 2", 0, 0);
		Motor.A.setSpeed(720);
		Motor.A.forward();
		Delay.msDelay(2000);
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 0);
		Motor.A.stop();
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 1);

		Motor.A.backward();
		while(Motor.A.getTachoCount()>0);
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 2);
		Motor.A.stop();
		LCD.drawString(String.valueOf(Motor.A.getTachoCount()), 0, 3);
		
		Button.waitForAnyPress();	
	}
}

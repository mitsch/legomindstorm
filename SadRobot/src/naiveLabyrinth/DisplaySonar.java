package naiveLabyrinth;

import lejos.nxt.LCD;
import lejos.util.Delay;
import common.Robot;

public class DisplaySonar extends Thread {
	private Robot robot;

	/**
	 * @param args
	 */
	public DisplaySonar(Robot robot) {
		this.robot = robot;
	}
	
	public void run() {
		LCD.drawString("    ", 0, 0);
		LCD.drawInt(robot.sonar.getDistance(), 0, 0);
		
		Delay.msDelay(1000);
	}
}

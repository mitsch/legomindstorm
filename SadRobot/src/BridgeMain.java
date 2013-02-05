import lejos.nxt.Button;
import lejos.util.Delay;
import common.Robot;

import bridgePasser.BridgeStrategy;


public class BridgeMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		Button.waitForAnyPress();
		Delay.msDelay(2000);
		BridgeStrategy bridge = new BridgeStrategy(robot, false);
		bridge.start();
	}
}

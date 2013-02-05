import labyrinth.WallFollower;
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
		
		WallFollower wall = new WallFollower(robot, false,
				WallFollower.BumpResult.NONE,
				WallFollower.AbortCondition.WOOD);
		wall.start();
		
		robot.pilot.travel(20);
		
		BridgeStrategy bridge = new BridgeStrategy(robot, false);
		bridge.start();
	}
}

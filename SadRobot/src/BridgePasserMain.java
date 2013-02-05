import lejos.nxt.Button;
import bridgePasser.BridgeStrategy;
import common.Robot;

public class BridgePasserMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		Button.waitForAnyPress();
		passBridge(robot, true);
	}

	public static void passBridge(Robot robot, boolean leftBridge) {
		if (leftBridge)
			robot.arm.alignRight();
		else
			robot.arm.alignLeft();
		BridgeStrategy passer = new BridgeStrategy(robot, leftBridge);
		passer.start();
	}
}

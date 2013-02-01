import lejos.nxt.Button;
import bridgePasser.BridgePasser;
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
			robot.alignLightRight();
		else
			robot.alignLightLeft();
		BridgePasser passer = new BridgePasser(robot, leftBridge);
		passer.go();
	}
}

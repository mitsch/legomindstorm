package bridgePasser;

import common.Robot;
import common.Strategy;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class BridgeStrategy  implements Strategy {
	private Arbitrator bridgePasser;
	public static boolean bridgePassed;
	
	public BridgeStrategy(Robot robot, boolean leftBridge) {
		Behavior drive = new DriveBridge(robot, leftBridge);
		Behavior avoidFalling = new AvoidFall(robot, leftBridge);
		Behavior lineRecognizer = new LineRecognizer(robot);
		
		Behavior[] behaviors = {drive, avoidFalling, lineRecognizer};
		
		bridgePasser = new Arbitrator(behaviors, true);
	}
	
	@Override
	public void start() {
		bridgePasser.start();
	}
	
	@Override
	public void stop() {	
		bridgePassed = true;
	}
}

package bridgePasser;

import common.Robot;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class BridgePasser {
	private Arbitrator bridgePasser;
	public static boolean bridgePassed;
	
	public BridgePasser(Robot robot, boolean leftBridge) {
		Behavior drive = new DriveBridge(robot, leftBridge);
		Behavior avoidFalling = new AvoidFall(robot, leftBridge);
		
		Behavior[] behaviors = {drive, avoidFalling};
		
		bridgePasser = new Arbitrator(behaviors, true);
	}
	
	public void go() {
		bridgePasser.start();
	}
}

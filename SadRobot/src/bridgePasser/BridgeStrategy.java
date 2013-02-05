package bridgePasser;

import common.Strategy;
import common.Robot;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Using this strategy, we cross the bridge by tagging along the edge.
 * If the light sensor is over the edge, we turn.
 * If not, we drive in a slight curve.
 * 
 * @author Thomas
 *
 */
public class BridgeStrategy  extends Strategy {
	
	public BridgeStrategy(Robot robot, boolean leftBridge) {
		Behavior drive = new DriveBridge(robot, leftBridge, this);
		Behavior avoidFalling = new AvoidFall(robot, leftBridge, this);
		Behavior lineRecognizer = new LineRecognizer(robot, this);
		
		Behavior[] behaviors = {drive, avoidFalling, lineRecognizer};
		
		arbitrator = new Arbitrator(behaviors, true);
		
		if (leftBridge)
			robot.arm.alignRight();
		else
			robot.arm.alignLeft();
	}
}
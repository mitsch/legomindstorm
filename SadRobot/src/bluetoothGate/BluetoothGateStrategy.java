/**
 * 
 */
package bluetoothGate;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lineFollowing.FindLine;
import lineFollowing.FollowLine;
import lineFollowing.LineFollower.AbortCondition;
import lineFollowing.ObstacleDetector;
import lineFollowing.WoodDetector;

import common.Robot;
import common.Strategy;

/**
 * @author Admin
 *
 */
public class BluetoothGateStrategy extends Strategy {
	
	Arbitrator arbitrator;
	
	
	public BluetoothGateStrategy(Robot robot, AbortCondition abort) {
		

		Behavior openGateBehavior = new OpenGateBehavior(this);
		
		
		
		
		Behavior followLine = new FollowLine(robot, this);
		
		Behavior findLine = new FindLine(robot,
				abort == AbortCondition.END_OF_THE_LINE, this);
		Behavior detectObstacle = new ObstacleDetector(robot, this);
		Behavior detectWood = new WoodDetector(robot, this);
		
		Behavior[] behaviors;
		if (abort == AbortCondition.END_OF_THE_LINE)
			behaviors = new Behavior[2];
		else
			behaviors = new Behavior[3];
		behaviors[0] = findLine;
		behaviors[1] = followLine;
		
		//add the abort condition
		switch (abort) {
		case OBSTACLE:
			behaviors[2] = detectObstacle;
			break;
		case WOOD:
			behaviors[2] = detectWood;
			break;
		case END_OF_THE_LINE:
			break;
		}
		
		arbitrator = new Arbitrator(behaviors, true);
		
	}
	
	/**
	 * Open sesame! This gate should open if we ask it nicely per BlueTooth.
	 */
//	public static void bluetoothGate() {
//		robot.sonar.continuous();
//			
//		//drive up to the gate		
//		robot.alignLightMiddle();
//		while (robot.sonar.getDistance() > 20) {
//			if (aborted)
//				return;
//		}
//		
//		//connect and open
//		GateControl gateControl = new GateControl();
//		while (!aborted && 
//				!gateControl.connectionToGateSuccessful(GateCommon.GATE_1)) {
//			if (aborted)
//				return;
//		}
//		
//		//navigate through it until you find a line
//		robot.pilot.forward();	
//		
//		turnTable();
//	}
		
}

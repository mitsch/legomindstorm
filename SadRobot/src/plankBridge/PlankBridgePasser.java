package plankBridge;

import common.Robot;
import common.Strategy;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Because our other line following algorithm has difficulties on the
 * plank bridge, we use a more naive method.
 * 
 * @author Thomas
 *
 */
public class PlankBridgePasser extends Strategy {
	
	public PlankBridgePasser(Robot robot) {
		Behavior followLine = new White(robot, this);
		Behavior findLine = new Black(robot, this);
		
		Behavior[] behaviors = {findLine, followLine};
		
		arbitrator = new Arbitrator(behaviors, true);
	}
}

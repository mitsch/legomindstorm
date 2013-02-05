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
	public static long started;
	
	public PlankBridgePasser(Robot robot) {
		Behavior followLine = new White(robot, this);
		Behavior findLine = new Black(robot, this);
		Behavior stop = new PlankStopper(this);
		
		Behavior[] behaviors = {findLine, followLine, stop};
		
		arbitrator = new Arbitrator(behaviors, true);
	}
	
	@Override
	public void start() {
		PlankBridgePasser.started = System.currentTimeMillis();
		arbitrator.start();
	}
}

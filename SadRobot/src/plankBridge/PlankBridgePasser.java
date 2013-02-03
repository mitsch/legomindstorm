package plankBridge;

import common.Robot;
import common.Strategy;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class PlankBridgePasser extends Strategy {
	
	public PlankBridgePasser(Robot robot) {
		Behavior followLine = new White(robot, this);
		Behavior findLine = new Black(robot, this);
		
		Behavior[] behaviors = {findLine, followLine};
		
		arbitrator = new Arbitrator(behaviors, true);
	}
}

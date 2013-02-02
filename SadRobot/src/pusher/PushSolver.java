package pusher;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import common.Robot;
import common.Strategy;

public class PushSolver extends Strategy {
	public static boolean measured = false;
	
	public PushSolver(Robot robot) {
		Behavior measurePusher = new PusherMeasurer(robot, this);
		Behavior leadFoot = new LeadFootDriver(robot, this);
		
		Behavior[] behaviors = {measurePusher, leadFoot};
		
		arbitrator = new Arbitrator(behaviors, true);
	}
}

package pusher;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import common.Robot;

public class PushSolver {
	private Arbitrator pushSolver;
	public static boolean measured = false;
	public static boolean passed = false;
	
	public PushSolver(Robot robot) {
		Behavior measurePusher = new PusherMeasurer(robot);
		Behavior leadFoot = new LeadFootDriver(robot);
		
		Behavior[] behaviors = {measurePusher, leadFoot};
		
		pushSolver = new Arbitrator(behaviors, true);
	}
	
	public void go() {
		pushSolver.start();
	}
}

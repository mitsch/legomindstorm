package lineFollowing;

import common.Robot;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LineFollower {
	private Arbitrator lineFollower;
	public static int lineCurvature = 0;
	
	public LineFollower(Robot robot) {
		Behavior followLine = new FollowLine(robot);
		Behavior findLine = new FindLine(robot);
		
		Behavior[] behaviors = {followLine, findLine};
		
		lineFollower = new Arbitrator(behaviors, true);
	}
	
	public void go() {
		lineFollower.start();
	}

}

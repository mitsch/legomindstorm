package lineFollowing;

import common.Robot;
import common.Strategy;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LineFollower extends Strategy {
	public static int lineCurvature = 0;
	
	public LineFollower(Robot robot) {
		Behavior followLine = new FollowLine(robot, this);
		Behavior findLine = new FindLine(robot, this);
		
		Behavior[] behaviors = {followLine, findLine};
		
		arbitrator = new Arbitrator(behaviors, true);
	}
}

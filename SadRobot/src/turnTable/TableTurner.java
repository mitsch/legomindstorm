package turningTable;

import common.Robot;
import common.Strategy;
import lineFollowing.FollowLine;
import lineFollowing.FindLine;
import turningTable.EnterBox;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class TableTurner extends Strategy {
	public static boolean passedBox;
	public static TurnControl client;

	public TableTurner(Robot robot) {
		Behavior followLine = new FollowLine(robot, this);
		Behavior findLine = new FindLine(robot, true, this);
		Behavior enterBox = new EnterBox(robot, this);
		passedBox = false;

		Behavior [] behaviors = {findLine, followLine, enterBox};

		arbitrator = new Arbitrator(behaviors, true);
	}
}


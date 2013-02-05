package turningTable;

import common.Robot;
import common.Strategy;
import lineFollowing.FollowLine;
import lineFollowing.FindLine;
import turningTable.EnterBox;
import turningTable.WaitForBox;
import common.gates.TurnControl;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class TableTurner extends Strategy {
	public static TurnControl client;

	public TableTurner(Robot robot) {
		client = new TurnControl();
		Behavior followLine = new FollowLine(robot, this);
		Behavior findLine = new FindLine(robot, true, this);
		Behavior enterBox = new EnterBox(robot, this);
		Behavior waitForBox = new WaitForBox(robot, this);

		Behavior [] behaviors = {findLine, followLine, enterBox, waitForBox};

		arbitrator = new Arbitrator(behaviors, true);
	}
}


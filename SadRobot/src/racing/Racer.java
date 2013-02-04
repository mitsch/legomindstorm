package racing;

import common.Robot;
import common.Strategy;
import racing.FollowWall;
import racing.FindWall;

import lejos.nxt.Sound;
import lejos.util.Delay;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Racer extends Strategy
{
	public static int regularDistance;
	
	public Racer(Robot robot) {
		FollowWall followWall = new FollowWall(robot, this);
		FindWall findWall = new FindWall(robot, this);
		Behavior [] behaviours = {followWall, findWall};

		arbitrator = new Arbitrator(behaviours, true);		

		robot.joker.rotateTo(robot.getLeftJoker());
		int leftDistance = robot.sonar.getDistance();
		robot.joker.rotateTo(robot.getRightJoker());
		int rightDistance = robot.sonar.getDistance();

		if (leftDistance == 255 && rightDistance == 255)	regularDistance = 50;
		else if (leftDistance == 255)	regularDistance = rightDistance;
		else if (rightDistance == 255)	regularDistance = leftDistance;
		else	regularDistance = (leftDistance + rightDistance) / 2;
		
		if (leftDistance < rightDistance)
			robot.joker.rotateTo(robot.getLeftJoker() + 20);
		else
			robot.joker.rotateTo(robot.getRightJoker() - 20);
		
		robot.pilot.forward();
	}
}


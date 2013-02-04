package racing;

import common.Robot;
import common.Strategy;
import racing.FollowWall;
import racing.FindWall;
import racing.MakeTurn;

import lejos.nxt.Sound;
import lejos.util.Delay;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Racer extends Strategy
{
	public static int regularDistance = 38;
	public static int angleOffsetJoker = 10;
	
	public Racer(Robot robot) {
		MakeTurn makeTurn = new MakeTurn(robot, this);
		FollowWall followWall = new FollowWall(robot, this);
		Behavior [] behaviours = {followWall, makeTurn};

		arbitrator = new Arbitrator(behaviours, true);		
/*
		robot.joker.rotateTo(robot.getRightJoker());
		int positionOfShortestDistance = robot.getRightJoker();
		regularDistance = 255;
		robot.joker.setSpeed(15);
		robot.joker.rotate(-15, true);
		while (robot.joker.isMoving()) {
			int distance = robot.sonar.getDistance();
			int position = robot.joker.getPosition();
			if (distance < regularDistance) {
				positionOfShortestDistance = position;
				regularDistance = distance;
			}
		}

		System.out.println(Integer.toString(regularDistance));
		robot.joker.rotateTo(positionOfShortestDistance);
*/
		robot.joker.rotateTo(robot.getRightJoker() - angleOffsetJoker);
		
		robot.pilot.setTravelSpeed(robot.pilot.getMaxTravelSpeed());
		robot.pilot.forward();
	}
}


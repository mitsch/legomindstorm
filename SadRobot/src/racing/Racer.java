package racing;

import common.Robot;
import common.Strategy;
import racing.FollowWall;

import lejos.nxt.Sound;
import lejos.util.Delay;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Racer extends Strategy
{
	public static int regularDistance;
	
	public Racer(Robot robot) {
		FollowWall followWall = new FollowWall(robot, this);
		Behavior [] behaviours = {followWall};

		arbitrator = new Arbitrator(behaviours, true);

		robot.joker.rotateTo(robot.getLeftJoker());
		int leftDistance = robot.sonar.getDistance();
		robot.joker.rotateTo(robot.getRightJoker());
		int rightDistance = robot.sonar.getDistance();

		regularDistance = (leftDistance + rightDistance) / 2;
		
		if (leftDistance < rightDistance)
			robot.joker.rotateTo(robot.getLeftJoker() + 20);
		else
			robot.joker.rotateTo(robot.getRightJoker() - 20);

		Sound.playTone(440, 500);
		Delay.msDelay(500);
		Sound.playTone(440, 500);
		Delay.msDelay(500);
		Sound.playTone(440, 500);
		Delay.msDelay(500);
		Sound.playTone(659, 1000);
		
		robot.pilot.forward();
	}
}


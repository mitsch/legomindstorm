package racing;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import racing.Racer;
import lejos.nxt.Sound;
import java.lang.Math;



public class FollowWall extends StrategyBehavior {
	private Robot robot;
	private int distance;
	private int seriousDistanceDrop;
	
	public FollowWall(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
		this.seriousDistanceDrop = 20;
		this.distance = Racer.regularDistance;
	}

	@Override
	public boolean wantsToWork() {
		distance = robot.sonar.getDistance();
		//return distance != 255 && Math.abs(distance - Racer.regularDistance) < seriousDistanceDrop;
		// return distance != 255;
		return Math.abs(distance - Racer.regularDistance) < seriousDistanceDrop;
	}

	@Override
	public void work() {
		int deltaDistance = distance - Racer.regularDistance;
		int factor = robot.getMiddleJoker() > robot.arm.getPosition() ? 3 : -5;

		System.out.println("correct to " + Integer.toString(factor * deltaDistance));
		robot.pilot.steer(factor *  deltaDistance);
	}
}

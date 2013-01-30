package lineFollowing;

import java.util.Random;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class FindLine implements Behavior {
	private Robot robot;
	private boolean suppressed;
	private Random rnd;
	
	public FindLine(Robot robot) {
		this.robot = robot;
		this.suppressed = false;
		this.rnd = new Random();
	}

	@Override
	public boolean takeControl() {
		return !robot.isLineBeneath();
	}

	@Override
	public void action() {
		suppressed = false;
		
		//find line by rotating a bit to the right and left	
		int turn = 10;
		short sign;
		if(rnd.nextBoolean())
			sign = -1;
		else
			sign = 1;
		
		while (!suppressed && !robot.isLineBeneath()) {
			robot.pilot.rotate(sign * turn, true);
			while (!suppressed && !robot.isLineBeneath() && robot.pilot.isMoving());	
			robot.pilot.stop();
			
			turn *= 2;
			sign *= -1;
			
			if (turn >= 200) {
				robot.pilot.rotate(sign * 110);
				robot.pilot.travel(30);
			}
		}
		
		//update the estimated lineCurvature
		LineFollower.lineCurvature -= (10 * sign);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
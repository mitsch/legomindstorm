package racing;

import common.Robot;
import racing.Racer;
import lejos.nxt.Sound;


import lejos.robotics.subsumption.Behavior;

public class FollowWall implements Behavior {
	private boolean suppressed;
	private Robot robot;
	private int prevDistance;
	private float prevTacho;
	private int seriousDistanceDrop;
	
	public FollowWall(Robot robot) {
		this.robot = robot; 
		this.suppressed = false;
		this.seriousDistanceDrop = 5;
	}

	@Override
	public boolean takeControl() {
		return !suppressed;
	}

	@Override
	public void action() {
		suppressed = false;

		while (!robot.leftTouch.isPressed());

		prevDistance = racing.Racer.regularDistance;
		prevTacho = robot.pilot.getMovementIncrement();


		robot.joker.rotateTo(robot.getLeftJoker());
		robot.sonar.continuous();
		robot.pilot.forward();

		while (!suppressed && !robot.leftTouch.isPressed() && !robot.rightTouch.isPressed())
		{

			int curDistance = robot.sonar.getDistance();
			float curTacho = robot.pilot.getMovementIncrement();
			int deltaDistance = curDistance - racing.Racer.regularDistance;

			System.out.println(Integer.toString(curDistance) + " " + Float.toString(curTacho));

			
			if (java.lang.Math.abs(deltaDistance) >= seriousDistanceDrop) {
				racing.Racer.regularDistance = curDistance;
				System.out.println("regular = " + Integer.toString(curDistance));
				Sound.buzz();
			}
			else if (java.lang.Math.abs(deltaDistance) >= 2) {
				int newSteer = 10 * deltaDistance / ((int)(curTacho - prevTacho) + 1);
				robot.pilot.steer(newSteer);
				System.out.println("correct to " + Integer.toString(newSteer));
			}
			else {
				System.out.println("none");
			}
			prevTacho = curTacho;
			prevDistance = curDistance;
		}

		robot.pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

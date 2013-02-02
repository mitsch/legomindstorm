package racing;

import common.Robot;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Racer
{
	private Arbitrator racer;
	public static int regularDistance;
	
	public Racer(Robot robot) {
		Behavior followWall = new FollowWall(robot);
		Behavior [] behaviours = {followWall};

		racer = new Arbitrator(behaviours, true);
	}


	public void go() {
		racer.start();
	}
}


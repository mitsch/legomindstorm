package pusher;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class PusherMeasurer implements Behavior {
	private Robot robot;
	
	public PusherMeasurer(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return !PushSolver.measured;
	}

	@Override
	public void action() {
		//try to determine  the frequency of the pusher
	}

	@Override
	public void suppress() {
	}

}

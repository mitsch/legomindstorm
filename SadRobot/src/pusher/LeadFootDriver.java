package pusher;

import common.Robot;

import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class LeadFootDriver implements Behavior {
	private Robot robot;
	
	public LeadFootDriver(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return PushSolver.measured && !PushSolver.passed;
	}

	@Override
	public void action() {
		//BURNOUT!!!
		robot.pilot.forward();
		Delay.msDelay(5000);
		PushSolver.passed = true;
	}

	@Override
	public void suppress() {
	}

}

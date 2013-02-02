package pusher;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

import lejos.util.Delay;

public class LeadFootDriver extends StrategyBehavior {
	private Robot robot;
	
	public LeadFootDriver(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return PushSolver.measured;
	}

	@Override
	public void work() {
		//BURNOUT!!!
		robot.pilot.forward();
		Delay.msDelay(5000);
		parent.stop();
	}
}

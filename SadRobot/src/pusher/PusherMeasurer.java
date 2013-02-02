package pusher;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class PusherMeasurer extends StrategyBehavior {
	private Robot robot;
	
	public PusherMeasurer(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return !PushSolver.measured;
	}

	@Override
	public void work() {
		//TODO: try to determine  the frequency of the pusher
	}
}

package lineFollowing;

import lejos.robotics.subsumption.Behavior;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

public class TimeStopper extends StrategyBehavior implements Behavior {
	
	public TimeStopper(Strategy parent) {
		super(parent);
	}

	@Override
	protected boolean wantsToWork() {
		return System.currentTimeMillis() - LineFollower.started > 25000;
	}

	@Override
	protected void work() {
		parent.stop();
	}
}

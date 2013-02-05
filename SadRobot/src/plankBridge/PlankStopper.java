package plankBridge;

import lejos.robotics.subsumption.Behavior;

import common.Strategy;
import common.StrategyBehavior;

public class PlankStopper extends StrategyBehavior implements Behavior {
	
	public PlankStopper(Strategy parent) {
		super(parent);
	}

	@Override
	protected boolean wantsToWork() {
		return System.currentTimeMillis() - PlankBridgePasser.started > 25000;
	}

	@Override
	protected void work() {
		parent.stop();
	}
}

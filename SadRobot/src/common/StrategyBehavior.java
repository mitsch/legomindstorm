package common;

import lejos.robotics.subsumption.Behavior;

public abstract class StrategyBehavior implements Behavior {
	protected Strategy parent;
	protected boolean suppressed;
	
	public StrategyBehavior(Strategy parent) {
		this.parent = parent;
		this.suppressed = false;
	}
	
	protected abstract boolean wantsToWork();
	protected abstract void work();

	@Override
	public boolean takeControl() {
		return !parent.isStopped() && wantsToWork();
	}

	@Override
	public void action() {
		suppressed = false;
		work();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

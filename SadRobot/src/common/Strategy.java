package common;

import lejos.robotics.subsumption.Arbitrator;

/**
 * A strategy is a solution pattern for a specific task, if we want to use
 * behaviors to solve it. In combination with a StrategyBehavior, it is
 * possible to stop these solution patterns.
 * (Just don't forget to use Arbitrator(behaviors[], *true*))
 * 
 * @author Thomas
 *
 */
public abstract class Strategy {
	protected Arbitrator arbitrator;
	private boolean stopped;
	
	public Strategy() {
		stopped = false;
	}
	
	public void start() {
		arbitrator.start();
	}
	
	public void stop() {
		stopped = true;
	}

	public boolean isStopped() {
		return stopped;
	}
}

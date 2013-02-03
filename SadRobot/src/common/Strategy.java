package common;

import lejos.robotics.subsumption.Arbitrator;

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

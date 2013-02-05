package turningTable;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

import turningTable.TableTurner;

public class WaitForBox extends StrategyBehavior {
	private Robot robot;
	private boolean connectionEstablished;
	
	public WaitForBox(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot; 
		this.connectionEstablished = false;
	}

	@Override
	public boolean wantsToWork() {
		return !connectionEstablished;
	}

	@Override
	public void work() {
		robot.pilot.stop();
		while (!suppressed && !TableTurner.client.connectionToTurntableSuccessful());
		connectionEstablished = true;
		robot.pilot.forward();
	}
}

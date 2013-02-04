package plankBridge;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class White extends StrategyBehavior {
	private Robot robot;
	
	private boolean isLineBeneath(){
		return (Color.SILVER == this.robot.color.getColor(Color.BLACK, Color.SILVER));
	}
	
	public White(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot; 
	}

	@Override
	public boolean wantsToWork() {
		return this.isLineBeneath();
	}

	@Override
	public void work() {
		robot.pilot.rotateLeft();
		while (!suppressed && this.isLineBeneath());
		robot.pilot.stop();
	}
}

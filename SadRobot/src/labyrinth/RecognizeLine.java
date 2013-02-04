package labyrinth;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;

public class RecognizeLine extends StrategyBehavior {
	private Robot robot;
	
	private boolean isLineBeneath(){
		return (Color.SILVER == this.robot.color.getColor(Color.BLACK, Color.SILVER));
	}
	
	public RecognizeLine(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		return this.isLineBeneath();
	}

	@Override
	public void work() {
		parent.stop();
	}
}
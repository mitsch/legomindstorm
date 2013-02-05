package labyrinth;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;
import labyrinth.WallFollower.AbortCondition;

public class RecognizeLine extends StrategyBehavior {
	private Robot robot;
	private AbortCondition abort;
	
	public RecognizeLine(Robot robot, AbortCondition abort, Strategy parent) {
		super(parent);
		this.abort = abort;
		this.robot = robot;
	}

	@Override
	public boolean wantsToWork() {
		switch (abort) {
		case LINE:
			return robot.isLineBeneath();
		case WOOD:
			return robot.isWoodBeneath();
		case COLOR:
			return robot.isLineBeneath();
		}
		return false;
	}

	@Override
	public void work() {
		parent.stop();
	}
}
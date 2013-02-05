package labyrinth;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;
import labyrinth.WallFollower.AbortCondition;

public class Stopper extends StrategyBehavior {
	private Robot robot;
	private AbortCondition abort;
	
	public Stopper(Robot robot, AbortCondition abort, Strategy parent) {
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
			return isColorBeneath();
		case TIME:
			return System.currentTimeMillis() - WallFollower.started > WallFollower.time;
		}
		return false;
	}
	
	private boolean isColorBeneath() {
		int measurement = robot.light.readNormalizedValue();
		switch (WallFollower.color) {
		case RED:
			return measurement < 485 && measurement > 450;
		case YELLOW:
			return measurement > 485 && measurement < 350;
		case GREEN:
			return measurement > 350 && measurement < 450;
		default:
			return false;
		}
	}

	@Override
	public void work() {
		parent.stop();
	}
}
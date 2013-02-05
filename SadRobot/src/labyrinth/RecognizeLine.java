package labyrinth;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;
import common.color.Color;
import labyrinth.LabyrinthStrategy.AbortCondition;

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
			return this.robot.color.getColor(Color.BLACK, Color.SILVER) == Color.SILVER;
		case WOOD:
			return this.robot.color.getColor(Color.BLACK, Color.BROWN, Color.SILVER) == Color.BROWN;
		case COLOR:
			return this.robot.color.getColor(Color.RED, Color.YELLOW, Color.GREEN) == LabyrinthStrategy.color;
		}
		return false;
	}

	@Override
	public void work() {
		parent.stop();
	}
}
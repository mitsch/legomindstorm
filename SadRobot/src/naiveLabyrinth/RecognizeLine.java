package naiveLabyrinth;

import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;
import common.Robot;

public class RecognizeLine implements Behavior {
	private Robot robot;
	
	public RecognizeLine(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return !NaiveLabyrinthSolver.solved && robot.isLineBeneath();
	}

	@Override
	public void action() {
		NaiveLabyrinthSolver.solved = true;
		Sound.beepSequenceUp();

	}

	@Override
	public void suppress() {
	}
}
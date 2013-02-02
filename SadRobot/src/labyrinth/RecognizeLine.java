package labyrinth;

import lejos.robotics.subsumption.Behavior;
import common.Robot;

public class RecognizeLine implements Behavior {
	private Robot robot;
	
	public RecognizeLine(Robot robot) {
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return !LabyrinthStrategy.solved && robot.isLineBeneath();
	}

	@Override
	public void action() {
		LabyrinthStrategy.solved = true;
	}

	@Override
	public void suppress() {
	}
}
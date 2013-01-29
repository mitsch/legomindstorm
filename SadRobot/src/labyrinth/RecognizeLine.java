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
		return !LabyrinthSolver.solved && robot.isGroundSilver();
	}

	@Override
	public void action() {
		LabyrinthSolver.solved = true;

	}

	@Override
	public void suppress() {
	}
}
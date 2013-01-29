package labyrinth;

import lejos.geom.Point;
import lejos.robotics.subsumption.Behavior;
import lejos.robotics.pathfinding.Path;
import common.Robot;

public class FindPath implements Behavior {
		
	private Robot robot;
	private LabyrinthMap labyrinth;
	private Point target;
	private Path path;
	private boolean suppressed = false;

	public FindPath(Robot robot, LabyrinthMap labyrinth) {
		this.robot = robot;
		this.labyrinth = labyrinth;
		this.target = new Point(0, 100);
		this.path = null;
	}

	@Override
	public boolean takeControl() {		
		return !LabyrinthSolver.solved;
	}

	@Override
	public void action() {
		while (!suppressed) {
			calculatePath();
			followPath();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
	
	public void calculatePath() {
		path = labyrinth.calculatePath(robot.getPose().getLocation(), target);
	}
	
	public void followPath() {
		robot.navigator.followPath(path);
		while (!suppressed && robot.navigator.isMoving());	
		robot.navigator.stop();
	}
}
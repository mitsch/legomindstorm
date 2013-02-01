package labyrinth;

import common.Robot;

import lejos.geom.Point;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;

/**
 * This Behavior enables the robot to save obstacles in the labyrinth map when
 * he bumps into them.
 * @author Thomas
 *
 */
public class MapByBump implements Behavior {
	private Robot robot;
	private LabyrinthMap labyrinth;
	
	public MapByBump(Robot robot, LabyrinthMap labyrinth) {
		this.robot = robot;
		this.labyrinth = labyrinth;
	}

	@Override
	public boolean takeControl() {
		return !LabyrinthSolver.solved && 
				(robot.leftTouch.isPressed() || robot.rightTouch.isPressed());
	}

	@Override
	public void action() {
		//calculate the position of the obstacle
		float angleOffset;
		if (robot.leftTouch.isPressed())
			angleOffset = 20;
		else
			angleOffset = -20;
		
		Pose pose = robot.getPose();
		Point touchPoint = pose.getLocation().pointAt(10, pose.getHeading()
				+ angleOffset);
		
		//save the obstacle in the labyrinth map
		labyrinth.setField(touchPoint, LabyrinthMap.Element.OBSTACLE);
	}

	@Override
	public void suppress() {
	}
}

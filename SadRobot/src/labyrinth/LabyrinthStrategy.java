package labyrinth;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

import common.Robot;
import common.Strategy;
import common.color.Color;

public class LabyrinthStrategy extends Strategy {	
	public enum BumpResult {TURN, EVADE, HALT};
	public enum AbortCondition {COLOR, LINE, WOOD};
	public static boolean sonarLeft = true;	
	public static Color color;
	
	public LabyrinthStrategy(Robot robot, boolean sonarLeft, Color color) {
		this(robot, sonarLeft, BumpResult.EVADE, AbortCondition.COLOR);
		LabyrinthStrategy.color = color;
	}
	
	public LabyrinthStrategy(Robot robot, boolean sonarLeft,
			BumpResult bump) {
		this(robot, sonarLeft, bump, AbortCondition.LINE);
	}
	
	public LabyrinthStrategy(Robot robot, boolean sonarLeft,
			BumpResult bump, AbortCondition abort) {
		LabyrinthStrategy.sonarLeft = sonarLeft;
				
		//Prepare behaviors
		Behavior driveAlongWall = new Drive(robot, this);
		Behavior bumper = new Bumper(robot, bump, this);
		Behavior lineRec = new RecognizeLine(robot, abort, this);
		
		Behavior[] behaviors = {driveAlongWall, bumper, lineRec};
		
		//Load the behaviors into an arbitrator
		arbitrator = new Arbitrator(behaviors, true);
		
		if (sonarLeft)
			robot.arm.alignLeft();
		else
			robot.arm.alignRight();
	}
}

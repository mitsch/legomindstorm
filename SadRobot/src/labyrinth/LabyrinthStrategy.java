package labyrinth;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

import common.Robot;
import common.Strategy;

public class LabyrinthStrategy extends Strategy {	
	public enum BumpResult {TURN, EVADE, HALT};
	public static boolean sonarLeft = true;
	
	
	
	public LabyrinthStrategy(Robot robot, boolean sonarLeft,
			BumpResult bump) {
		LabyrinthStrategy.sonarLeft = sonarLeft;
				
		//Prepare behaviors
		Behavior driveAlongWall = new Drive(robot, this);
		Behavior bumper = new Bumper(robot, bump, this);
		Behavior lineRec = new RecognizeLine(robot, this);
		
		Behavior[] behaviors = {driveAlongWall, bumper, lineRec};
		
		//Load the behaviors into an arbitrator
		arbitrator = new Arbitrator(behaviors, true);
		
		if (sonarLeft)
			robot.alignLightLeft();
		else
			robot.alignLightRight();
	}
}

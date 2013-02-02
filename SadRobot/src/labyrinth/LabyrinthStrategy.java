package labyrinth;

import common.Robot;
import common.Strategy;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LabyrinthStrategy implements Strategy {
	
	public static boolean solved = false;
	public static boolean sonarLeft = true;
	private Arbitrator labyrinthSolver;
	
	public LabyrinthStrategy(Robot robot, boolean sonarLeft) {
		solved = false;
		LabyrinthStrategy.sonarLeft = sonarLeft;
		
		//Prepare behaviors
		Behavior driveAlongWall = new Drive(robot);
		Behavior bumper = new Bumper(robot);
		Behavior lineRec = new RecognizeLine(robot);
		
		Behavior[] behaviors = {driveAlongWall, bumper, lineRec};
		
		//Load the behaviors into an arbitrator
		labyrinthSolver = new Arbitrator(behaviors);
		
		if (sonarLeft)
			robot.alignLightLeft();
		else
			robot.alignLightRight();
	}

	public void start() {
		labyrinthSolver.start();
	}
	
	public void stop() {
		solved = true;
	}
}
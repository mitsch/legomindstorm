package labyrinth;

import common.Robot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LabyrinthSolver {
	
	public static boolean solved = false;
	public static boolean sonarLeft = true;
	private Arbitrator labyrinthSolver;
	
	public LabyrinthSolver(Robot robot, boolean sonarLeft) {
		solved = false;
		LabyrinthSolver.sonarLeft = sonarLeft;
		
		//Prepare behaviors
		Behavior driveAlongWall = new Drive(robot);
		Behavior bumper = new Bumper(robot);
		//Behavior lineRec = new RecognizeLine(robot);
		
		Behavior[] behaviors = {driveAlongWall, bumper};
		
		//Load the behaviors into an arbitrator
		labyrinthSolver = new Arbitrator(behaviors);
		
		if (sonarLeft)
			robot.alignLightLeft();
		else
			robot.alignLightRight();
	}

	public void go() {
		labyrinthSolver.start();
	}
}
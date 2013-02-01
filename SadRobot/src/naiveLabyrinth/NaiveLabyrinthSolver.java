package naiveLabyrinth;

import common.Robot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class NaiveLabyrinthSolver {
	
	public static boolean solved = false;
	public static boolean sonarLeft = true;
	private Arbitrator labyrinthSolver;
	
	public NaiveLabyrinthSolver(Robot robot, boolean leftWall) {
		solved = false;
		sonarLeft = true;
		
		//Prepare behaviors
		Behavior drive = new Drive(robot);
		Behavior getAwayFromWall = new WallToClose(robot);
		Behavior closeToWall = new WallToFar(robot);
		Behavior leftBumper = new LeftBumper(robot);
		Behavior rightBumper = new RightBumper(robot);
		Behavior lineRec = new RecognizeLine(robot);
		Behavior frontScanner = new ScanFront(robot); 
		
		Behavior[] behaviors = {drive, getAwayFromWall, closeToWall,
				leftBumper,	rightBumper};
		
		//Load the behaviors into an arbitrator
		labyrinthSolver = new Arbitrator(behaviors);
	}

	public void go() {
		labyrinthSolver.start();
	}
}
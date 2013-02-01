package labyrinth;

import common.Robot;
import lejos.geom.Point;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LabyrinthSolver {
	
	public static boolean solved = false;
	private Arbitrator labyrinthSolver;
	private UltraSonicScanner scanner;
	
	public LabyrinthSolver(Robot robot, LabyrinthMap labyrinth, Point target) {
		//Prepare labyrinth map
		
		scanner = new UltraSonicScanner(robot, labyrinth);
		
		//Prepare behaviors
		Behavior scanMap = new MapByScan(robot);
		MapByBump bumpMap = new MapByBump(robot, labyrinth);
		RecognizeLine lineRec = new RecognizeLine(robot);
		FindPath pathFind = new FindPath(robot, labyrinth, target);
		
		Behavior[] behaviors = {scanMap, pathFind, bumpMap};
		
		//Load the behaviors into an arbitrator
		labyrinthSolver = new Arbitrator(behaviors, true);
	}

	public void go() {
		scanner.start();
		labyrinthSolver.start();
	}
}
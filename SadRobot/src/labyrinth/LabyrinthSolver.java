package labyrinth;

import common.Robot;
import lejos.geom.Point;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LabyrinthSolver {
	
	public static boolean solved = false;
	private LabyrinthMap labyrinth;
	private Arbitrator labyrinthSolver;
	private UltraSonicScanner scanner;
	
	public LabyrinthSolver(Robot robot) {
		//Prepare labyrinth map
		labyrinth = new LabyrinthMap(new Point(0,0), new Point(200, 200));
		
		scanner = new UltraSonicScanner(robot, labyrinth);
		
		//Prepare behaviors
		MapByBump bumpMap = new MapByBump(robot, labyrinth);
		RecognizeLine lineRec = new RecognizeLine(robot);
		FindPath pathFind = new FindPath(robot, labyrinth);
		
		Behavior[] behaviors = {pathFind, bumpMap, lineRec};
		
		//Load the behaviors into an arbitrator
		labyrinthSolver = new Arbitrator(behaviors, true);
	}

	public void go() {
		scanner.start();
		labyrinthSolver.start();
	}
}
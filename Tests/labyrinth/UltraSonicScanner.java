package labyrinth;

import lejos.geom.Point;
import common.Robot;

public class UltraSonicScanner extends Thread {
	private Robot robot;
	private LabyrinthMap map;
	
	public UltraSonicScanner(Robot robot, LabyrinthMap map) {
		this.robot = robot;
		this.map = map;
	}
	
	@Override
	public void run() {
		while (!LabyrinthSolver.solved) {
			//distance in cm
			Point obstaclePosition = robot.useSonar();
			
			if (obstaclePosition != null)	
				map.setObstacleAt(obstaclePosition, robot.getPose().getLocation());
			else
				map.setFree(robot.getPose().getLocation(),
						robot.getPose().getLocation().pointAt(50,
								robot.getPose().getHeading()));
		}
	}
}

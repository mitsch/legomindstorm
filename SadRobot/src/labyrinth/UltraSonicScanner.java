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
			try {
				//distance in cm
				Point obstaclePosition = robot.useSonar();
				
				if (obstaclePosition != null) {	
					map.setObstacleAt(obstaclePosition, robot.getPose().getLocation());
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}

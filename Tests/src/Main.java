import lejos.geom.Point;
import common.Robot;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		LabyrinthMap map = new LabyrinthMap(new Point(0,0), new Point(200, 200));
		Thread thread = new UltraSonicScanner(robot, map); 
		
		robot.pilot.rotate(90,true);
		thread.start();
		while (robot.pilot.isMoving());
		
		while (thread.isAlive());
	}
}

import lejos.nxt.Button;
import common.Robot;


public class NaiveLineFollow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		Button.waitForAnyPress();
		
		while (true) {
			if (robot.isLineBeneath()) {
				robot.pilot.rotateLeft();
				while (robot.isLineBeneath());
			} else {
				robot.pilot.steer(-25);
				while (!robot.isLineBeneath());
			}
		}
	}
}

import lejos.nxt.Button;
import common.Robot;


public class SimpleLine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		Button.waitForAnyPress();
		robot.pilot.setTravelSpeed(10);
		
		int leftAngle = 0;
		int rightAngle = 0;
		
		while (Button.readButtons() != Button.ID_ESCAPE) {
			robot.joker.rotateTo(90, true);
			while (!robot.isLineBeneath() && robot.joker.isMoving());
			while (robot.isLineBeneath() && robot.joker.isMoving());
			rightAngle = robot.joker.getTachoCount();
			
			robot.joker.rotateTo(-90, true);
			while (!robot.isLineBeneath() && robot.joker.isMoving());
			while (robot.isLineBeneath() && robot.joker.isMoving());
			leftAngle = robot.joker.getTachoCount();
			
			if (leftAngle < -85 || rightAngle > 85) {
				robot.pilot.backward();
			} else {
				robot.pilot.steer(-(rightAngle + leftAngle)/1.5f);
			}
		}
	}
}

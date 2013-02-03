
import lejos.nxt.Button;
import lejos.util.Delay;
import lineFollowing.LineFollower;
import common.Robot;

public class LineFollowMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		Delay.msDelay(5000);
		Robot sadRobot = new Robot();
		followLine(sadRobot);
	}
	
	public static void followLine(Robot robot) {
		robot.alignLightMiddle();
		LineFollower lineFollower = new LineFollower(robot);
		lineFollower.start();	
	}

}

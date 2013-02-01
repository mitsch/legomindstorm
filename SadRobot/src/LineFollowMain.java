
import lejos.nxt.Button;
import lineFollowing.LineFollower;
import common.Robot;

public class LineFollowMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		Robot sadRobot = new Robot();
		followLine(sadRobot);
	}
	
	public static void followLine(Robot robot) {
		robot.alignLightMiddle();
		LineFollower lineFollower = new LineFollower(robot);
		lineFollower.go();		
	}

}

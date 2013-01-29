
import lineFollowing.LineFollower;
import common.Robot;

public class LineFollowMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot sadRobot = new Robot();
		followLine(sadRobot);
	}
	
	public static void followLine(Robot robot) {
		LineFollower lineFollower = new LineFollower(robot);
		lineFollower.go();		
	}

}

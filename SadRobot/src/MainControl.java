import common.Robot;

public class MainControl {
	public static short cm = 1;
	
	/**
	 * This is the main entry point for our program. We initialize the
	 * MainControl and start it.
	 */
	public static void main(String[] args) {	
		Robot sadRobot = new Robot();
		LabyrinthMain.solveLabyrinth(sadRobot);
		LineFollowMain.followLine(sadRobot);
	}
}
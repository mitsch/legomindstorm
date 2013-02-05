import common.Robot;

import turningTable.TableTurner;
import lejos.nxt.Button;

public class TableTurnMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		Robot sadRobot = new Robot();
		turnTheTable(sadRobot);
	}
	
	public static void turnTheTable(Robot robot) {
		TableTurner turner = new TableTurner(robot);
		turner.start();
	}
}

package labyrinth;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

import common.Robot;
import common.Strategy;
import common.color.Color;

/**
 * Our labyrinth strategy tags along a wall until we find the end of the
 * labyrinth.
 * 
 * @author Thomas
 *
 */
public class WallFollower extends Strategy {	
	public enum BumpResult {TURN, EVADE, HALT, HALT_AGGRESSIVE, NONE};
	public enum AbortCondition {COLOR, LINE, WOOD, TIME};
	public static boolean sonarLeft = true;	
	public static Color color;
	public static long started;
	
	public WallFollower(Robot robot, boolean sonarLeft, Color color) {
		this(robot, sonarLeft, BumpResult.EVADE, AbortCondition.COLOR);
		WallFollower.color = color;
	}
	
	public WallFollower(Robot robot, boolean sonarLeft,
			BumpResult bump) {
		this(robot, sonarLeft, bump, AbortCondition.LINE);
	}
	
	public WallFollower(Robot robot, boolean sonarLeft,
			BumpResult bump, AbortCondition abort) {
		WallFollower.sonarLeft = sonarLeft;
				
		//Prepare behaviors
		Behavior driveAlongWall = new Drive(robot, this);
		Behavior bumper = new Bumper(robot, bump, this);
		Behavior stop = new Stopper(robot, abort, this);
		
		Behavior[] behaviors;
		if (bump == BumpResult.HALT || bump == BumpResult.HALT_AGGRESSIVE) {
			behaviors = new Behavior[2];
			behaviors[0] = driveAlongWall;
			behaviors[1] = bumper;
		} else {
			behaviors = new Behavior[3];
			behaviors[0] = driveAlongWall;
			behaviors[1] = bumper;
			behaviors[2] = stop;
		}
		
		//Load the behaviors into an arbitrator
		arbitrator = new Arbitrator(behaviors, true);
		
		if (sonarLeft)
			robot.arm.alignLeft();
		else
			robot.arm.alignRight();
	}
	
	@Override
	public void start() {
		started = System.currentTimeMillis();
		super.start();
	}
}

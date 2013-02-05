package lineFollowing;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

import common.Robot;
import common.Strategy;

/**
 * Our line follower drives along the line. If he loses the line, he tries
 * to find it again by swinging left and right.
 * 
 * @author Thomas
 *
 */
public class LineFollower extends Strategy {
	public enum AbortCondition {OBSTACLE, WOOD, END_OF_THE_LINE, COLOR};
	public enum Mode {FOLLOW_LINE, AVOID_OBSTACLE};
	public static int lineCurvature = 0;
	public static long started;
	public static Mode mode = Mode.FOLLOW_LINE;
	
	public LineFollower(Robot robot, AbortCondition abort) {
		this(robot, abort, false);
	}
		
	public LineFollower(Robot robot, AbortCondition abort, boolean detectObstacles) {
		
		Behavior followLine = new FollowLine(robot, this);
		Behavior findLine = new FindLine(robot,
				abort == AbortCondition.END_OF_THE_LINE, this);
		
		int size = 2;
		if (abort != AbortCondition.END_OF_THE_LINE)
			size++;
		
		if (detectObstacles)
			size+=2;
		
		Behavior[] behaviors = new Behavior[size];
		behaviors[0] = findLine;
		behaviors[1] = followLine;
		
		int filled = 2;
		
		//add the abort condition
		switch (abort) {
		case OBSTACLE:
			behaviors[2]= new ObstacleDetector(robot, this);
			filled++;
			break;
		case WOOD:
			behaviors[2] = new WoodDetector(robot, this);
			filled++;
			break;
		case COLOR:
			behaviors[2] = new ColorDetector(robot, this);
			filled++;
			break;
		case END_OF_THE_LINE:
			break;
		}
		
		if (detectObstacles) {
			behaviors[filled] = new ObstacleAvoider(robot, this);
			behaviors[filled+1] = new LineFinder(robot, this);
		}
		
		arbitrator = new Arbitrator(behaviors, true);
		
		robot.arm.alignCenter();
	}
}

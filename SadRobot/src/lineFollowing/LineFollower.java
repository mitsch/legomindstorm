package lineFollowing;

import common.Robot;
import common.Strategy;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class LineFollower extends Strategy {
	public enum AbortCondition {OBSTACLE, WOOD, END_OF_THE_LINE};
	public static int lineCurvature = 0;
	
	public LineFollower(Robot robot, AbortCondition abort) {
		Behavior followLine = new FollowLine(robot, this);
		Behavior findLine = new FindLine(robot,
				abort == AbortCondition.END_OF_THE_LINE, this);
		Behavior detectObstacle = new ObstacleDetector(robot, this);
		Behavior detectWood = new WoodDetector(robot, this);
		
		Behavior[] behaviors;
		if (abort == AbortCondition.END_OF_THE_LINE)
			behaviors = new Behavior[2];
		else
			behaviors = new Behavior[3];
		behaviors[0] = findLine;
		behaviors[1] = followLine;
		
		//add the abort condition
		switch (abort) {
		case OBSTACLE:
			behaviors[2] = detectObstacle;
			break;
		case WOOD:
			behaviors[2] = detectWood;
			break;
		case END_OF_THE_LINE:
			break;
		}
		
		arbitrator = new Arbitrator(behaviors, true);
	}
}

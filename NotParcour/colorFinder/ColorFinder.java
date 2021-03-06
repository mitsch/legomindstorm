package colorFinder;

import lineFollowing.LineFollower;
import common.Strategy;
import common.StrategyBehavior;
import common.Robot;

public class ColorFinder extends StrategyBehavior  {
	private Robot robot;
	private boolean lastHeadingLeft;
	

	public ColorFinder(Robot robot, Strategy parent){
		super(parent);
		this.robot = robot;
		this.lastHeadingLeft = true;
	}

	
	public boolean wantsToWork() {
		return robot.isLineBeneath();
	}
	
	 public void work(){
		 robot.pilot.travel(10);
	     //find line by rotating a bit to the right and left	
		 robot.pilot.setRotateSpeed(60);
		 int currentHeading = 0;
		 int turn = 20;
		 short sign;
		 if(lastHeadingLeft)
			 sign = 1;
		 else
			 sign = -1;

		while (!suppressed && !robot.isLineBeneath()) {
			//if we would rotate too far
			if (turn >= 130) {
				break;
			} else {
				for (int i=0; i<2 && !suppressed && !robot.isLineBeneath(); i++) {	
					robot.pilot.rotate(-currentHeading + sign*turn, true);
					while (!suppressed && !robot.isLineBeneath() && robot.pilot.isMoving());	
					robot.pilot.stop();
					currentHeading = sign*turn;
				
					if (!robot.isLineBeneath())
						sign *= -1;
						turn += 20;
				}
			}	
		}
		
		lastHeadingLeft = sign == 1;
		LineFollower.lineCurvature = sign*20;
		if (Math.abs(LineFollower.lineCurvature) > 100) {
			if (Math.signum(LineFollower.lineCurvature) < 0)
				LineFollower.lineCurvature = -100;
			else
				LineFollower.lineCurvature = 100;
			
		}
		robot.pilot.setRotateSpeed(90);
	}
}

package foamBog;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import common.Robot;
import common.Strategy;

/**
 * To solve the foambgo, we simply drive over it.
 * 
 * @author Thomas
 *
 */
public class FoamBogStrategy extends Strategy {
	
	public FoamBogStrategy(Robot robot) {
		Behavior drive = new Drive(robot, this);
		Behavior bump = new Bumper(robot, this);
		
		Behavior[] behaviors = {drive, bump};
		
		arbitrator = new Arbitrator(behaviors, true);	
	}
}
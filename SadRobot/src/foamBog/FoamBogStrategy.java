package foamBog;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import common.Robot;
import common.Strategy;

public class FoamBogStrategy implements Strategy {
	public static boolean passed = false;
	private Arbitrator foamer;
	
	public FoamBogStrategy(Robot robot) {
		passed = false;
		
		Behavior drive = new Drive(robot);
		Behavior bump = new Bumper(robot);
		
		Behavior[] behaviors = {drive, bump};
		
		foamer = new Arbitrator(behaviors, true);	
	}

	@Override
	public void start() {
		foamer.start();
	}

	@Override
	public void stop() {
		passed = true;
	}
}

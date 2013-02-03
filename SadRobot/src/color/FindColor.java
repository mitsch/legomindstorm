package colorFinder;

import common.Robot;
import common.Color;

public class FindColor implements StrategyBehavior {
		private boolean suppressed;
		public Lightsensor light;
		private Robot robot;
	
		public void findColor(Robot robot){
			this.robot = robot;
			this.suppressed = false;
		}
		public boolean wantsToWork() {
			return robot.pilot.isLineBeneath();
		}
		public void work() {
			robot.pilot.forward();
			while (!suppressed){
				robot.light.readValue();
			}
			robot.pilot.stop();
		}
}

package testing;

import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class BumperBackup implements Behavior {
	private TouchSensor touchLeft, touchRight;
	private DifferentialPilot pilot;
	
	public BumperBackup(TouchSensor leftTouchSensor,
			TouchSensor rightTouchSensor, DifferentialPilot pilot) {
		this.touchLeft = leftTouchSensor;
		this.touchRight = rightTouchSensor;
		this.pilot = pilot;
	}

	@Override
	public boolean takeControl() {
		return touchLeft.isPressed() || touchRight.isPressed();
	}

	@Override
	public void action() {
		boolean leftIsPressed = touchLeft.isPressed();
		pilot.travel(-10);	
		if (leftIsPressed) {
			pilot.rotate(-30, true);
		} else {
			pilot.rotate(30, true);	
		}	
		pilot.travel(10);
	}

	@Override
	public void suppress() {
	}
}
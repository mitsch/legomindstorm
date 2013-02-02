package bridgePasser;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class DriveBridge implements Behavior {
	private boolean suppressed;
	private Robot robot;
	private boolean leftBridge;
	
	public DriveBridge(Robot robot, boolean leftBridge) {
		this.suppressed = false;
		this.robot = robot;
		this.leftBridge = leftBridge;
	}

	@Override
	public boolean takeControl() {
		return !BridgeStrategy.bridgePassed && !robot.isFallBeneath();
	}

	@Override
	public void action() {
		suppressed = false;
		
		if (leftBridge)
			robot.pilot.steer(-20);
		else
			robot.pilot.steer(20);
		while (!suppressed);
		robot.pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

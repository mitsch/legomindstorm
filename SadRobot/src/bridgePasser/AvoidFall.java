package bridgePasser;

import common.Robot;

import lejos.robotics.subsumption.Behavior;

public class AvoidFall implements Behavior {
	private Robot robot;
	private boolean leftBridge;
	
	public AvoidFall(Robot robot, boolean leftBridge) {
		this.robot = robot;
		this.leftBridge = leftBridge;	
	}

	@Override
	public boolean takeControl() {
		return !BridgeStrategy.bridgePassed && robot.isFallBeneath();
	}

	@Override
	public void action() {
		if (leftBridge)
			robot.pilot.steer(-100, 20);
		else
			robot.pilot.steer(100, -20);
		while (robot.pilot.isMoving());
		robot.pilot.stop();
	}

	@Override
	public void suppress() {
	}

}

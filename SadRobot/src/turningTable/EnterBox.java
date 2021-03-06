package turningTable;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

import turningTable.TableTurner;

public class EnterBox extends StrategyBehavior {
	private Robot robot;
	private boolean passedBox;
	
	public EnterBox(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
		this.passedBox = false;
	}

	@Override
	public boolean wantsToWork() {
		return !passedBox && robot.sonar.getDistance() < 40 && !robot.isLineBeneath();
	}

	@Override
	public void work() {
		robot.arm.alignLeft();
		int leftPreDistance = robot.sonar.getDistance();
		robot.arm.alignRight();
		int rightPreDistance = robot.sonar.getDistance();
	
		if (leftPreDistance > 30 || rightPreDistance > 30)
			return;
	
		if (leftPreDistance < rightPreDistance) {
			robot.pilot.steer(-200.0, java.lang.Math.cos((double)(leftPreDistance + rightPreDistance - 2 * leftPreDistance)/48.0));
		} else if (rightPreDistance < leftPreDistance) {
			robot.pilot.steer(200.0, java.lang.Math.cos(0.5 * (double)(leftPreDistance + rightPreDistance - 2 * rightPreDistance)/48.0));
		}

		robot.pilot.forward();
		while (!suppressed && !robot.leftTouch.isPressed() && !robot.rightTouch.isPressed());
		robot.pilot.stop();
		robot.pilot.travel(-12);

		robot.arm.alignLeft();
		robot.pilot.steer(200);
		int state = 0;
		while (state != 2 && !suppressed) {
			int distance = robot.sonar.getDistance();
			if (state == 0 && distance > 100) state = 1;
			else if (state == 1 && distance < 20) {
				robot.pilot.stop();
				robot.arm.alignCenter();
				if (robot.sonar.getDistance() > 30)
					state = 2;
				else {
					robot.arm.alignLeft();
					robot.pilot.steer(200);
				}
			}
		}
		robot.pilot.stop();
		robot.arm.rotateTo(robot.arm.getRightMaxAngle() - 45);

		TableTurner.client.turnClockwise(-180);
		state = 0;
		while (state != 4 && !suppressed) {
			int distance = robot.sonar.getDistance();
			if (state == 0 && distance < 20) state = 1;
			else if (state == 1 && distance > 100) state = 2;
			else if (state == 2 && distance < 20) state = 3;
			else if (state == 3 && distance > 100) state = 4;
			else if (state == 0 && distance > 100) state = 2;
		}
			
		robot.pilot.forward();
		robot.arm.alignCenter();
		passedBox = true;
		TableTurner.client.disconnectFromTurntable();
	}
}

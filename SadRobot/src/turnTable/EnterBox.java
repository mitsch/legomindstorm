package turningTable;

import common.Robot;
import common.Strategy;
import common.StrategyBehavior;

import turningTable.TableTurner;

import lejos.robotics.subsumption.Behavior;
import lejos.robotics.subsumption.Arbitrator;

public class EnterBox extends StrategyBehavior {
	private Robot robot;
	
	public EnterBox(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot; 
	}

	@Override
	public boolean wantsToWork() {
		return !TableTurner.passedBox && TableTurner.client.connectionToTurnTableSuccessful() && robot.sonar.getDistance() > 30;
	}

	@Override
	public void work() {
		robot.pilot.forward();
		while (robot.sonar.getDistance() > 6);
		robot.joker.rotateTo(robot.getLeftJoker());
		robot.pilot.steer(200, 180);
		robot.joker.rotateTo(robot.getMiddleJoker());
		parent.stop();
	}
}

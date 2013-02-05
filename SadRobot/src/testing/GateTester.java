package testing;

import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.util.Delay;
import common.GateControl;
import common.GateCommon;
import common.Robot;

public class GateTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Robot robot = new Robot();
		Button.waitForAnyPress();
		openGate(robot);
	}
	
	public static void openGate(Robot robot) {
		robot.arm.alignMiddle();
		robot.pilot.forward();
		while (robot.sonar.getDistance() > 20);
		robot.pilot.stop();
		
		Sound.beep();
		GateControl gateControl = new GateControl();
		while (!gateControl.connectionToGateSuccessful(GateCommon.GATE_1));
		Sound.beep();
		gateControl.openGate();
		gateControl.disconnectFromGate();
		Sound.beep();
		
		while(robot.sonar.getDistance() < 30);
		
		robot.pilot.forward();
		Delay.msDelay(2000);
		robot.pilot.stop();
	}
}

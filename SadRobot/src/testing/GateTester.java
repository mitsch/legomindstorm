package testing;

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
		openGate(robot);
	}
	
	public static void openGate(Robot robot) {
		robot.alignLightMiddle();
		robot.pilot.forward();
		while (robot.sonar.getDistance() > 20);
		robot.pilot.stop();
		
		GateControl gateControl = new GateControl();
		while (!gateControl.connectionToGateSuccessful(GateCommon.GATE_3));
		gateControl.openGate();
		gateControl.disconnectFromGate();
		
		robot.pilot.forward();
		Delay.msDelay(2000);
		robot.pilot.stop();
	}
}

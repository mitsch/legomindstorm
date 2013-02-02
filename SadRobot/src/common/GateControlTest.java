package common;
import lejos.nxt.Button;


public class GateControlTest {
	public static void main(String[] args) {
		GateControl gateControl = new GateControl();
		
		System.out.println("Press button to open");
		Button.waitForAnyPress();
		
		while (!gateControl.connectionToGateSuccessful(GateCommon.GATE_3));
		gateControl.openGate();
		gateControl.disconnectFromGate();
		
		System.out.println("Gate opened");
		Button.waitForAnyPress();
	}
}
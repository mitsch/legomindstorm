package bluetoothGate;

import common.GateCommon;
import common.GateControl;
import common.Strategy;
import common.StrategyBehavior;

public class OpenGateBehavior extends StrategyBehavior {
	
	private GateControl gateControl = new GateControl();
	
	public OpenGateBehavior(Strategy parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean wantsToWork() {
		return !this.gateControl.connectionToGateSuccessful(GateCommon.GATE_1);
	}
	
	@Override
	protected void work() {
		// TODO Auto-generated method stub
		this.gateControl.openGate();
	}

}

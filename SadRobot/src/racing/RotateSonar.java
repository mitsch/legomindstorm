package racing;

public class RotateSonar implements java.lang.Runnable
{
	private boolean holdingOn;
	private lejos.nxt.NXTRegulatedMotor motor;
	private int leftMaximumAngle;
	private int rightMaximumAngle;

	public RotateSonar(lejos.nxt.NXTRegulatedMotor motor, int leftMaximumAngle, int rightMaximumAngle) {
		holdingOn = false;
		this.motor = motor;
		this.leftMaximumAngle = leftMaximumAngle;
		this.rightMaximumAngle = rightMaximumAngle;
	}

	public void holdOn() {
		holdingOn = true;
		motor.stop();
	}

	public void run() {
		holdingOn = false;
		boolean leftTo = true;
		int nextAngle = leftMaximumAngle;

		while (!holdingOn) {
			if (leftTo)
				motor.rotateTo(leftMaximumAngle);
			else
				motor.rotateTo(rightMaximumAngle);

			leftTo = !leftTo;
		}
	}
};


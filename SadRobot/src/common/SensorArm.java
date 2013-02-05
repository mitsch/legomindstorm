package common;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;

public class SensorArm extends NXTRegulatedMotor {

	private int leftMaxAngle = -33;
	private int rightMaxAngle = +33;
	private int middleAngle = 0;

	public SensorArm(TachoMotorPort port) {
		super(port);
	}
	
	public void calibrate(int leftMaxAngle, int rightMaxAngle) {
		this.leftMaxAngle = leftMaxAngle;
		this.rightMaxAngle = rightMaxAngle;

		this.calibrate();
	}
	
	public void calibrate() {
		do {
			System.out.println("calibrate joker");
			this.setStallThreshold(8, 3);

			// Find left boundary
			this.backward();
			while (!this.isStalled())
				;
			this.stop();
			this.rotate(16);
			this.waitComplete();
			leftMaxAngle = this.getPosition();
			System.out.println("left max "
					+ Integer.toString(this.leftMaxAngle));

			// Find right boundary
			this.forward();
			while (!this.isStalled())
				;
			this.stop(false);
			this.rotate(-16);
			this.waitComplete();
			this.rightMaxAngle = this.getPosition();
			System.out.println("right max "
					+ Integer.toString(this.rightMaxAngle));
		} while (java.lang.Math
				.abs(180 - (this.rightMaxAngle - this.leftMaxAngle)) > 4);

		this.setStallThreshold(50, 20);

		this.middleAngle = ((this.leftMaxAngle + this.rightMaxAngle) / 2);

		this.rotateTo(middleAngle);
	}

	/**
	 * @return the leftMaxAngle
	 */
	public int getLeftMaxAngle() {
		return leftMaxAngle;
	}

	/**
	 * @param leftMaxAngle
	 *            the leftMaxAngle to set
	 */
	public void setLeftMaxAngle(int leftMaxAngle) {
		this.leftMaxAngle = leftMaxAngle;
	}

	/**
	 * @return the rightMaxAngle
	 */
	public int getRightMaxAngle() {
		return rightMaxAngle;
	}

	/**
	 * @param rightMaxAngle
	 *            the rightMaxAngle to set
	 */
	public void setRightMaxAngle(int rightMaxAngle) {
		this.rightMaxAngle = rightMaxAngle;
	}

	/**
	 * @return the middleAngle
	 */
	public int getMiddleAngle() {
		return middleAngle;
	}

	/**
	 * @param middleAngle
	 *            the middleAngle to set
	 */
	public void setMiddleAngle(int middleAngle) {
		this.middleAngle = middleAngle;
	}

	public void alignLeft() {
		this.rotateTo(this.leftMaxAngle);
	}

	public void alignMiddle() {
		this.rotateTo(this.middleAngle);
	}

	public void alignRight() {
		this.rotateTo(this.rightMaxAngle);
	}


}

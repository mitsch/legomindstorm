package common;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;

/**
 * Our sensor arm carries both the light sensor and ultrasonic sensor.
 * In theory, we can bend it up to 90°.
 * 
 * @author Philipp
 *
 */
public class SensorArm extends NXTRegulatedMotor {

	private int leftMaxAngle = -33;
	private int rightMaxAngle = +33;
	private int centerAngle = 0;

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
			System.out.println("Calibrate SensorArm");
			this.setStallThreshold(8, 3);

			// Find left boundary
			this.backward();
			while (!this.isStalled())
				;
			this.stop();
			this.rotate(16);
			this.waitComplete();
			leftMaxAngle = this.getPosition();
			System.out.println("SensorArm.leftMaxAngle: "+ Integer.toString(this.leftMaxAngle));

			// Find right boundary
			this.forward();
			while (!this.isStalled())
				;
			this.stop(false);
			this.rotate(-16);
			this.waitComplete();
			this.rightMaxAngle = this.getPosition();
			
			System.out.println("SensorArm.rightMaxAngle: "+ Integer.toString(this.rightMaxAngle));
			
		
		} while (java.lang.Math
				.abs(180 - (this.rightMaxAngle - this.leftMaxAngle)) > 4);

		this.setStallThreshold(50, 20);

		this.centerAngle = ((this.leftMaxAngle + this.rightMaxAngle) / 2);

		this.rotateTo(centerAngle);
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
	public int getCenterAngle() {
		return centerAngle;
	}

	/**
	 * @param middleAngle
	 *            the middleAngle to set
	 */
	public void setCenterAngle(int centerAngle) {
		this.centerAngle = centerAngle;
	}

	public void alignLeft() {
		this.rotateTo(this.leftMaxAngle);
	}

	public void alignCenter() {
		this.rotateTo(this.centerAngle);
	}

	public void alignRight() {
		this.rotateTo(this.rightMaxAngle);
	}
}

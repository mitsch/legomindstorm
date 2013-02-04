package common;


import common.color.Color;
import common.color.ColorOracle.Strength;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
	public TouchSensor leftTouch, rightTouch;
	public UltrasonicSensor sonar;
	
	public ColorSensor color;
	
	public NXTRegulatedMotor leftMotor, rightMotor;
	public NXTRegulatedMotor joker;
	
	public DifferentialPilot pilot;
	
	private int leftMaxJokerAngle;
	private int rightMaxJokerAngle;
	
	public Robot() {
		leftMotor = Motor.C;
		rightMotor = Motor.A;
		joker = Motor.B;
		
		leftTouch = new TouchSensor(SensorPort.S4);
		rightTouch = new TouchSensor(SensorPort.S1);
		sonar = new UltrasonicSensor(SensorPort.S3);
		
		// This is our new ColorSensor! Its used insted of the LightSensor.
		color = new ColorSensor(SensorPort.S2);
		color.setStrength(Strength.WEAK);
		
		////  pilot = new DifferentialPilot(3.3f, 21.0f, leftMotor, rightMotor);
		pilot = new DifferentialPilot(3.65f , 26.f, leftMotor, rightMotor);
		
		leftMaxJokerAngle = -90;
		rightMaxJokerAngle = 90;
		
		calibrateJoker();
	}	
	
	public boolean isLineBeneath() {
		return color.getColor(Color.BLACK, Color.SILVER) == Color.SILVER;
	}

	public void alignLightLeft() {
		joker.rotateTo(leftMaxJokerAngle);
	}
	
	public void alignLightMiddle() {
		joker.rotateTo((leftMaxJokerAngle + rightMaxJokerAngle) / 2);
	}
	
	public void alignLightRight() {
		joker.rotateTo(rightMaxJokerAngle);
	}
	
	public int getLeftJoker() {
		return leftMaxJokerAngle;
	}
	
	public int getRightJoker() {
		return rightMaxJokerAngle;
	}

	public int getMiddleJoker() {
		return (leftMaxJokerAngle + rightMaxJokerAngle) / 2;
	}

	public void calibrateJoker() {
		do {
			System.out.println("calibrate joker");
			joker.setStallThreshold(8, 3);
			
			//Find left boundary
			joker.backward();
			while (!joker.isStalled());
			joker.stop();
			joker.rotate(16);
			joker.waitComplete();
			leftMaxJokerAngle = joker.getPosition();
			System.out.println("left max " + Integer.toString(leftMaxJokerAngle));
			
			//Find right boundary
			joker.forward();
			while (!joker.isStalled());
			joker.stop(false);
			joker.rotate(-16);
			joker.waitComplete();
			rightMaxJokerAngle = joker.getPosition();
			System.out.println("right max " + Integer.toString(rightMaxJokerAngle));
		} while (java.lang.Math.abs(180 - (rightMaxJokerAngle - leftMaxJokerAngle)) > 4);

		joker.setStallThreshold(50, 20);

		joker.rotateTo((leftMaxJokerAngle + rightMaxJokerAngle) / 2);
	}
}

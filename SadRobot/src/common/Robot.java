package common;


import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.geom.Point;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;

public class Robot {
	public TouchSensor leftTouch, rightTouch;
	public UltrasonicSensor sonar;
	public LightSensor light;
	
	public NXTRegulatedMotor leftMotor, rightMotor;
	public NXTRegulatedMotor joker;
	
	public Navigator navigator;
	public DifferentialPilot pilot;
	
	private Point ultraSonicPosition;

	private int leftMaxJokerAngle;
	private int rightMaxJokerAngle;
	
	public Robot() {
		leftMotor = Motor.C;
		rightMotor = Motor.A;
		joker = Motor.B;
		
		leftTouch = new TouchSensor(SensorPort.S4);
		rightTouch = new TouchSensor(SensorPort.S1);
		sonar = new UltrasonicSensor(SensorPort.S3);
		//sonar.setMode(UltrasonicSensor.MODE_PING);
		light = new LightSensor(SensorPort.S2);
		
		pilot = new DifferentialPilot(3.3f, 21.0f, leftMotor,
				rightMotor);
		navigator = new Navigator(pilot);
		
		ultraSonicPosition = new Point(10, 10);

		leftMaxJokerAngle = -90;
		rightMaxJokerAngle = 90;
	}
	
	public Pose getPose() {
		return navigator.getPoseProvider().getPose();
	}
	
	public Point getUltraSonicPosition() {
		return this.getPose().getLocation().add(ultraSonicPosition);
	}
	
	public int getLineValue() {
		return 46;
	}
	
	public int getWoodValue() {
		return 37;
	}
	
	public int getFallValue() {
		return 28;
	}
	
	public int getBlackValue() {
		return 26;
	}
	
	public boolean isLineBeneath() {
		return light.readValue() > (getLineValue() + getBlackValue())/2;
	}
	
	public boolean isFallBeneath() {
		return light.readValue() < (getWoodValue() + getFallValue())/2;
	}
	
	public Point useSonar() {	
		int distance = sonar.getDistance();
		if (distance < 50) {
			Pose robotPose = navigator.getPoseProvider().getPose();
			return ultraSonicPosition.pointAt(distance,
					robotPose.getHeading());
		} else
			return null;
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
		System.out.println("calibrate joker");
		joker.setStallThreshold(8, 3);
		joker.backward();
		while (!joker.isStalled());
		joker.stop();
		joker.rotate(16);
		joker.waitComplete();
		leftMaxJokerAngle = joker.getPosition();
		System.out.println("left max " + Integer.toString(leftMaxJokerAngle));

		joker.forward();
		while (!joker.isStalled());
		joker.stop(false);
		joker.rotate(-16);
		joker.waitComplete();
		rightMaxJokerAngle = joker.getPosition();
		System.out.println("right max " + Integer.toString(rightMaxJokerAngle));

		joker.setStallThreshold(50, 20);

		joker.rotateTo((leftMaxJokerAngle + rightMaxJokerAngle) / 2);
	}
}

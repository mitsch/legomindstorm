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
	}
	
	public Pose getPose() {
		return navigator.getPoseProvider().getPose();
	}
	
	public Point getUltraSonicPosition() {
		return this.getPose().getLocation().add(ultraSonicPosition);
	}
	
	public boolean isLineBeneath() {
		return light.readNormalizedValue() > 350;
	}
	
	public boolean isFallBeneath() {
		return light.readValue() < 34;
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
		joker.rotateTo(-90);
	}
	
	public void alignLightMiddle() {
		joker.rotateTo(0);
	}
	
	public void alignLightRight() {
		joker.rotateTo(90);
	}
	
	public int getLeftJoker() {
		return -90;
	}
	
	public int getRightJoker() {
		return 90;
	}
}

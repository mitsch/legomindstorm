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
		rightMotor = Motor.C;
		joker = Motor.A;
		
		leftTouch = new TouchSensor(SensorPort.S1);
		rightTouch = new TouchSensor(SensorPort.S4);
		sonar = new UltrasonicSensor(SensorPort.S3);
		light = new LightSensor(SensorPort.S2);
		
		pilot = new DifferentialPilot(5.6f, 11.1f, leftMotor,
				rightMotor);
		navigator = new Navigator(pilot);
		
		ultraSonicPosition = new Point(0, 0);
	}
	
	public Pose getPose() {
		return navigator.getPoseProvider().getPose();
	}
	
	public Point getUltraSonicPosition() {
		return this.getPose().getLocation().add(ultraSonicPosition);
	}
	
	public boolean isGroundSilver() {
		return light.readNormalizedValue() > 50;
	}
}

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
		leftMotor = Motor.A;
		rightMotor = Motor.B;
		joker = Motor.C;
		joker.setSpeed(30);
		
		leftTouch = new TouchSensor(SensorPort.S1);
		rightTouch = new TouchSensor(SensorPort.S4);
		sonar = new UltrasonicSensor(SensorPort.S3);
		sonar.setMode(UltrasonicSensor.MODE_PING);
		light = new LightSensor(SensorPort.S2);
		
		pilot = new DifferentialPilot(3.3f, 22.0f, leftMotor,
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
		return light.readValue() > 50;
	}
	
	public Point useSonar() {	
		int distance = sonar.getDistance();
		if (distance != 255) {
			Pose robotPose = navigator.getPoseProvider().getPose();
			return ultraSonicPosition.pointAt(distance,
					robotPose.getHeading());
		} else
			return null;
	}
	
	public void alignLight() {
		joker.rotateTo(0);
	}
	
	/**
	 * Tries to get a good reading of the environment. Afterwards, the light
	 * sensor is calibrated according to the darkest/brightest reading.
	 * The sensor will be in front of the robot.
	 */
	public void calibrateLight() {
		alignLight();
		int max = 0;
		int min = 1024;
		joker.rotateTo(140, true);
		while (joker.isMoving()) {
			int measurement = light.readNormalizedValue();
			if (measurement > max)
				max = measurement;
			if (measurement < min)
				min = measurement;
		}
		light.setHigh(max);
		light.setLow(min);
		joker.rotateTo(70);
	}
}

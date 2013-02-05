package common;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * We use this class as a central reference to define sensors, actors and
 * commonly used methods concerning our robot. 
 * 
 * @author Thomas
 *
 */
public class Robot {
	public TouchSensor leftTouch, rightTouch;
	public UltrasonicSensor sonar;
	public LightSensor light;
	
	//public ColorSensor color;
	
	public NXTRegulatedMotor leftMotor, rightMotor;
	public SensorArm arm;
	
	public DifferentialPilot pilot;
	
	public Robot() {
		leftMotor = Motor.C;
		rightMotor = Motor.A;
		arm = new SensorArm(MotorPort.B);
		
		leftTouch = new TouchSensor(SensorPort.S4);
		rightTouch = new TouchSensor(SensorPort.S1);
		sonar = new UltrasonicSensor(SensorPort.S3);
		sonar.continuous();
		light = new LightSensor(SensorPort.S2);
		
		// This is our new ColorSensor! It is used instead of the LightSensor.
		//color = new ColorSensor(SensorPort.S2);
		
		pilot = new DifferentialPilot(3.3f , 21.0f, leftMotor, rightMotor);
		
		arm.calibrate(-90, 90);
	}	
	
	public boolean isLineBeneath() {
		return light.readValue() > 40;
	}
	
	public boolean isWoodBeneath() {
		return light.readValue() > 34;
	}
}

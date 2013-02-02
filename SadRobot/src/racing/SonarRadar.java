package racing;

import common.Robot;
import java.util.ArrayList;
import lejos.geom.Point;

public class SonarRadar implements java.lang.Runnable {

	private boolean hold;
	private Robot robot;
	private ArrayList<Point> detections;
	private int sleepingTime;

	public Sonar(Robot robot, int sleepingTime) {
		this.robot = robot;
		this.sleepingTime = sleepingTime;
		this.detections = new ArrayList<Point>();
	}

	public 
	
}


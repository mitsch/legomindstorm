package racing;

import common.Robot;
import racing.Racer;

import lejos.robotics.subsumption.Behavior;

public class FindWall implements Behavior {

	private class DetectionRange {
		public int leftAngle;
		public int rightAngle;
		public int averageDistance;

		public DetectionRange(int leftAngle, int rightAngle, int averageDistance) {
			this.leftAngle = leftAngle;
			this.rightAngle = rightAngle;
			this.averageDistance = averageDistance;
		}
	};
	
	private boolean suppressed;
	private Robot robot;

	public FindWall(Robot robot) {
		this.suppressed = false;
		this.robot = robot;
	}

	@Override
	public boolean takeControl() {
		return robot.sonar.getDistance() == 255;
	}

	@Override
	public void action() {
		suppressed = false;

		robot.pilot.stop();
	}

	private java.util.ArrayList<DetectionRange> detectEnvironment() {
		boolean foundObject = false;
		int firstAngle = 0;
		int lastAngle = 0;
		float distanceSum = 0;
		float samplesCount = 0;
		java.util.ArrayList<DetectionRange> rangesA = new java.util.ArrayList<DetectionRange>();
		java.util.ArrayList<DetectionRange> rangesB = new java.util.ArrayList<DetectionRange>();

		robot.joker.rotateTo(robot.getLeftJoker());
		robot.joker.setSpeed(45);
		robot.joker.rotate(180, true);
		while (robot.joker.isMoving()) {
			int distance = robot.sonar.getDistance();
			int angle = robot.joker.getPosition();
			if (distance == 255) {
				if (foundObject) {
					rangesA.add(new DetectionRange(firstAngle, lastAngle, distanceSum / samplesCount));
					foundObject = false;
				}		
			}
			else {
				if (foundObject) {
					lastAngle = angle;
					distanceSum += (float) distance;
					samplesCount += 1.0;
				} else {
					firstAngle = lastAngle = angle;
					distanceSum = (float) distance;
					samplesCount = 1.0;
					foundObject = true;
				}
			}
		}
		if (foundObject) {
			rangesA.add(new DetectionRange(firstAngle, lastAngle, distanceSum / samplesCount));
		}

		robot.joker.rotate(-180, true);
		while (robot.joker.isMoving()) {
			int distance = robot.sonar.getDistance();
			int angle = robot.joker.getPosition();
			if (distance == 255) {
				if (foundObject) {
					rangesB.add(new DetectionRange(lastAngle, firstAngle, distanceSum / samplesCount));
					foundObject = false;
				}
			}
			else {
				if (foundObject) {
					lastAngle = angle;
					distanceSum += (float) distance;
					samplesCount += 1.0;
				} else {
					firstAngle = lastAngle = angle;
					distanceSum = (float) distance;
					samplesCount = 1.0;
					foundObject = true;
				}
			}
		}
		if (foundObject) {
			rangesB.add(new DetectionRange(lastAngle, firstAngle, distanceSum / samplesCount));
		}

		if (rangesA.size() == 0)	return rangesB;
		if (rangesB.size() == 0)	return rangesA;

		// iterate through lists to find hopefully matching ranges
		ListIterator<DetectionRange> iterateA = rangesA.listIterator();
		listIterator<DetectionRange> iterateB = rangesB.listIterator(rangesB.size());

		java.util.ListArray<DetectionRange> result = new java.util.ListArray<DetectionRange>();
		DetectionRange rangeA = iterateA.next();
		DetectionRange rangeB = iterateB.next();
		while (iterateA.hasNext() || iterateB.hasPrevious()) {
			if (rangeA.firstAngle <= rangeB.firstAngle) {
				if (rangeA.lastAngle >= rangeB.lastAngle) {
					if (rangeA.averageDistance > rangeB.averageDistance) {
						result.add(rangeA);
					}
				}
			}
			else {

			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}

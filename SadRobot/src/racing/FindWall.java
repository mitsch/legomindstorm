package racing;

import common.Robot;
import racing.Racer;

import common.StrategyBehavior;
import common.Strategy;

public class FindWall extends StrategyBehavior {

	private Robot robot;

	public FindWall(Robot robot, Strategy parent) {
		super(parent);
		this.robot = robot;
	}

	public boolean wantsToWork() {
		return robot.sonar.getDistance() == 255;
	}

	public void work() {
		robot.pilot.setTravelSpeed(0.5 * robot.pilot.getTravelSpeed());

		int jokerPosition = robot.arm.getPosition();
		if (jokerPosition > robot.getMiddleJoker()) {
			robot.arm.rotateTo(robot.getLeftJoker() + Racer.angleOffsetJoker);
		} else {
			robot.arm.rotateTo(robot.getRightJoker() - Racer.angleOffsetJoker);
		}

		int distance = robot.sonar.getDistance();
		if (distance == 255) {
			robot.pilot.rotate(360);
//			while (!robot.leftTouch.isPressed());
		} else {
			Racer.regularDistance = distance;
			robot.pilot.setTravelSpeed(robot.pilot.getMaxTravelSpeed());
		}
	}

//			private class DetectionRange {
//				public int leftAngle;
//				public int rightAngle;
//				public float averageDistance;
//			
//				public DetectionRange(int leftAngle, int rightAngle, float averageDistance) {
//					this.leftAngle = leftAngle;
//					this.rightAngle = rightAngle;
//					this.averageDistance = averageDistance;
//				}
//			};
//			
//			private class DetectionEdge {
//				public boolean leftEdge;
//				public DetectionRange range;
//			
//				public DetectionEdge(boolean leftEdge, DetectionRange range) {
//					this.leftEdge = leftEdge;
//					this.range = range;
//				}
//			};
//			
//			private boolean suppressed;
//			private Robot robot;
//			
//			public FindWall(Robot robot) {
//				this.suppressed = false;
//				this.robot = robot;
//			}
//			
//			@Override
//			public boolean takeControl() {
//				return robot.sonar.getDistance() == 255;
//			}
//			
//			@Override
//			public void action() {
//				suppressed = false;
//			
//				robot.pilot.stop();
//			}
//			
//			private java.util.ArrayList<DetectionRange> detectEnvironment() {
//				boolean foundObject = false;
//				int firstAngle = 0;
//				int lastAngle = 0;
//				float distanceSum = 0;
//				float samplesCount = 0;
//				java.util.ArrayList<DetectionRange> rangesA = new java.util.ArrayList<DetectionRange>();
//				java.util.ArrayList<DetectionRange> rangesB = new java.util.ArrayList<DetectionRange>();
//			
//				robot.joker.rotateTo(robot.getLeftJoker());
//				robot.joker.setSpeed(45);
//				robot.joker.rotate(180, true);
//				while (robot.joker.isMoving()) {
//					int distance = robot.sonar.getDistance();
//					int angle = robot.joker.getPosition();
//					if (distance == 255) {
//						if (foundObject) {
//							rangesA.add(new DetectionRange(firstAngle, lastAngle, distanceSum / samplesCount));
//							foundObject = false;
//						}		
//					}
//					else {
//						if (foundObject) {
//							lastAngle = angle;
//							distanceSum += (float) distance;
//							samplesCount += 1.0;
//						} else {
//							firstAngle = lastAngle = angle;
//							distanceSum = (float) distance;
//							samplesCount = 1.0;
//							foundObject = true;
//						}
//					}
//				}
//				if (foundObject) {
//					rangesA.add(new DetectionRange(firstAngle, lastAngle, distanceSum / samplesCount));
//				}
//			
//				robot.joker.rotate(-180, true);
//				while (robot.joker.isMoving()) {
//					int distance = robot.sonar.getDistance();
//					int angle = robot.joker.getPosition();
//					if (distance == 255) {
//						if (foundObject) {
//							rangesB.add(new DetectionRange(lastAngle, firstAngle, distanceSum / samplesCount));
//							foundObject = false;
//						}
//					}
//					else {
//						if (foundObject) {
//							lastAngle = angle;
//							distanceSum += (float) distance;
//							samplesCount += 1.0;
//						} else {
//							firstAngle = lastAngle = angle;
//							distanceSum = (float) distance;
//							samplesCount = 1.0;
//							foundObject = true;
//						}
//					}
//				}
//				if (foundObject) {
//					rangesB.add(new DetectionRange(lastAngle, firstAngle, distanceSum / samplesCount));
//				}
//			
//				java.util.ArrayList<DetectionEdge> edges = new java.util.ArrayList<DetectionEdge>();
//			
//				for (DetectionRange range: rangesA) {
//					edges.add(new DetectionEdge(true, range));
//					edges.add(new DetectionEdge(false, range);
//				}
//				for (DetectionRange range: rangesB) {
//					edges.add(new DetectionEdge(true, range));
//					edges.add(new DetectionEdge(false, range));
//				}
//			
//				rangesA = null;
//				rangesB = null;
//			
//				java.util.Collection.sort(edges, new Comparator<DetectionEdge>() {
//					public int compare(DetectionEdge a, DetectionEdge b) {
//						int angleA = a.leftAngle ? a.range.leftAngle : a.range.rightAngle;
//						int angleB = b.leftAngle ? b.range.leftAngle : b.range.rightAngle;
//			
//						if (angleA == angleB) {
//							if ((a.leftEdge && b.leftEdge) || (!a.leftEdge || !b.leftEdge)) {
//								return a.range.averageDistance < b.range.averageDistance;
//							} else {
//								return !a.leftEdge && b.leftEdge;
//							}
//						} else {
//							return angleA < angleB;
//						}
//					}
//				});
//			
//				java.util.ArrayList<DetectionRange> result = new java.util.ArrayList<DetectionRange>();
//				boolean detectedOverlapping = false;
//				DetectionEdge lastEdge = null;
//				for (DetectionEdge edge: edges) {
//					if (lastEdge == null)
//						if (edge.leftEdge) {
//							lastEdge = edge;
//					}
//					else if (lastEdge.leftEdge && edge.leftEdge) {
//						lastEdge = lastEdge.range.averageDistance > edge.range.averageDistance ? lastEdge : edge;
//						detectedOverlapping = true;
//					}
//					else if (lastEdge.leftEdge && !edge.leftEdge) {
//						if (lastEdge.range == edge.range) {
//							if (detectedOverlapping
//						}
//					}
//			
//					else if (lastEdge.leftEdge && edge.rightEdge && lastEdge.range == edge.range) {
//						// must be a movable object because it apparently disappeared
//					}
//					else if (lastEdge.leftEdge && 
//				}
//			
//				for (DetectionEdge edge : edges) {
//					if (lastEdge == null && edge.firstAngle) {
//						lastEdge = edge;
//					} else if (lastEdge.firstAngle && !edge.firstAngle && java.lang.Math.abs(lastEdge.averageDistance - edge.averageDistance) < 0.0001) {
//						result.add(new DetectionRange(lastEdge.angle, edge.angle, edge.averageDistance));
//						lastEdge = null;
//					} else if (lastEdge.firstAngle && edge.firstAngle) {
//						
//					} else if (!lastEdge.firstAngle && !edge.firstAngle) {
//						
//					} else if (!lastEdge.firstAngle && edge.firstAngle) {
//					}
//				}
//			}
//			
//			@Override
//			public void suppress() {
//				suppressed = true;
//			}
}

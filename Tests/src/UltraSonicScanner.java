import lejos.robotics.navigation.Pose;
import lejos.geom.Point;
import common.Robot;

public class UltraSonicScanner extends Thread {
	private Robot robot;
	private LabyrinthMap map;
	
	public UltraSonicScanner(Robot robot, LabyrinthMap map) {
		this.robot = robot;
		this.map = map;
	}
	
	@Override
	public void run() {
		while (robot.pilot.isMoving()) {
			try {
				//distance in cm
				int distance = robot.sonar.getDistance();
				
				if (distance != 255) {
					//calculate the coordinates of the obstacle we detected
					Pose robotPose = robot.getPose();
					
					Point sensorPosition = robot.getUltraSonicPosition();
					Point obstaclePosition = sensorPosition.pointAt(distance,
							robotPose.getHeading());
					
					map.setObstacleAt(obstaclePosition, sensorPosition);
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
		map.save();
	}
}

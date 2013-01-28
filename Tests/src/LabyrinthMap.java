import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.Sound;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Math;

public class LabyrinthMap {
	public enum Element {UNKNOWN, OBSTACLE, FREE, ROBOT};
	
	private static int pad = 5;
	
	private Element[][] labyrinthMap;
	private Point bottomLeft;
	private float dX, dY;

	public LabyrinthMap(Point bottomLeft, Point topRight) {
		this(bottomLeft, topRight, 20, 20);
	}
	
	public LabyrinthMap(Point bottomLeft, Point topRight, int resolutionX,
			int resolutionY) {
		labyrinthMap = new Element[resolutionX + 2*pad][resolutionY + 2*pad];
		for (int x=0; x<resolutionX + 2*pad; x++) {
			for (int y=0; y<resolutionY + 2*pad; y++) {
				labyrinthMap[x][y] = Element.UNKNOWN;
			}
		}
		this.bottomLeft = bottomLeft;
		dX = (topRight.x - bottomLeft.x) / resolutionX;
		dY = (topRight.y - bottomLeft.y) / resolutionY;		
	}
	
	public void setField(Point p, Element element) {
		setCoordinate(pointToArrayXY(p), element);
	}
	
	public void setCoordinate(Coordinate coord, Element element) {
		if (0 <= coord.x && coord.x < labyrinthMap.length &&
				0 <= coord.y && coord.y < labyrinthMap[coord.x].length) {
			switch (labyrinthMap[coord.x][coord.y]) {
			case UNKNOWN:
			case ROBOT:
			case OBSTACLE:
				labyrinthMap[coord.x][coord.y] = element;
				break;
			//once we have set a field to FREE, it can also be an obstacle
			//if another robot occupies it
			case FREE:
				if (element == Element.OBSTACLE || element == Element.ROBOT)
					labyrinthMap[coord.x][coord.y] = Element.ROBOT;
				break;
			}
		}		
	}
	
	public void setObstacleAt(Point obstacle, Point sensor) {
		Coordinate obstacleCoord = this.pointToArrayXY(obstacle);
		Coordinate currentCoord = this.pointToArrayXY(sensor);
		
		//use a simplified Brasenham to paint the fields along the line as FREE
		int dx =  Math.abs(obstacleCoord.x - currentCoord.x);
		int dy = -Math.abs(obstacleCoord.y - currentCoord.y);
		int sx = (currentCoord.x < obstacleCoord.x) ? 1 : -1;
		int sy = (currentCoord.y < obstacleCoord.x) ? 1 : -1; 
		int err = dx+dy, e2; /* error value e_xy */

		while (!currentCoord.equals(obstacleCoord)) {
			setCoordinate(currentCoord, Element.FREE);
			e2 = 2*err;
			if (e2 > dy) { err += dy; currentCoord.x += sx; }
			if (e2 < dx) { err += dx; currentCoord.y += sy; }
		}	
		
		setCoordinate(currentCoord, Element.OBSTACLE);
	}
	
	private Coordinate pointToArrayXY(Point p) {	
		return new Coordinate(
				(int)((p.x - bottomLeft.x) / dX) + pad,
				(int)((p.y - bottomLeft.y) / dY) + pad);	
	}
	
	
	public void save() {
		File data = new File("log.dat");
		
		//Prepare the file
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(data);
		} catch (IOException e) {
			System.err.println("Failed to create output stream");
			Button.waitForAnyPress();
			System.exit(1);
		}
		DataOutputStream dos = new DataOutputStream(out);
		
		//save the map
		try {
			for (int y=0; y<labyrinthMap[0].length; y++) {
				for (int x=0; x<labyrinthMap.length; x++) {
					dos.writeBoolean(labyrinthMap[x][y] != Element.FREE &&
							labyrinthMap[x][y] != Element.UNKNOWN);
				}
			}
		} catch (IOException e) {
			System.err.println("Failed to write to output stream");
		}
		Sound.beep();
		
		try {
			out.close();
		} catch (IOException e) {
			System.err.println("Failed to write to output stream");
		}
	}
}
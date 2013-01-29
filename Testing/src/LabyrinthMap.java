

import lejos.geom.Point;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import java.util.HashSet;
import java.lang.Math;

public class LabyrinthMap {
	public enum Element {UNKNOWN, OBSTACLE, FREE, ROBOT};
	
	private static int pad = 0;
	
	private Element[][] labyrinthMap;
	private Point bottomLeft;
	private float dX, dY;

	//these cost arrays will be used to calculate the shortest path
	private int[][] g_score;
	//this parent array will be used to reconstruct the shortest path
	private Coordinate[][] cameFrom;
	
	public LabyrinthMap(Point bottomLeft, Point topRight) {
		this(bottomLeft, topRight, 20, 20);
	}
	
	public LabyrinthMap(Point bottomLeft, Point topRight, int resolutionX,
			int resolutionY) {
		labyrinthMap = new Element[resolutionX + 2*pad][resolutionY + 2*pad];
		g_score = new int[resolutionX + 2*pad][resolutionY + 2*pad];
		cameFrom = new Coordinate[resolutionX + 2*pad][resolutionY + 2*pad];
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
	
	private Point arrayXYToPoint(Coordinate coord) {
		return new Point( (coord.x - pad) * dX + bottomLeft.x,
				(coord.y - pad) * dY + bottomLeft.y);
	}
	
	private Element[][] obstacleMap() {
		//create a new map with widened obstacles, so the robot won't collide
		Element[][] newMap = new Element[labyrinthMap.length][labyrinthMap[0].length];
		for (int x=0; x<newMap.length; x++) {
			for (int y=0; y<newMap.length; y++) {
				if (this.hasNearby(x,y,Element.OBSTACLE, 1))
					newMap[x][y] = Element.OBSTACLE;
				else if (this.hasNearby(x,y,Element.ROBOT, 1))
					newMap[x][y] = Element.ROBOT;
				else
					newMap[x][y] = labyrinthMap[x][y];
			}
		}			
		return newMap;
	}
	
	public Path calculatePath(Point from, Point to) {
		Element[][] newMap = this.obstacleMap();
		
		//find a way through the obstacles using A*
		Coordinate target = this.pointToArrayXY(to);
		Coordinate start = this.pointToArrayXY(from);
		HashSet<Coordinate> closedSet = new HashSet<Coordinate>();
		PriorityQueue openSet = new PriorityQueue(3*target.distance(start));
		openSet.add(start);
		start.key = start.distance(target);
		g_score[start.x][start.y] = 0; 
		cameFrom[start.x][start.y] = null;
		
		do {
			Coordinate current = openSet.popMin();
			closedSet.add(current);
			
			//have we found the target yet?
			if (current.equals(target))
				return reconstructPath(start, target);
			
			//if not, process each 8 surrounding fields
			for (int dx=-1; dx<=1; dx++) {
				for (int dy=-1; dy<=1; dy++) {
					if (dx==0 && dy==0) continue;
					Coordinate neighbor = new Coordinate(current.x + dx,
							current.y + dy);
					
					//check if we are beyond the map
					if (0>neighbor.x || neighbor.x >= newMap.length
							|| 0>neighbor.y || neighbor.y >= newMap[0].length)
						continue;
					
					//we ignore obstacles and already checked fields
					if (!closedSet.contains(neighbor)
							&& newMap[neighbor.x][neighbor.y] != Element.OBSTACLE) {
						
						//calculate the cost from start to neighbor
						int transitionCost;
						switch (newMap[neighbor.x][neighbor.y]) {
						case UNKNOWN:
							transitionCost = 2;
							break;
						case ROBOT:
							transitionCost = 4;
							break;
						default:
							transitionCost = 1;
							break;
						}
						int cost = g_score[current.x][current.y] + transitionCost;
						
						if (!openSet.contains(neighbor)
								|| cost <= g_score[neighbor.x][neighbor.y]) {
							cameFrom[neighbor.x][neighbor.y] = current;
							g_score[neighbor.x][neighbor.y] = cost;
							neighbor.key = cost + neighbor.distance(target); 
							if (!openSet.contains(neighbor))
								openSet.add(neighbor);
						}
					}
				}
			}
		} while (!openSet.isEmpty());
		
		//we didn't find a path
		return null;
	}
	
	private Path reconstructPath(Coordinate start, Coordinate target) {
		Path path = new Path();
		Coordinate previousDirection = new Coordinate(0,0);
		Coordinate currentDirection = null;
		Coordinate current = target;
		while (cameFrom[current.x][current.y] != null && current != start) {
			currentDirection = cameFrom[current.x][current.y].direction(current);
			if (!currentDirection.equals(previousDirection)) {
				path.add(0, new Waypoint(this.arrayXYToPoint(current)));
				previousDirection = currentDirection;
			}
			current = cameFrom[current.x][current.y];
		}
		return path;
	}
	
	private boolean hasNearby(int x, int y, Element element, int distance) {
		boolean result = labyrinthMap[x][y] == element;
		for (int dx=-distance; dx<=distance; dx++) {
			for (int dy=-distance; dy<=distance; dy++) {
				if (x+dx >= 0 && x+dx < labyrinthMap.length
						&& y+dy >= 0 && y+dy < labyrinthMap[x+dx].length) {
					result = result || labyrinthMap[x+dx][y+dy] == element;
					if (result) return result;	
				}
			}
		}
		return false;
	}
	
	public void print() {
		for (int y=0; y<labyrinthMap[0].length; y++) {
			for (int x=0; x<labyrinthMap.length; x++) {
				switch (labyrinthMap[x][y]) {
				case FREE:
					System.out.print(" ");
					break;
				case UNKNOWN:
					System.out.print("U");
					break;
				default:
					System.out.print("X");
					break;
				}
			}
			System.out.println("\n");
		}
	}
}

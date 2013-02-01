package labyrinth;

import java.lang.Math;

public class Coordinate {
	public int x;
	public int y;
	public int key;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
		this.key = 0;
	}

	public int distance(Coordinate other) {
		//We simply use the Manhattan distance.
		return Math.abs(other.y - y) + Math.abs(other.x - x);
	}
	
	public boolean equals(Coordinate other) {
		return x == other.x && y == other.y;
	}
	
	public Coordinate direction(Coordinate to) {
		return new Coordinate(to.x - x, to.y - y);
	}
}

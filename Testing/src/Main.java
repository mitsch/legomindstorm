import java.util.Random;

import lejos.geom.Point;
import lejos.robotics.pathfinding.Path;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		pathPlanningTest();
	}
	
	private static void pathPlanningTest() {
		LabyrinthMap map = new LabyrinthMap(new Point(0,0), new Point(100, 100), 5, 5);

		LabyrinthMap.Element[]  elements = {LabyrinthMap.Element.FREE,
				LabyrinthMap.Element.FREE,
				LabyrinthMap.Element.FREE,
				LabyrinthMap.Element.FREE,
				LabyrinthMap.Element.FREE,
				LabyrinthMap.Element.FREE,
				LabyrinthMap.Element.FREE,
				LabyrinthMap.Element.OBSTACLE,
				LabyrinthMap.Element.ROBOT};

		Path path;
		
		//do {
			Random rnd = new Random();
			for (int i=0; i<200; i++) {
				//pick a field
				int x = rnd.nextInt(200);
				int y = rnd.nextInt(200);

				//pick an Element
				int e = rnd.nextInt(elements.length);

				map.setField(new Point(x,y), elements[e]);
			}
			/*for (int x=0; x<200; x++) {
				for (int y=0; y<200; y++) {
					map.setField(new Point(x,y), LabyrinthMap.Element.FREE);
				}
			}*/
			
			map.print();

			path = map.calculatePath(new Point(0,0), new Point(90,90));
		//} while (path == null);
		
		for (Point p : path) {
			System.out.println(p.x + "," + p.y);
		}
	}

	private static void priorityTest() {
		Coordinate[] list = {new Coordinate(2,3),
				new Coordinate(1,0),
				new Coordinate(8,2),
				new Coordinate(2,9),
				new Coordinate(7,4),
				new Coordinate(4,4)};

		Random rnd = new Random();

		//for (int j=0; j<10; j++) {
			PriorityQueue queue = new PriorityQueue(1);
			for (int i=0; i<10; i++) {
				queue.add(list[rnd.nextInt(list.length)]);
			}
			for (int i=0; i<10; i++) {
				queue.popMin();
			}
		//}
	}
}

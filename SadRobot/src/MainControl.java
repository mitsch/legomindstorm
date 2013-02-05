import bridgePasser.BridgeStrategy;
import labyrinth.WallFollower;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.util.Delay;
import lineFollowing.LineFollower;
import common.Robot;
import common.Strategy;
import common.color.Color;
import common.gates.ColorGateControl;
import common.gates.GateControl;

public class MainControl {
	public enum Mode {WAIT_FOR_BARCODE, WAIT_FOR_RACE, RUNNING};
	public static Robot robot;
	public static Mode mode = Mode.WAIT_FOR_BARCODE;
	public static Strategy currentStrategy = null;
	private static boolean aborted = false;
	
	/**
	 * This is the main entry point for our program. We initialize the
	 * Robot. After that, we register a ButtonListener to the ENTER-Button
	 * so we will know when the race begins.
	 */
	public static void main(String[] args) {	
		Delay.msDelay(2000);
		robot = new Robot();
		
		//Since the rule committee wants it so, we react to our Enter-Button
		//depending on the current mode
		Button.ENTER.addButtonListener(new ButtonListener() {
			public void buttonPressed(Button b) {
				switch (mode) {
				case WAIT_FOR_RACE:
					//Since we were just waiting for the race to begin...
					//LET THE RACE BEGIN!
					mode = Mode.RUNNING;
					start();
					break;
				case RUNNING:
					//If we were trying to complete a level, we stop what we are
					//doing
					if (currentStrategy != null) {
						currentStrategy.stop();
						currentStrategy = null;
					}
					mode = Mode.WAIT_FOR_BARCODE;
					aborted = true;
					robot.pilot.stop();
					break;
				case WAIT_FOR_BARCODE:
					//If we were just waiting to reenter the race, we have a
					//look at the bar code to see what out current level is
					aborted = false;
					mode = Mode.RUNNING;
					startNextLevel();
				}
			}
			
			public void buttonReleased(Button b) {
			}
		});
		
		while (true);
	}
	
	/**
	 * This little gadget starts the next level - independent of where we were
	 * before.
	 */
	public static void startNextLevel() {
		Delay.msDelay(2000);
		int lines = analyzeLines();
		if (lines == 0)
			return;
		startProgram(lines);
	}
	
	/**
	 * Counting lines is fun! Simply drive forward until you find one, estimate
	 * its width, and search for further lines. Very error-prone.
	 * @return the number of lines counted
	 */
	public static int analyzeLines() {
		//we find the first line
		int lineCount = 0;
		robot.pilot.travel(100, true);
		while (!robot.isLineBeneath() && robot.pilot.isMoving()) {
			if (aborted)
				return -1;
		}
		
		//now we count them
		while (robot.isLineBeneath()) {
			lineCount++;
			robot.pilot.travel(8, true);
			//we have to leave the current line, cross the void, and enter the
			//next line
			while (robot.pilot.isMoving() && robot.isLineBeneath()) {
				if (aborted)
					return -1;
			}
			Delay.msDelay(75);
			while (robot.pilot.isMoving() && !robot.isLineBeneath()) {
				if (aborted)
					return -1;
			}
			Delay.msDelay(75);
		}
		
		return lineCount;
	}
	
	/**
	 * This little routine starts the right level if we tell it how many lines
	 * we saw.
	 * @param lines the lines just measured
	 */
	public static void startProgram(int lines) {
		switch (lines) {
			case 13:
				mode = Mode.WAIT_FOR_RACE;
				break;
			case 5:
				bridge(false);
				break;
			case 7:
				labyrinth();
				break;
			case 4:
				foamBog();
				break;
			case 3:
				bluetoothGate();
				break;
			case 11:
				turnTable();
				break;
			case 12:
				pusher();
				break;
			case 10:
				seesaw();
				break;
			case 6:
				plankBridge();
				break;
			case 9:
				oppositeLane();
				break;
			case 8:
				colorChooser();
				break;
		}
	}
	
	/**
	 * We need a head start to win... Go, go, GO!
	 */
	public static void start() {
		
		Delay.msDelay(5000);
		robot.arm.rotateTo(robot.arm.getLeftMaxAngle(), true);
		Delay.msDelay(5000);
		
		robot.pilot.forward();
		int distance = robot.sonar.getDistance();
		boolean leftWall = true;
		while (!robot.isLineBeneath()) {
			if (robot.sonar.getDistance() - distance > 20)
				leftWall = false;
			
			if (aborted) return;
		}	
		Delay.msDelay(50);
		
		if (!leftWall) {
			currentStrategy = new WallFollower(robot, true,
					WallFollower.BumpResult.EVADE);
			currentStrategy.start();
			
			if (aborted) return;
		}
		
		while (robot.isLineBeneath()) {
			if (aborted) return;
		}
		Delay.msDelay(50);
		
		race(leftWall);
	}
	
	/**
	 * The race has begun! We have to take two slalom turns, that's all.
	 * Oh yeah... There could be other robots in the way...
	 */
	public static void race(boolean leftWall) {	
		//We start 
		
		//We use our labyrinth algorithm to pass this
		currentStrategy = new WallFollower(robot, leftWall,
				WallFollower.BumpResult.EVADE);
		currentStrategy.start();
		
		if (aborted) return;
		
		//now we are on the first line of the bridge barcode
		if (analyzeLines() == -1) return; 

		bridge(leftWall);
	}
	
	/**
	 * We have to cross a bridge. It has no guardrail, so we could fall off
	 * very easily. Use the light sensor to determine if we are on the bridge
	 * or just in the process of falling off.
	 */
	public static void bridge(boolean leftWall) {
		
		robot.pilot.travel(10, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		//navigate on to the bridge
		currentStrategy = new WallFollower(robot, leftWall,
				WallFollower.BumpResult.EVADE,
				WallFollower.AbortCondition.WOOD);
		currentStrategy.start();
		
		if (aborted) return;
		
		currentStrategy = new BridgeStrategy(robot, true);	
		currentStrategy.start();	
		
		if (aborted) return;
		
		if (analyzeLines() == -1) return;
		
		labyrinth();
	}
	
	/**
	 * A labyrinth - luckily a very easy one. Nevertheless, we simply tag along
	 * the left or right wall and hope we end up somewhere.
	 */
	public static void labyrinth() {
		
		robot.pilot.travel(10, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		currentStrategy = new WallFollower(robot, true,
				WallFollower.BumpResult.TURN);
		currentStrategy.start();
		
		if (aborted) return;
		
		if (analyzeLines() == -1) return;
		
		foamBog();
	}
	
	/**
	 * A bog of foam. How stupid. FYI: Our robot cannot swim.
	 */
	public static void foamBog() {
		
		robot.pilot.travel(10, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		//We could also simply use the labyrinth strategy
		currentStrategy = new WallFollower(robot, true,
				WallFollower.BumpResult.NONE,
				WallFollower.AbortCondition.TIME);
		currentStrategy.start();
		
		if (aborted) return;
		
		robot.pilot.forward();
		while (!aborted && robot.isLineBeneath());
		
		Delay.msDelay(100);
		
		if (analyzeLines() == -1) return;
		
		bluetoothGate();
	}
	
	/**
	 * Open sesame! This gate should open if we ask it nicely per BlueTooth.
	 */
	public static void bluetoothGate() {
			
		//drive up to the gate		
		robot.arm.alignMiddle();
		robot.pilot.forward();
		while (!aborted && robot.sonar.getDistance() > 20);
		robot.pilot.stop();
		
		if (aborted) return;
		
		//connect and open
		System.out.println("Now opening the gate");
		GateControl gateControl = new GateControl();
		while (!aborted && 
				!gateControl.connectionToGateSuccessful());
		if (aborted) return;
		
		gateControl.openGate();
		while (!aborted && robot.sonar.getDistance() < 20);
		
		if (aborted) return;
		
		//navigate through it until you find a line
		robot.pilot.forward();	
		while (!robot.isLineBeneath());
		
		if (analyzeLines() == -1)
			return;
		
		gateControl.disconnectFromGate();
		
		turnTable();
	}
	
	/**
	 * Rock DJ! We simply enter the turntable, ask it to turn, and drive off
	 * again. What could possibly go wrong?
	 * Since the table adds errors to our turn demands, we ask him multiple
	 * times to turn only small degrees and hope the errors cancel each other
	 * out.
	 */
	public static void turnTable() {
		
		//follow line until the sonar registers an obstacle
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.OBSTACLE);
		currentStrategy.start();
		
		if (aborted) return;
		
		//ask the bluetooth to enter the table
		
		//navigate into the table
		
		//rotate 4*45° (Hopefully, we end up right
		//alternatively: rotate yourself 180°
		
		//find a line and follow it until we see wood
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE);
		currentStrategy.start();
		
		if (aborted) return;
		
		pusher();
	}
	
	/**
	 * To pass this pusher, we have to hit the button, drive next to the pusher
	 * and wait until it's gone. Then BURNOUT!
	 */
	public static void pusher() {
		
		robot.pilot.travel(10, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		currentStrategy = new WallFollower(robot, false,
				WallFollower.BumpResult.HALT);
		currentStrategy.start();
		
		if (aborted) return;
		
		currentStrategy = new WallFollower(robot, false,
				WallFollower.BumpResult.HALT_AGGRESSIVE);
		currentStrategy.start();
		
		//drive backwards to the button
		robot.pilot.travel(-10, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		currentStrategy = new WallFollower(robot, false,
				WallFollower.BumpResult.NONE,
				WallFollower.AbortCondition.TIME);
		currentStrategy.start();
		
		if (aborted) return;
		
		//drive forward until we see the pusher (or accidently passed it)
		robot.arm.alignMiddle();
		robot.pilot.travel(10, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		//wait until it is free
		robot.pilot.stop();
		while (!aborted && robot.sonar.getDistance() > 50);
		Delay.msDelay(100);
		while (!aborted && robot.sonar.getDistance() < 50);
		
		if (aborted) return;
		
		robot.pilot.forward();
		
		currentStrategy = new WallFollower(robot, false,
			WallFollower.BumpResult.NONE,
			WallFollower.AbortCondition.TIME);
		currentStrategy.start();	

		if (aborted) return;
		
		robot.pilot.forward();
		while (!aborted && !robot.isLineBeneath());
		
		if (aborted) return;
		
		//follow the line until we see an obstacle (sonar) or wood
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE);
		currentStrategy.start();
		
		if (aborted) return;
	
		seesaw();
	}
	
	/**
	 * Ha, child's play!
	 */
	public static void seesaw() {
		
		robot.pilot.forward();
		while (!aborted && !robot.isWoodBeneath());
		
		if (aborted) return;
		
		robot.pilot.travel(20);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		currentStrategy = new BridgeStrategy(robot, false);
		currentStrategy.start();
		
		if (aborted) return;
		
		int n;
		do {	
			currentStrategy = new LineFollower(robot,
					LineFollower.AbortCondition.END_OF_THE_LINE);
			currentStrategy.start();
			
			if (aborted)
				return;
			
			n = analyzeLines();
			if (n==-1) return;
			
			if (n<3)
				robot.pilot.travel(-50);
		} while (n < 3);
		
		plankBridge();
	}
	
	/**
	 * Simply drive over it. Can't be that hard...
	 */
	public static void plankBridge() {
		robot.pilot.travel(30, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE);
		currentStrategy.start();
		
		if (aborted) return;
		
		if (analyzeLines() == -1) return;
		
		oppositeLane();
	}
	
	/**
	 * When we follow this line, there's a substantial chance that we bump into
	 * another robot. Since we are quite heavy and have a chain drive, we don't
	 * care.
	 */
	public static void oppositeLane() {
		
		robot.pilot.travel(30, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
			
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE, true);
		currentStrategy.start();
		
		if (aborted) return;
		
		if (analyzeLines() == -1) return;
		
		colorChooser();
	}
	
	/**
	 * That stupid bluetooth gate again. Ask it nicely to open... it won't!
	 * Instead, you have to find a specific color on the ground and push the
	 * button next to it.
	 */
	public static void colorChooser() {	
		
		//ask the bluetooth gate for a color
		ColorGateControl gateControl = new ColorGateControl();
		while (!aborted && 
				!gateControl.connectionToColorGateSuccessful()) {
			if (aborted)
				return;
		}
		
		//wait for the answer
		int i = gateControl.readColor();
		
		if (aborted) return;
		
		Color color;
		if (i==0) {
			//we are looking for red
			color = Color.GREEN;
		} else if (i==1) {
			//we are looking for yellow
			color = Color.YELLOW;
		} else {
			//we are looking for green
			color = Color.RED;
		}
		
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.COLOR);
		currentStrategy.start();
		
		if (aborted) return;
		
		currentStrategy = new WallFollower(robot, true, color);
		currentStrategy.start();
		
		if (aborted) return;
		
		//now we reached the right color
		robot.pilot.steer(-100, -90, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		robot.pilot.forward();
		while (!aborted && !robot.leftTouch.isPressed() && !robot.rightTouch.isPressed());
		
		if (aborted) return;
		
		//Hopefully, we have pushed the button
		robot.pilot.steer(-100, 90, true);
		while(!aborted && robot.pilot.isMoving());
		
		if (aborted) return;
		
		currentStrategy = new WallFollower(robot, true, WallFollower.BumpResult.EVADE,
				WallFollower.AbortCondition.LINE);
		currentStrategy.start();
		
		if (aborted) return;
		
		analyzeLines();	
	}
	
	/**
	 * Since we know nothing about this, there is only one goal: SURVIVAL!
	 * EDIT: The endboss will be some robot with a claw, trying to overthrow
	 * this robots. Hopefully, we are heavy enough.
	 */
	public static void endBoss() {
		
		currentStrategy = new WallFollower(robot, true,
				WallFollower.BumpResult.TURN);
		currentStrategy.start();
		
		if (aborted) return;
	}
}
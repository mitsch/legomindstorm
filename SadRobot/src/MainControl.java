import plankBridge.PlankBridgePasser;
import bridgePasser.BridgeStrategy;
import labyrinth.LabyrinthStrategy;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lineFollowing.LineFollower;
import common.GateCommon;
import common.GateControl;
import common.Robot;
import common.Strategy;
import foamBog.FoamBogStrategy;

public class MainControl {
	public enum Mode {WAIT_FOR_BARCODE, WAIT_FOR_RACE, RUNNING};
	public enum Level {RACE, BRIDGE, LABYRINTH, BOG, BLUETOOTH, TURNTABLE,
		PUSHER, SEESAW, PLANKBRIDGE, OPPOSITE_LANE, COLORS, ENDBOSS
	}
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
		MainControl.robot = new Robot();
		
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
		
		Button.ESCAPE.waitForPressAndRelease();
	}
	
	/**
	 * This little gadget starts the next level - independent of where we were
	 * before.
	 */
	public static void startNextLevel() {
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
		robot.pilot.forward();
		int lineCount = 0;
		while (robot.isLineBeneath()) {
			if (aborted)
				return 0;
		}
		
		//now we count them
		while (robot.isLineBeneath()) {
			lineCount++;
			robot.pilot.travel(20, true);
			//we have to leave the current line, cross the void, and enter the
			//next line
			while (robot.pilot.isMoving() && robot.isLineBeneath()) {
				if (aborted)
					return 0;
			}
			
			while (robot.pilot.isMoving() && !robot.isLineBeneath()) {
				if (aborted)
					return 0;
			}
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
			case 1:
				race();
				break;
			case 2:
				bridge();
				break;
			case 3:
				labyrinth();
				break;
			case 4:
				bluetoothGate();
				break;
			case 5:
				turnTable();
				break;
			case 6:
				pusher();
				break;
			case 7:
				seesaw();
				break;
			case 8:
				plankBridge();
				break;
			case 9:
				oppositeLane();
				break;
			case 10:
				colorChooser();
				break;
		}
	}
	
	/**
	 * We need a head start to win... Go, go, GO!
	 */
	public static void start() {
		//We don't need no light sensor.
		robot.light.setFloodlight(false);
		robot.sonar.off();
		
		robot.pilot.forward();
		while (!robot.isLineBeneath()) {
			if (aborted)
				return;
		}	
		while (robot.isLineBeneath()) {
			if (aborted)
				return;
		}
		
		race();
	}
	
	/**
	 * The race has begun! We have to take two slalom turns, that's all.
	 * Oh yeah... There could be other robots in the way...
	 */
	public static void race() {	
		//We start 
		robot.sonar.continuous();
		robot.light.setFloodlight(true);
		
		//We use our labyrinth algorithm to pass this
		currentStrategy = new LabyrinthStrategy(robot, true,
				LabyrinthStrategy.BumpResult.EVADE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		//now we are on the first line of the bridge barcode
		//TODO: while (!wood)
		bridge();
	}
	
	/**
	 * We have to cross a bridge. It has no guardrail, so we could fall off
	 * very easily. Use the light sensor to determine if we are on the bridge
	 * or just in the process of falling off.
	 */
	public static void bridge() {
		robot.sonar.off();
		robot.light.setFloodlight(true);
		
		currentStrategy  = new BridgeStrategy(robot, false);	
		currentStrategy.start();	
		
		if (aborted)
			return;
		
		labyrinth();
	}
	
	/**
	 * A labyrinth - luckily a very easy one. Nevertheless, we simply tag along
	 * the left or right wall and hope we end up somewhere.
	 */
	public static void labyrinth() {
		robot.sonar.continuous();
		robot.light.setFloodlight(true);
		
		currentStrategy = new LabyrinthStrategy(robot, true,
				LabyrinthStrategy.BumpResult.TURN);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		foamBog();
	}
	
	/**
	 * A bog of foam. How stupid. FYI: Our robot cannot swim.
	 */
	public static void foamBog() {
		robot.sonar.continuous();
		robot.light.setFloodlight(true);
		
		//We could also simply use the labyrinth strategy
		currentStrategy = new FoamBogStrategy(robot);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		bluetoothGate();
	}
	
	/**
	 * Open sesame! This gate should open if we ask it nicely per BlueTooth.
	 */
	public static void bluetoothGate() {
		robot.sonar.continuous();
		robot.light.setFloodlight(true);
			
		//drive up to the gate		
		robot.alignLightMiddle();
		while (robot.sonar.getDistance() > 20) {
			if (aborted)
				return;
		}
		
		//connect and open
		GateControl gateControl = new GateControl();
		while (!aborted && 
				!gateControl.connectionToGateSuccessful(GateCommon.GATE_1)) {
			if (aborted)
				return;
		}
		
		//navigate through it until you find a line
		
		//follow line until the sonar registers an obstacle
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.OBSTACLE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
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
		robot.sonar.continuous();
		robot.light.setFloodlight(true);
		
		//navigate into the table
		
		//rotate 4*45° (Hopefully, we end up right
		//alternatively: rotate yourself 180°
		
		//find a line and follow it until we see wood
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.WOOD);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		pusher();
	}
	
	/**
	 * To pass this pusher, we have to hit the button, drive next to the pusher
	 * and wait until it's gone. Then BURNOUT!
	 */
	public static void pusher() {
		robot.sonar.continuous();
		robot.light.setFloodlight(false);
		
		//drive along the wall 'til we bump, twice
		for (int i=0; i<2; i++) {
			currentStrategy = new LabyrinthStrategy(robot, true,
					LabyrinthStrategy.BumpResult.HALT);
			currentStrategy.start();
			
			if (aborted)
				return;
		}
		
		//drive backwards to the button
		robot.pilot.travel(-20, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted)
			return;
		
		//drive forward until we see the pusher (or accidently passed it)
		robot.alignLightLeft();
		robot.pilot.forward();
		robot.light.setFloodlight(true);
		while (!aborted && !(robot.sonar.getDistance() < 10) 
				&& !robot.isLineBeneath());
		
		if (aborted)
			return;
		
		//wait until it is free
		robot.pilot.stop();
		while (!aborted && robot.sonar.getDistance() < 10);
		
		if (aborted)
			return;
		
		//follow the line until we see an obstacle (sonar) or wood
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.OBSTACLE);
		currentStrategy.start();
		
		if (aborted)
			return;
	
		seesaw();
	}
	
	/**
	 * Ha, child's play!
	 */
	public static void seesaw() {
		robot.sonar.off();
		robot.light.setFloodlight(true);
		
		robot.pilot.forward();
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted)
			return;
		
		currentStrategy = new BridgeStrategy(robot, true);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		if (analyzeLines() == 0)
			return;
		
		plankBridge();
	}
	
	/**
	 * Simply drive over it. Can't be that hard...
	 */
	public static void plankBridge() {
		robot.sonar.off();
		robot.light.setFloodlight(true);
		
		currentStrategy = new PlankBridgePasser(robot);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		if (analyzeLines() == 0)
			return;
		
		oppositeLane();
	}
	
	/**
	 * When we follow this line, there's a substantial chance that we bump into
	 * another robot. Since we are quite heavy and have a chain drive, we don't
	 * care.
	 */
	public static void oppositeLane() {
		robot.sonar.continuous();
		robot.light.setFloodlight(true);
		
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		if (analyzeLines() == 0)
			return;
		
		colorChooser();
	}
	
	/**
	 * That stupid bluetooth gate again. Ask it nicely to open... it won't!
	 * Instead, you have to find a specific color on the ground and push the
	 * button next to it.
	 */
	public static void colorChooser() {	
		robot.sonar.continuous();
		robot.light.setFloodlight(true);
		
		//ask the bluetooth gate for a color
		
		//wait for the answer
			
	}
	
	/**
	 * Since we know nothing about this, there is only one goal: SURVIVAL!
	 */
	public static void endBoss() {
		robot.sonar.continuous();
		robot.light.setFloodlight(false);
		
		//do some bullshit here
	}
}
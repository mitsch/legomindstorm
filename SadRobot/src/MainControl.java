import plankBridge.PlankBridgePasser;
import bridgePasser.BridgeStrategy;
import labyrinth.LabyrinthStrategy;
import labyrinth.LabyrinthStrategy.AbortCondition;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.util.Delay;
import lineFollowing.LineFollower;
import common.GateCommon;
import common.GateControl;
import common.ColorGateControl;
import common.Robot;
import common.Strategy;
import common.color.Color;
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
		Delay.msDelay(2000);
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
		
		Button.ESCAPE.waitForPressAndRelease();
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
		robot.pilot.forward();
		int lineCount = 0;
		while (!robot.isLineBeneath()) {
			if (aborted)
				return 0;
		}
		
		//now we count them
		while (robot.isLineBeneath()) {
			System.out.println(lineCount);
			lineCount++;
			robot.pilot.travel(15, true);
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
			case 13:
				start();
				break;
			case 5:
				bridge();
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
		robot.sonar.continuous();
		
		Delay.msDelay(5000);
		robot.joker.rotateTo(robot.getLeftJoker(), true);
		Delay.msDelay(5000);
		
		robot.pilot.forward();
		int distance = robot.sonar.getDistance();
		boolean leftWall = true;
		while (!robot.isLineBeneath()) {
			if (robot.sonar.getDistance() - distance > 20)
				leftWall = false;
			
			if (aborted)
				return;
		}	
		
		if (!leftWall) {
			currentStrategy = new LabyrinthStrategy(robot, true,
					LabyrinthStrategy.BumpResult.EVADE);
			currentStrategy.start();
			
			if (aborted)
				return;
		}
		
		while (robot.isLineBeneath()) {
			if (aborted)
				return;
		}
		
		race(leftWall);
	}
	
	/**
	 * The race has begun! We have to take two slalom turns, that's all.
	 * Oh yeah... There could be other robots in the way...
	 */
	public static void race(boolean leftWall) {	
		//We start 
		robot.sonar.continuous();
		
		//We use our labyrinth algorithm to pass this
		currentStrategy = new LabyrinthStrategy(robot, leftWall,
				LabyrinthStrategy.BumpResult.EVADE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		//now we are on the first line of the bridge barcode
		analyzeLines();

		bridge();
	}
	
	/**
	 * We have to cross a bridge. It has no guardrail, so we could fall off
	 * very easily. Use the light sensor to determine if we are on the bridge
	 * or just in the process of falling off.
	 */
	public static void bridge() {
		robot.sonar.continuous();
		
		//navigate on to the bridge
		currentStrategy = new LabyrinthStrategy(robot, false,
				LabyrinthStrategy.BumpResult.EVADE,
				LabyrinthStrategy.AbortCondition.WOOD);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		currentStrategy = new BridgeStrategy(robot, false);	
		currentStrategy.start();	
		
		if (aborted)
			return;
		
		analyzeLines();
		
		labyrinth();
	}
	
	/**
	 * A labyrinth - luckily a very easy one. Nevertheless, we simply tag along
	 * the left or right wall and hope we end up somewhere.
	 */
	public static void labyrinth() {
		robot.sonar.continuous();
		
		currentStrategy = new LabyrinthStrategy(robot, true,
				LabyrinthStrategy.BumpResult.TURN);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		analyzeLines();
		
		foamBog();
	}
	
	/**
	 * A bog of foam. How stupid. FYI: Our robot cannot swim.
	 */
	public static void foamBog() {
		robot.sonar.continuous();
		
		//We could also simply use the labyrinth strategy
		currentStrategy = new FoamBogStrategy(robot);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		analyzeLines();
		
		bluetoothGate();
	}
	
	/**
	 * Open sesame! This gate should open if we ask it nicely per BlueTooth.
	 */
	public static void bluetoothGate() {
		robot.sonar.continuous();
			
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
		robot.pilot.forward();	
		
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
		
		//follow line until the sonar registers an obstacle
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.OBSTACLE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		//ask the bluetooth to enter the table
		
		//navigate into the table
		
		//rotate 4*45° (Hopefully, we end up right
		//alternatively: rotate yourself 180°
		
		//find a line and follow it until we see wood
		currentStrategy = new LineFollower(robot,
				LineFollower.AbortCondition.END_OF_THE_LINE);
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
		
		//ask the bluetooth gate for a color
		ColorGateControl gateControl = new ColorGateControl();
		while (!aborted && 
				!gateControl.connectionToGate2Successful()) {
			if (aborted)
				return;
		}
		
		//wait for the answer
		int i = gateControl.readColor();
		
		if (aborted)
			return;
		
		Color color;
		if (i==0) {
			//we are looking for red
			color = Color.RED;
		} else if (i==1) {
			//we are looking for yellow
			color = Color.YELLOW;
		} else {
			//we are looking for green
			color = Color.GREEN;
		}
		
		currentStrategy = new LabyrinthStrategy(robot, true, color);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		//now we reached the right color
		robot.pilot.steer(-100, -90, true);
		while (!aborted && robot.pilot.isMoving());
		
		if (aborted)
			return;
		
		robot.pilot.forward();
		while (!aborted && !robot.leftTouch.isPressed() && !robot.rightTouch.isPressed());
		
		if (aborted)
			return;
		
		//Hopefully, we have pushed the button
		robot.pilot.steer(-100, 90, true);
		while(!aborted && robot.pilot.isMoving());
		
		if (aborted)
			return;
		
		currentStrategy = new LabyrinthStrategy(robot, true, LabyrinthStrategy.BumpResult.EVADE,
				LabyrinthStrategy.AbortCondition.LINE);
		currentStrategy.start();
		
		if (aborted)
			return;
		
		analyzeLines();	
	}
	
	/**
	 * Since we know nothing about this, there is only one goal: SURVIVAL!
	 * EDIT: The endboss will be some robot with a claw, trying to overthrow
	 * this robots. Hopefully, we are heavy enough.
	 */
	public static void endBoss() {
		robot.sonar.continuous();
		
		currentStrategy = new LabyrinthStrategy(robot, true,
				LabyrinthStrategy.BumpResult.TURN);
		currentStrategy.start();
		
		if (aborted)
			return;
	}
}
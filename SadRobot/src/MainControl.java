import bridgePasser.BridgeStrategy;
import labyrinth.LabyrinthStrategy;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import common.Robot;
import common.Strategy;
import foamBog.FoamBogStrategy;

public class MainControl {
	public enum Mode {WAIT_FOR_BARCODE, WAIT_FOR_RACE, RUNNING};
	public static Robot robot;
	public static Mode mode = Mode.WAIT_FOR_BARCODE;
	public static Strategy currentStrategy = null;
	
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
			}
			
			public void buttonReleased(Button b) {
				switch (mode) {
				case WAIT_FOR_RACE:
					//Since we were just waiting for the race to begin...
					//LET THE RACE BEGIN!
					mode = Mode.RUNNING;
					race();
					break;
				case RUNNING:
					//If we were trying to complete a level, we stop what we are
					//doing
					if (currentStrategy != null) {
						currentStrategy.stop();
						currentStrategy = null;
					}
					mode = Mode.WAIT_FOR_BARCODE;
					break;
				case WAIT_FOR_BARCODE:
					//If we were just waiting to reenter the race, we have a
					//look at the bar code to see what out current level is
					mode = Mode.RUNNING;
					startNextLevel();
				}
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
		while(!robot.isLineBeneath());
		
		//now we count them
		while (robot.isLineBeneath()) {
			lineCount++;
			robot.pilot.travel(20, true);
			//we have to leave the current line, cross the void, and enter the
			//next line
			while (robot.pilot.isMoving() && robot.isLineBeneath());
			while (robot.pilot.isMoving() && !robot.isLineBeneath());
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
	 * The race has begun! We have to take two slalom turns, that's all.
	 * Oh yeah... There could be other robots in the way...
	 */
	public static void race() {	
	}
	
	/**
	 * We have to cross a bridge. It has no guardrail, so we could fall off
	 * very easily. Use the light sensor to determine if we are on the bridge
	 * or just in the process of falling off.
	 */
	public static void bridge() {
		Strategy bridgeStrategy = new BridgeStrategy(robot, false);	
		bridgeStrategy.start();	
		startNextLevel();
	}
	
	/**
	 * A labyrinth - luckily a very easy one. Nevertheless, we simply tag along
	 * the left or right wall and hope we end up somewhere.
	 */
	public static void labyrinth() {
		Strategy labyrinthStrategy = new LabyrinthStrategy(robot, true);
		labyrinthStrategy.start();
		startNextLevel();
	}
	
	/**
	 * A bog of foam. How stupid. FYI: Our robot cannot swim.
	 */
	public static void foamBog() {
		Strategy foamBogStrategy = new FoamBogStrategy(robot);
		foamBogStrategy.start();
		bluetoothGate();
	}
	
	/**
	 * Open sesame! This gate should open if we ask it nicely per BlueTooth.
	 */
	public static void bluetoothGate() {
	}
	
	/**
	 * Rock DJ! We simply enter the turntable, ask it to turn, and drive off
	 * again. What could possibly go wrong?
	 * Since the table adds errors to our turn demands, we ask him multiple
	 * times to turn only small degrees and hope the errors cancel each other
	 * out.
	 */
	public static void turnTable() {
	}
	
	/**
	 * To pass this pusher, we have to hit the button, drive next to the pusher
	 * and wait until it's gone. Then BURNOUT!
	 */
	public static void pusher() {
	}
	
	/**
	 * Ha, child's play!
	 */
	public static void seesaw() {
	}
	
	/**
	 * Simply drive over it. Can't be that hard...
	 */
	public static void plankBridge() {
	}
	
	/**
	 * When we follow this line, there's a substantial chance that we bump into
	 * another robot. Since we are quite heavy and have a chain drive, we don't
	 * care.
	 */
	public static void oppositeLane() {
	}
	
	/**
	 * That stupid bluetooth gate again. Ask it nicely to open... it won't!
	 * Instead, you have to find a specific color on the ground and push the
	 * button next to it.
	 */
	public static void colorChooser() {	
	}
}
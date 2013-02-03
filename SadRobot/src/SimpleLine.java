import lejos.nxt.Button;
import lejos.robotics.Color;
import lejos.util.Delay;
import common.Robot;

public class SimpleLine {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Button.waitForAnyPress();
		Delay.msDelay(5000);
		Robot robot = new Robot();
		Button.waitForAnyPress();
		if (robot.light.setFloodlight(Color.GREEN))
			System.out.println("Success");
		Button.waitForAnyPress();
	}
}

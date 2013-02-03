package racing;
import lejos.nxt.comm.LCPBTResponder;

public class Main
{
	public static void main(String [] args) {
		lejos.util.Delay.msDelay(1000);
		common.Robot robot = new common.Robot();
		robot.calibrateJoker();
		
		Racer racer = new Racer(robot);
		racer.start();
	}
}


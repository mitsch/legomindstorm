package racing;
import lejos.nxt.comm.LCPBTResponder;

public class Main
{
	public static void main(String [] args) {
		common.Robot robot = new common.Robot();
		robot.calibrateJoker();
		
		Racer racer = new Racer(robot);
		racer.start();
	}
}


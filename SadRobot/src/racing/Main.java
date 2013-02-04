package racing;
import lejos.nxt.comm.LCPBTResponder;

public class Main
{
	public static void main(String [] args) {
		common.Robot robot = new common.Robot();
		
		Racer racer = new Racer(robot);
		racer.start();
	}
}



import lejos.nxt.ADSensorPort;
import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class ColorGatherer {

	public LightSensor light;

	public NXTRegulatedMotor leftMotor, rightMotor;

	public DifferentialPilot pilot;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		(new ColorGatherer()).run();

	}

	public ColorGatherer() {
		super();
		this.leftMotor = Motor.C;
		this.rightMotor = Motor.A;
			
		this.light = new LightSensor(SensorPort.S2);

		this.pilot = new DifferentialPilot(3.3f, 21.0f, leftMotor, rightMotor);

	}

	private void run() {
				
		System.out.println("GO!");
	
		
		int[] values = new int[100];
		
		int cnt = 0;
		
		pilot.forward();
		
		while (cnt<500) {
			
			values[this.light.getLightValue()]++;
			
			Delay.msDelay(10);
			cnt++;
		}
		
		pilot.stop();
		
		String csv = "";
		
		for (int i = 0; i < values.length; i++) {
			csv+= values[i] +" ";
		}
		
		System.out.println(csv);
		
		return;
	}
}

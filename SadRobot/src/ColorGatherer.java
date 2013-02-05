
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
	
	private int[] gather(int cnt){
		

		int[] values = new int[cnt];
		
		int rnd = 0;
		
		pilot.forward();
		
		
		while (rnd<cnt) {
			
			values[this.light.getLightValue()]++;
			
			Delay.msDelay(10);
			rnd++;
		}
		
		pilot.stop();
	
		return values;
		
	}
	
	private void run() {
		

		System.out.println("GO!");
	
		while(Button.readButtons()!=Button.ID_ESCAPE){
			
			System.out.println("Gather?");
		
			String csv = "";
			
			int[] values = this.gather(500);
			
			for (int i = 0; i < values.length; i++) {
				csv+= values[i] +"; ";
			}
			
			System.out.println(csv);
			
		}
		
	}
}

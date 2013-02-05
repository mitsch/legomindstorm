import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class ColorGatherer {

	public LightSensor light;

	public NXTRegulatedMotor leftMotor, rightMotor;

	public DifferentialPilot pilot;
	
	private boolean emergency = false;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("ColorGatherer");

		(new ColorGatherer()).run();

	}

	public ColorGatherer() {
		super();
		this.leftMotor = Motor.C;
		this.rightMotor = Motor.A;

		this.light = new LightSensor(SensorPort.S2);

		this.pilot = new DifferentialPilot(3.3f, 21.0f, leftMotor, rightMotor);
		
		ButtonListener bl = new ButtonListener() {
			
			@Override
			public void buttonReleased(Button b) {
					
			}
			
			@Override
			public void buttonPressed(Button b) {
				emergency = true;
			}
		};
		
		Button.ESCAPE.addButtonListener(bl);
		
	}

	private void run() {

		String csv;
		
		while (!emergency) {
			
			System.out.println("Enter = new Gathering");
			
			// Waiting ;)
			while (Button.readButtons() == Button.ID_ENTER) {

			}
			
			System.out.println("-----------------------");
			
			int[] values = this.gather(512);
			csv = ColorGatherer.toCSV(values);

			System.out.println(csv);
			
			System.out.println("-----------------------");
			
						
		}

		//return;
	}

	private int[] gather(int cntSamples) {

		int[] values = new int[1023];

		int cnt = 0;

		while (cnt < cntSamples) {

			values[this.light.readNormalizedValue()]++;

			Delay.msDelay(10);
			cnt++;
		}

		return values;

	}

	private static String toCSV(int[] values) {

		String csv = "";

		for (int i = 0; i < values.length; i++) {
			csv += values[i] + ";";
		}

		return csv;

	}

}
